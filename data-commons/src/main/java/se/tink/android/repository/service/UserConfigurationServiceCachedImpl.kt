package se.tink.android.repository.service

import com.tink.model.user.UserProfile
import com.tink.service.observer.ChangeObserver
import com.tink.service.user.UserProfileService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@Deprecated("Use UserProfileService directly")
class UserConfigurationServiceCachedImpl @Inject constructor(
    private val userService: UserProfileService,
    private val dispatcher: DispatcherProvider,
) : UserConfigurationService {

    private val changeObservers: MutableList<ChangeObserver<UserProfile>> = mutableListOf()

    override fun subscribe(listener: ChangeObserver<UserProfile>) {
        changeObservers.add(listener)
    }

    override fun unsubscribe(listener: ChangeObserver<UserProfile>) {
        changeObservers.remove(listener)
    }

    override fun refreshUserConfiguration() {
        CoroutineScope(dispatcher.io()).launch {
            try {
                notifyOnRead(userService.getProfile())
            } catch (error: Throwable) {
                Timber.e(error)
            }
        }
    }

    private fun notifyOnRead(userProfile: UserProfile) {
        for (observer in changeObservers) {
            observer.onRead(userProfile)
        }
    }
}