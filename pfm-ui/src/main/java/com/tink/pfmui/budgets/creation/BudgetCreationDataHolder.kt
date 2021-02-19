package com.tink.pfmui.budgets.creation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.tink.model.budget.Budget
import com.tink.model.misc.Amount

internal class BudgetCreationDataHolder {
    val id: MutableLiveData<String?> = MutableLiveData()
    val name: MutableLiveData<String?> = MutableLiveData()
    val amount: MediatorLiveData<Amount?> = MediatorLiveData()
    val selectedFilter: MediatorLiveData<Budget.Specification.Filter?> = MediatorLiveData()
    val periodicity: MediatorLiveData<Budget.Periodicity?> = MediatorLiveData()
}