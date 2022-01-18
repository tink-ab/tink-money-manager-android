package se.tink.android.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.user.UserProfile
import com.tink.service.user.UserProfileService
import kotlinx.coroutines.*
import com.tink.service.network.ResponseState
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@PfmScope
class UserRepository @Inject constructor(
    private val userProfileService: UserProfileService
) {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    // Perhaps use a StateFlow instead (or wait until it's out of experimental).


    private val _userProfile = object : MutableLiveData<UserProfile?>() {
        override fun onActive() {
            refresh()
        }

        override fun onInactive() {
            // Not sure if this is really needed since we do a [postValue] in [onActive] anyway...
            scope.coroutineContext.cancelChildren()
        }
    }
    val userProfile: LiveData<UserProfile?> = _userProfile

    fun refresh() {
        scope.launch {
            try {
                _userProfile.postValue(userProfileService.getProfile())
            } catch (error: Exception) {
                Timber.e(error)
            }
        }
    }

    private val _userProfileState = object : MutableLiveData<ResponseState<UserProfile>>() {
        override fun onActive() {
            refresh()
        }

        fun refresh() {
            refreshState()
        }
    }

    val userProfileState: LiveData<ResponseState<UserProfile>> = _userProfileState

    fun refreshState() {
        _userProfileState.postValue(LoadingState)
        scope.launch {
            try {
                val userProfile = userProfileService.getProfile()
                _userProfileState.postValue(SuccessState(userProfile))
            } catch (e: Exception) {
                _userProfileState.postValue(ErrorState(e))
            }
        }
    }
}
