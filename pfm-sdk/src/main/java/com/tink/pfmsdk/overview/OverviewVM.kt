package com.tink.pfmsdk.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.tink.android.AppExecutors
import se.tink.android.livedata.createChangeObserver
import com.tink.pfmsdk.repository.CredentialRepository
import com.tink.pfmsdk.repository.ProviderRepository
import se.tink.core.models.transaction.Transaction
import se.tink.repository.service.TransactionService
import javax.inject.Inject

class OverviewVM @Inject constructor(
    private val providerRepository: ProviderRepository,
    private val credentialRepository: CredentialRepository,
    private val transactionService: TransactionService,
    appExecutors: AppExecutors
) : ViewModel() {


    private val _transactions = MutableLiveData<List<Transaction>>()

    private val transactionChangeObserver = _transactions.createChangeObserver(appExecutors).also {
        transactionService.listAndSubscribeForLatestTransactions(false, it)
    }

    val transactions: LiveData<List<Transaction>> = MediatorLiveData<List<Transaction>>().apply {
        fun update() = _transactions.value?.let { list ->
            value = list.sorted().take(3)
        }
        addSource(_transactions) { update() }
    }

    override fun onCleared() {
        transactionService.unsubscribe(transactionChangeObserver)
    }
}
