package com.tink.moneymanagerui.overview.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.account.Account
import se.tink.android.livedata.switchMap
import se.tink.android.repository.account.AccountRepository
import javax.inject.Inject

internal class AccountDetailsViewModel @Inject constructor(
    accountRepository: AccountRepository
): ViewModel() {

    private val accountId: MutableLiveData<String> = MutableLiveData()

    fun setAccountId(id: String) {
        accountId.postValue(id)
    }

    val account: LiveData<Account?> = accountId.switchMap { accountRepository.accountById(it) }
}