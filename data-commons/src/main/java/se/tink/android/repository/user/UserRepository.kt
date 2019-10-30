package se.tink.android.repository.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.tink.core.models.user.UserConfiguration
import se.tink.core.models.user.UserFeatureFlags
import se.tink.repository.SimpleObjectChangeObserver
import se.tink.repository.service.UserConfigurationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userConfigurationService: UserConfigurationService) {

    val userConfiguration = object : LiveData<UserConfiguration?>() {

        private val observer = object : SimpleObjectChangeObserver<UserConfiguration>() {
            override fun onCreate(item: UserConfiguration) = update(item)
            override fun onRead(item: UserConfiguration) = update(item)
            override fun onUpdate(item: UserConfiguration) = update(item)
            override fun onDelete(item: UserConfiguration?) = update(null)
        }

        fun update(config: UserConfiguration?) = postValue(config)

        override fun onActive() = userConfigurationService.subscribe(observer)
        override fun onInactive() = userConfigurationService.unsubscribe(observer)
    }

    val hasNewBudgets: LiveData<Boolean> = Transformations.map(userConfiguration) {
        it?.flags?.hasBudgets == true
    }

    val hasRecurringTransactions: LiveData<Boolean> = Transformations.map(userConfiguration) {
        it?.flags?.hasRecurringTransactions == true
    }

    val isTinkEmployee: LiveData<Boolean> = Transformations.map(userConfiguration) {
        it?.flags?.isEmployee ?: false
    }

    val featureFlags: LiveData<UserFeatureFlags> = Transformations.map(userConfiguration) {
        it?.flags
    }

    val hasActionableInsights: LiveData<Boolean> = Transformations.map(userConfiguration) {
        it?.flags?.run { isEmployee && hasInsights } ?: false
    }
}
