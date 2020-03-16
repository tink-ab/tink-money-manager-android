package se.tink.android.repository.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import se.tink.core.models.account.Account
import se.tink.repository.service.AccountService
import javax.inject.Inject

@PfmScope
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