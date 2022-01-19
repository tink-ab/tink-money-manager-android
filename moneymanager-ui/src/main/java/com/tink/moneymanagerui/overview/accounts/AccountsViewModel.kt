package com.tink.moneymanagerui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import se.tink.android.repository.account.AccountRepository
import com.tink.model.account.Account
import com.tink.service.network.ResponseState
import javax.inject.Inject

internal class AccountsViewModel @Inject constructor(
    accountRepository: AccountRepository
) : ViewModel() {

    val accountsState: LiveData<ResponseState<List<Account>>> = accountRepository.accountsState
}
