package se.tink.android.repository.user

import androidx.lifecycle.LiveData
import com.tink.annotations.PfmScope
import com.tink.model.user.UserProfile
import com.tink.service.observer.ChangeObserver
import se.tink.repository.service.UserConfigurationService
import javax.inject.Inject

@PfmScope
class UserRepository @Inject constructor(private val userConfigurationService: UserConfigurationService) {

    val userProfile = object : LiveData<UserProfile?>() {

        private val observer = object : ChangeObserver<UserProfile> {
            override fun onCreate(item: UserProfile) = update(item)
            override fun onRead(item: UserProfile) = update(item)
            override fun onUpdate(item: UserProfile) = update(item)
            override fun onDelete(item: UserProfile) = update(null)
        }

        fun update(config: UserProfile?) = postValue(config)

        override fun onActive() = userConfigurationService.subscribe(observer)
        override fun onInactive() = userConfigurationService.unsubscribe(observer)
    }
}
