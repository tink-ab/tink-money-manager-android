package se.tink.android.repository.account

import androidx.lifecycle.LiveData
import com.tink.annotations.PfmScope
import com.tink.model.account.Account
import com.tink.service.account.AccountService
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.map
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import javax.inject.Inject

@PfmScope
class AccountRepository @Inject constructor(
    private val accountService: AccountService,
    dataRefreshHandler: DataRefreshHandler,
    private val dispatcher: DispatcherProvider
) : Refreshable {

    init {
        dataRefreshHandler.registerRefreshable(this)
    }

    private val _accounts: AutoFetchLiveData<List<Account>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                val accounts = try {
                    accountService.listAccounts()
                } catch (t: Throwable) {
                    emptyList()
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
        _accountsState.update()
    }

    private val _accountsState: AutoFetchLiveData<ResponseState<List<Account>>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                val accounts: ResponseState<List<Account>> = try {
                    val accounts = accountService.listAccounts()
                    SuccessState(accounts)
                } catch (t: Throwable) {
                    ErrorState(t)
                }
                it.postValue(accounts)
            }
        }
    val accountsState: LiveData<ResponseState<List<Account>>> = _accountsState

    fun accountByIdState(id: String): LiveData<ResponseState<Account?>> = accountsState.map { accountList ->
        when(accountList) {
            is LoadingState -> LoadingState
            is ErrorState -> ErrorState(accountList.errorMessage)
            is SuccessState -> SuccessState(
                    accountList.data.firstOrNull {
                    it.id == id
                }
            )
        }
    }
}