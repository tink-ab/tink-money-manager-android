package com.tink.pfmui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import se.tink.android.repository.account.AccountRepository
import se.tink.core.models.account.Account
import javax.inject.Inject

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {
    val accounts: LiveData<List<Account>> = accountRepository.accounts()

    val loading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(accounts) { postValue(it == null) }
    }
}
