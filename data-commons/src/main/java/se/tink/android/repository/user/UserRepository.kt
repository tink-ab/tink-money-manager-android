package se.tink.android.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.user.UserProfile
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.user.UserProfileService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@PfmScope
class UserRepository @Inject constructor(
    private val userProfileService: UserProfileService,
    private val dispatcher: DispatcherProvider
) {
    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    // Perhaps use a StateFlow instead (or wait until it's out of experimental).

    private val _userProfile = object : MutableLiveData<UserProfile?>() {
        override fun onActive() {
            refresh()
        }
    }
    val userProfile: LiveData<UserProfile?> = _userProfile

    fun refresh() {
        CoroutineScope(dispatcher.io()).launch {
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
        CoroutineScope(dispatcher.io()).launch {
            try {
                val userProfile = userProfileService.getProfile()
                _userProfileState.postValue(SuccessState(userProfile))
            } catch (e: Exception) {
                _userProfileState.postValue(ErrorState(e))
            }
        }
    }
}
