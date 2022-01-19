package se.tink.android.repository.service

import com.tink.model.user.UserProfile
import com.tink.service.observer.ChangeObserver

@Deprecated("")
interface UserConfigurationService {
    fun subscribe(listener: ChangeObserver<UserProfile>)
    fun unsubscribe(listener: ChangeObserver<UserProfile>)
    fun refreshUserConfiguration()
}