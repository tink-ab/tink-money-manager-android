package com.tink.moneymanagerui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.tink.model.account.Account
import com.tink.service.network.ResponseState
import se.tink.android.repository.account.AccountRepository
import javax.inject.Inject

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {

    val accountsState: LiveData<ResponseState<List<Account>>> = accountRepository.accountsState

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
