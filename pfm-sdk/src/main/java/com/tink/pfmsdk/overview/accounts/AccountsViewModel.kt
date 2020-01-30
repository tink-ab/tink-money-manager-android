package com.tink.pfmsdk.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import se.tink.android.repository.account.AccountRepository
import se.tink.core.models.account.Account
import javax.inject.Inject

class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {
    val accounts: LiveData<List<Account>?> = accountRepository.accounts()

    val loading = MediatorLiveData<Boolean>().apply {
        addSource(accounts) { postValue(it == null) }
    }
}