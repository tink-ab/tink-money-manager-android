package com.tink.moneymanagerui.accounts.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tink.model.account.Account
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.service.account.UpdateAccountDescriptor
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.network.map
import kotlinx.coroutines.launch
import se.tink.android.livedata.switchMap
import se.tink.android.repository.account.AccountRepository
import se.tink.commons.extensions.whenNonNull
import javax.inject.Inject

data class AccountDetailsEditData(
    val name: String,
    val type: Account.Type,
    val isExcluded: Boolean,
    val isFavored: Boolean,
    val isShared: Boolean
)

class AccountDetailsEditViewModel@Inject constructor(
    private val accountRepository: AccountRepository,
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val accountId: MutableLiveData<String> = MutableLiveData()

    private var initialState: AccountDetailsEditData? = null

    fun setAccountId(id: String) {
        accountId.postValue(id)
    }

    private val account = accountId.switchMap {
        accountRepository.accountByIdState(it)
    }

    val enableFields = FinanceOverviewFragment.accountEditConfiguration.fields

    val editedNameText: MutableLiveData<String> = MutableLiveData("")
    val editedType: MutableLiveData<Account.Type> = MutableLiveData(Account.Type.OTHER)
    val editedExcluded: MutableLiveData<Boolean> = MutableLiveData(false)
    val editedFavorite: MutableLiveData<Boolean> = MutableLiveData(false)
    val editedShared: MutableLiveData<Boolean> = MutableLiveData(false)

    val isUpdating: MutableLiveData<Boolean> = MutableLiveData(false)

    val accountDetailsEditData: LiveData<ResponseState<AccountDetailsEditData>> =
        MediatorLiveData<ResponseState<AccountDetailsEditData>>().apply {
            value = LoadingState

            addSource(account) { accountState ->
                value = accountState.map { account ->

                    val isShared = account.ownership.toBigDecimal().toDouble() != 1.0

                    editedNameText.postValue(account.name)
                    editedType.postValue(account.type)
                    editedExcluded.postValue(isAccountExcluded(account))
                    editedFavorite.postValue(account.favored)
                    editedShared.postValue(isShared)

                    val newState = AccountDetailsEditData(
                        name = account.name,
                        type = account.type,
                        isExcluded = isAccountExcluded(account),
                        isFavored = account.favored,
                        isShared = isShared
                    )

                    if (initialState == null) {
                        initialState = newState
                    }

                    newState
                }
                isUpdating.postValue(false)
            }

            addSource(editedNameText) { name ->
                value.let {
                    if (it is SuccessState) {
                        value = SuccessState(it.data.copy(name = name))
                    }
                }
            }

            addSource(editedType) { type ->
                value.let {
                    if (it is SuccessState) {
                        value = SuccessState(it.data.copy(type = type))
                    }
                }
            }

            addSource(editedExcluded) { isExcluded ->
                value.let {
                    if (it is SuccessState) {
                        value = SuccessState(it.data.copy(isExcluded = isExcluded))
                    }
                }
            }

            addSource(editedFavorite) { isFavorite ->
                value.let {
                    if (it is SuccessState) {
                        value = SuccessState(it.data.copy(isFavored = isFavorite))
                    }
                }
            }

            addSource(editedShared) { isShared ->
                value.let {
                    if (it is SuccessState) {
                        value = SuccessState(it.data.copy(isShared = isShared))
                    }
                }
            }
        }

    private fun isAccountExcluded(account: Account) =
        account.accountExclusion == Account.AccountExclusion.PFM_DATA

    val hasMadeChanges: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun update() {
            whenNonNull(accountDetailsEditData.value, initialState) { accountDetailsEditData, initialState ->
                value = accountDetailsEditData is SuccessState &&
                    initialState != accountDetailsEditData.data
            }
        }

        addSource(accountDetailsEditData) { update() }
    }

    val saveButtonEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun update() {
            whenNonNull(editedNameText.value, isUpdating.value, hasMadeChanges.value) {
                    editedNameText,
                    isUpdating,
                    hasMadeChanges ->
                value = editedNameText.isNotBlank() && !isUpdating && hasMadeChanges
            }
        }

        addSource(editedNameText) { update() }
        addSource(isUpdating) { update() }
        addSource(hasMadeChanges) { update() }
    }

    fun uppdateAccount() {
        val accountValue = account.value
        val editedData = accountDetailsEditData.value
        if (accountValue !is SuccessState<Account> ||
            editedData !is SuccessState<AccountDetailsEditData>
        ) return

        val ownershipPart = if (editedData.data.isShared) ExactNumber(0.5) else ExactNumber(1)

        isUpdating.postValue(true)
        viewModelScope.launch {
            val accountExclusion = if (editedData.data.isExcluded) {
                Account.AccountExclusion.PFM_DATA
            } else {
                Account.AccountExclusion.NONE
            }

            accountRepository.updateAccountState(
                UpdateAccountDescriptor(
                    id = accountValue.data.id,
                    favored = editedData.data.isFavored,
                    name = editedData.data.name,
                    ownership = ownershipPart,
                    type = editedData.data.type,
                    accountExclusion = accountExclusion
                )
            )
            statisticsRepository.refreshDelayed()
        }
    }
}
