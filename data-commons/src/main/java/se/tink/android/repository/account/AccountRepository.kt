package se.tink.android.repository.account

import androidx.lifecycle.LiveData
import com.tink.annotations.PfmScope
import com.tink.model.account.Account
import com.tink.service.account.AccountService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import javax.inject.Inject

@PfmScope
class AccountRepository @Inject constructor(
    private val accountService: AccountService,
    dataRefreshHandler: DataRefreshHandler
) : Refreshable {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        dataRefreshHandler.registerRefreshable(this)
    }

    private val _accounts: AutoFetchLiveData<List<Account>> =
        AutoFetchLiveData {
            scope.launch {
                val accounts = try {
                    accountService.listAccounts()
                } catch (t: Throwable) {
                    emptyList<Account>()
                }
                it.postValue(accounts)
            }
        }
    val accounts: LiveData<List<Account>> = _accounts

    fun accountById(id: String): LiveData<Account?> = accounts.map { accountList ->
        accountList.firstOrNull { it.id == id }
    }

    override fun refresh() {
        _accounts.update()
    }
}