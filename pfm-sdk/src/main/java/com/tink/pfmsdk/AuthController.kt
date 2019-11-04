package com.tink.pfmsdk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import com.tink.pfmsdk.buildConfig.Feature
import com.tink.pfmsdk.analytics.Analytics
import com.tink.pfmsdk.collections.Periods
import com.tink.pfmsdk.collections.Providers
import com.tink.pfmsdk.collections.Categories
import com.tink.pfmsdk.buildConfig.BuildConfigurations
import com.tink.pfmsdk.configuration.SuitableLocaleFinder
import se.tink.android.di.application.ApplicationScoped
import se.tink.core.models.tracking.TrackingConfigurationResponse
import se.tink.privacy.DataWipeManager
import se.tink.repository.ServiceCoordinator
import se.tink.repository.SimpleMutationHandler
import se.tink.repository.TinkNetworkError
import se.tink.repository.TinkNetworkErrorHandler
import se.tink.repository.cache.CacheHandle
import se.tink.repository.service.*
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_INACTIVITY_UNTIL_LOGOUT_DELAY_MS = 120_000L

@Singleton
class AuthController @Inject constructor(@ApplicationScoped private val context: Context,
                                         private val analytics: Analytics,
                                         private val trackingService: TrackingService,
                                         private var interceptor: HeaderClientInterceptor,
                                         private val serviceCoordinator: ServiceCoordinator,
                                         private val categoryService: CategoryService,
                                         private val periodService: PeriodService,
                                         private val userConfigurationService: UserConfigurationService,
                                         private val deviceService: DeviceService,
                                         private val providerService: ProviderService,
                                         private val cacheHandle: CacheHandle,
                                         private val networkErrorHandler: TinkNetworkErrorHandler) {

    private val dataStorage = ClientDataStorage.sharedInstance(context)
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    val isLoggedIn: Boolean get() = authState.value != AuthState.Logout

    private var fingerprintLocked = false

    init {
        val sessionId = if (isLoginWithStoredSessionIdAllowed()) dataStorage.sessionId else null
        if (sessionId != null) {
            logIn(sessionId)
        } else {
            _authState.value = AuthState.Logout
        }
    }

    private fun logIn(sessionId: String, isNewUser: Boolean = false) {
        fingerprintLocked = false
        _authState.postValue(AuthState.Login(isNewUser))
        if (sessionId != dataStorage.sessionId) {
            dataStorage.sessionId = sessionId
        }
        interceptor.setSession(sessionId)

        Categories.getSharedInstance().attatchListener(categoryService)
        // TODO: PFMSDK: Do we need this?
        //Currencies.getSharedInstance().attatchListener(userConfigurationService)
        Periods.getSharedInstance().attatchListener(periodService)
        Providers.getSharedInstance().attatchListener(providerService)
        // TODO: PFMSDK: Do we need this?
        //FCMManager.getInstance().registerPushToken(deviceService)

        networkErrorHandler.setOnUnauthenticated { logOut() }
        serviceCoordinator.updateLocaleAndInitializeStreaming(
                StreamingServiceErrorHandler(networkErrorHandler),
                SuitableLocaleFinder().findLocaleString(),
                networkErrorHandler)

        trackingService.getTrackingConfiguration(
                object : SimpleMutationHandler<TrackingConfigurationResponse>() {
                    override fun onNext(item: TrackingConfigurationResponse) =
                            analytics.registerUser(item.trackingUserId, item.trackingUsername)

                    override fun onError(error: TinkNetworkError) {}
                }
        )
    }

    fun logOut() {
        serviceCoordinator.logout()
        interceptor.setSession(null)

        Categories.getSharedInstance().removeListener(categoryService)
        // TODO: PFMSDK: Do we need this?
        //Currencies.getSharedInstance().removeListener(userConfigurationService)
        Periods.getSharedInstance().removeListener(periodService)
        Providers.getSharedInstance().removeListener(providerService)
        dataStorage.sessionId = null

        cacheHandle.clearCache()
        _authState.postValue(AuthState.Logout)
    }

    fun onSessionObtained(sessionId: String) {
        dataStorage.isFirstLogin = false
        logIn(sessionId)
    }

    fun newUserLogin(sessionId: String) {
        dataStorage.isFirstLogin = false
        logIn(sessionId, true)
    }

    fun onLoggedInWithFingerprint() = logIn(dataStorage.sessionId)

    fun canLogInWithFingerprint() =
            !fingerprintLocked && dataStorage.sessionId != null && dataStorage.useTouchIdLogin()


    fun temporaryLockFingerprintAuth() {
        fingerprintLocked = true
        _authState.postValue(_authState.value)
    }

    private fun isLoginWithStoredSessionIdAllowed(): Boolean {
        val forceProtection = BuildConfigurations.instance.featureConfigurations
                .hasFeature(Feature.FORCE_PASSWORD_PROTECTION)
        val usePasswordProtection = dataStorage.passwordProtection
        val touchId = dataStorage.useTouchIdLogin()

        return !forceProtection && !usePasswordProtection && !touchId
    }

    fun onInactivity(inactivityTime: Long) {
        if (isLoggedIn && dataStorage.passwordProtection
                && (inactivityTime > DEFAULT_INACTIVITY_UNTIL_LOGOUT_DELAY_MS)) {
            logOut()
        }
    }

    fun removeAccount() {
        logOut()
        DataWipeManager.sharedInstance().wipeData()
    }

    sealed class AuthState {

        data class Login(val isNewUser: Boolean): AuthState()

        object Logout : AuthState()
    }
}