package com.tink.moneymanagerui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.account.Account
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.accounts.AccountGroupable
import com.tink.moneymanagerui.accounts.list.AccountGroup
import com.tink.moneymanagerui.accounts.list.EVERYDAY_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem
import com.tink.moneymanagerui.accounts.list.LOANS_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.OTHER_ACCOUNTS
import com.tink.moneymanagerui.accounts.list.SAVINGS_ACCOUNTS
import com.tink.service.network.ResponseState
import com.tink.service.network.map
import javax.inject.Inject
import se.tink.android.livedata.map
import se.tink.android.repository.account.AccountRepository

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {

    val accountsState: LiveData<ResponseState<List<Account>>> = accountRepository.accountsState

    val groupedAccountsState: LiveData<ResponseState<List<GroupedAccountsItem>>> = accountRepository.accountsState.map {
        accountState -> accountState.map { accounts ->
            (FinanceOverviewFragment.accountGroupType as? AccountGroupable)
                ?.groupAccounts(accounts)
                ?: emptyList()
        }
    }

    private val _accounts: LiveData<List<Account>> = accountRepository.accounts

    // TODO: Fetch provider images here and add them to accountsWithImage
    private val images = MutableLiveData<List<Pair<String, String?>>>()

    private val accountsWithImage: LiveData<List<AccountWithImage>> =
        MediatorLiveData<List<AccountWithImage>>().apply {

            addSource(_accounts) { newAccounts ->
                postValue(newAccounts.map { account ->
                    val accountImage =
                        images.value?.firstOrNull { it.first == account.financialInstitutionID }?.second
                    AccountWithImage(account, accountImage)
                })
            }

            addSource(images) { providerImages ->
                postValue(_accounts.value?.map { account ->
                    val accountImage =
                        providerImages?.firstOrNull { it.first == account.financialInstitutionID }?.second
                    AccountWithImage(account, accountImage)
                })
            }
        }

    val overviewAccounts: LiveData<List<AccountWithImage>> = MediatorLiveData<List<AccountWithImage>>().apply {
        addSource(accountsWithImage) { accountsWithImage ->
            value = accountsWithImage.filter {
                overviewAccountsMode.overviewAccountsFilter.isIncludedInOverview(it)
            }
        }
    }

    val hasOverviewAccount: LiveData<Boolean> = overviewAccounts.map {
        it.isNotEmpty()
    }

    val areAllAccountsOnOverview: LiveData<Boolean> = accountsWithImage.map { accounts ->
        val overviewAccountsSize = accounts.filter {
            overviewAccountsMode.overviewAccountsFilter.isIncludedInOverview(it)
        }.size
        accounts.size == overviewAccountsSize
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
