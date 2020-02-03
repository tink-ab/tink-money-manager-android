package se.tink.android.repository.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import se.tink.core.models.account.Account
import se.tink.repository.service.AccountService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(private val accountService: AccountService) {

    fun accounts(): LiveData<List<Account>> {
        val liveData = MutableLiveData<ErrorOrValue<List<Account>>>()
        accountService.listAccounts(liveData.createMutationHandler())
        return liveData.map { it.value ?: emptyList() }
    }

    fun accountById(id: String): LiveData<Account?> = accounts().map { accountList ->
        accountList.firstOrNull { it.id == id }
    }
}