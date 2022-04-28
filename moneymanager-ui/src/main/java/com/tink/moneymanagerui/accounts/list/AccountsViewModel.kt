package com.tink.moneymanagerui.accounts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.account.Account
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.accounts.AccountGroupable
import com.tink.moneymanagerui.accounts.AccountWithImage
import com.tink.moneymanagerui.extensions.mapState
import com.tink.moneymanagerui.util.EspressoIdlingResource
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.network.map
import se.tink.android.livedata.map
import se.tink.android.repository.account.AccountRepository
import se.tink.android.repository.provider.ProviderRepository
import se.tink.commons.extensions.whenNonNull
import javax.inject.Inject

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository,
    providerRepository: ProviderRepository
) : ViewModel() {

    private val accountsState: LiveData<ResponseState<List<Account>>> = accountRepository.accountsState

    private val _accountIdToImageState: LiveData<ResponseState<Map<String, String?>>> =
        providerRepository.accountIdToImagesState

    val accountsWithImageState: LiveData<ResponseState<List<AccountWithImage>>> =
        MediatorLiveData<ResponseState<List<AccountWithImage>>>().apply {
            fun update() {
                whenNonNull(accountsState.value, _accountIdToImageState.value) { accountsStateValue, accountIdToImageStateValue ->
                    postValue(
                        mapState(accountsStateValue, accountIdToImageStateValue) { accounts, idToImage ->
                            accounts.map { account ->
                                AccountWithImage(account, idToImage[account.financialInstitutionID])
                            }
                        }
                    )
                }
            }

            addSource(accountsState) { update() }
            addSource(_accountIdToImageState) { update() }
        }

    val groupedAccountsState: LiveData<ResponseState<List<GroupedAccountsItem>>> = accountsWithImageState.map {
            accountState ->
        accountState.map { accounts ->
            (FinanceOverviewFragment.accountGroupType as? AccountGroupable)
                ?.groupAccounts(accounts)
                ?: emptyList()
        }
    }

    val overviewAccounts: LiveData<ResponseState<List<AccountWithImage>>> = MediatorLiveData<ResponseState<List<AccountWithImage>>>().apply {
        addSource(accountsWithImageState) { accountsWithImage ->
            value = accountsWithImage.map { accounts ->
                accounts.filter {
                    overviewAccountsMode.overviewAccountsFilter.isIncludedInOverview(it)
                }
            }
            EspressoIdlingResource.decrement()
        }
    }

    val hasOverviewAccount: LiveData<Boolean> = overviewAccounts.map {
        it is SuccessState && it.data.isNotEmpty()
    }

    val accountDetailsViewMode = FinanceOverviewFragment.accountGroupType

    private val overviewAccountsMode = FinanceOverviewFragment.overviewAccountsMode
}

fun Account.Type.toAccountGroup(): AccountGroup =
    when (this) {
        Account.Type.CHECKING,
        Account.Type.CREDIT_CARD -> EVERYDAY_ACCOUNTS
        Account.Type.SAVINGS,
        Account.Type.PENSION,
        Account.Type.INVESTMENT -> SAVINGS_ACCOUNTS
        Account.Type.MORTGAGE,
        Account.Type.LOAN -> LOANS_ACCOUNTS
        Account.Type.UNKNOWN,
        Account.Type.EXTERNAL,
        Account.Type.OTHER -> OTHER_ACCOUNTS
    }
