package se.tink.android.repository.service

import com.tink.model.user.UserProfile
import com.tink.service.observer.ChangeObserver
import com.tink.service.user.UserProfileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

class UserConfigurationServiceCachedImpl @Inject constructor(
    private val userService: UserProfileService
) : UserConfigurationService {

    private val changeObservers: MutableList<ChangeObserver<UserProfile>> = mutableListOf()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun subscribe(listener: ChangeObserver<UserProfile>) {
        changeObservers.add(listener)
    }

    override fun unsubscribe(listener: ChangeObserver<UserProfile>) {
        changeObservers.remove(listener)
    }

    override fun refreshUserConfiguration() {
        scope.launch {
            try {
                notifyOnRead(userService.getProfile())
            } catch (error: Throwable) {
                //TODO: Core
            }
        }
    }

    private fun notifyOnRead(userProfile: UserProfile) {
        for (observer in changeObservers) {
            observer.onRead(userProfile)
        }
    }
}