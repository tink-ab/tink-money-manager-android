package se.tink.android.repository.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tink.annotations.PfmScope
import com.tink.model.account.Account
import com.tink.service.account.AccountService
import se.tink.android.livedata.map
import javax.inject.Inject

@PfmScope
class AccountRepository @Inject constructor(private val accountService: AccountService) {

    fun accounts(): LiveData<List<Account>> = liveData {
        val accounts = try {
            accountService.listAccounts()
        } catch (t: Throwable) {
            emptyList<Account>()
        }
        emit(accounts)
    }

    fun accountById(id: String): LiveData<Account?> = accounts().map { accountList ->
        accountList.firstOrNull { it.id == id }
    }
}