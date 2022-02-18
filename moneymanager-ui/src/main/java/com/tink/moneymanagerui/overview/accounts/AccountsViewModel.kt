package com.tink.moneymanagerui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.account.Account
import com.tink.moneymanagerui.accounts.list.AccountGroupByKind
import com.tink.moneymanagerui.accounts.list.GroupedAccountsItem
import com.tink.service.network.ResponseState
import com.tink.service.network.map
import se.tink.android.livedata.map
import se.tink.android.repository.account.AccountRepository
import javax.inject.Inject

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {

    val accountsState: LiveData<ResponseState<List<Account>>> = accountRepository.accountsState

    val groupedAccountsState: LiveData<ResponseState<List<GroupedAccountsItem>>> = accountRepository.accountsState.map {
        it.map { accounts ->
            accounts.groupBy { account ->
                when (account.type) {
                    Account.Type.CHECKING, Account.Type.CREDIT_CARD -> AccountGroupByKind.EVERYDAY
                    Account.Type.SAVINGS, Account.Type.PENSION, Account.Type.INVESTMENT -> AccountGroupByKind.SAVINGS
                    Account.Type.MORTGAGE, Account.Type.LOAN -> AccountGroupByKind.LOANS
                    else -> AccountGroupByKind.OTHER
                }
            }.mapNotNull {
                // TODO: Load provider images correctly
                if (it.value.isNotEmpty()) {
                    GroupedAccountsItem(it.key, it.value.map { AccountWithImage(it, null) })
                } else {
                    null
                }
            }.sortedBy {
                it.accountGroup.sortOrder
            }
        }
    }

    private val _accounts: LiveData<List<Account>> = accountRepository.accounts

    // TODO: Fetch provider images here and add them to accountsWithImage
    private val images = MutableLiveData<List<Pair<String, String?>>>()

    val accountsWithImage: LiveData<List<AccountWithImage>> =
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

    val favoriteAccounts: LiveData<List<AccountWithImage>> = MediatorLiveData<List<AccountWithImage>>().apply {
        addSource(accountsWithImage) {
            value = it.filter {
                it.account.favored
            }
        }
    }

    val hasFavoriteAccount: LiveData<Boolean> = favoriteAccounts.map {
        it.isNotEmpty()
    }

    // TODO: Add logic to configure MM which account list mode to use
    val accountDetailsDisplayMode = AccountDetailsScreenType.GROUPED_ACCOUNTS_LIST
}
