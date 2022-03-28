package com.tink.moneymanagerui.collections

import com.tink.model.user.UserProfile
import com.tink.service.observer.ChangeObserver
import se.tink.android.privacy.Clearable

@Deprecated("")
class Currencies : ChangeObserver<UserProfile?>, Clearable {
    var userProfile: UserProfile? = null
        private set
    val defaultCurrencyCode: String
        get() = userProfile?.currency ?: DEFAULT_CURRENCY_CODE

    override fun onCreate(item: UserProfile?) {
        userProfile = item
    }

    override fun onRead(item: UserProfile?) {
        userProfile = item
    }

    override fun onUpdate(item: UserProfile?) {
        userProfile = item
    }

    override fun onDelete(item: UserProfile?) {
        userProfile = item
    }

    override fun clear() {
        userProfile = null
    }

    companion object {
        private const val DEFAULT_CURRENCY_CODE = "EUR"
        val sharedInstance: Currencies = Currencies()
    }
}
