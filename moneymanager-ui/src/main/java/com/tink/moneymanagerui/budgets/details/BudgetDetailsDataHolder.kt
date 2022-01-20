package com.tink.moneymanagerui.budgets.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriod
import com.tink.moneymanagerui.extensions.formattedPeriod
import se.tink.commons.extensions.minus
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils

internal class BudgetDetailsDataHolder(
    private val budgetSelectionController: BudgetSelectionController,
    private val dateUtils: DateUtils
) {
    val budget get() = budgetSelectionController.budgetSpecification

    val budgetPeriod get() = budgetSelectionController.currentSelectedPeriod

    val budgetPeriodsList get() = budgetSelectionController.budgetPeriodsList

    val error get() = budgetSelectionController.error

    val budgetName: LiveData<String> = Transformations.map(budget) { it.name }

    val amountLeft: LiveData<Float> =
        Transformations.map(budgetPeriod) { budgetPeriod ->
            (budgetPeriod.budgetAmount - budgetPeriod.spentAmount).value.toBigDecimal().toFloat()
        }

    val budgetPeriodInterval: LiveData<String> = MediatorLiveData<String>().apply {
        fun updateInterval() {
            whenNonNull(
                budget.value,
                budgetPeriod.value
            ) { budget, budgetPeriod ->
                val periodicity = budget.periodicity
                value = when (periodicity) {
                    is Budget.Periodicity.OneOff -> periodicity.formattedPeriod(dateUtils)
                    is Budget.Periodicity.Recurring -> periodicity.formattedPeriod(budgetPeriod, dateUtils)
                }
            }
        }
        addSource(budget) { updateInterval() }
        addSource(budgetPeriod) { updateInterval() }
    }

    val hasNext: LiveData<Boolean> = createPeriodPickerStateLiveData { currentPeriod ->
        currentPeriod != budgetSelectionController.budgetPeriods.last()
    }
    val hasPrevious: LiveData<Boolean> = createPeriodPickerStateLiveData { currentPeriod ->
        currentPeriod != budgetSelectionController.budgetPeriods.first()
    }

    private inline fun createPeriodPickerStateLiveData(
        crossinline isVisible: (BudgetPeriod) -> Boolean
    ): LiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(budgetSelectionController.currentSelectedPeriod) { budgetPeriod ->
                value = budgetPeriod?.let { isVisible(budgetPeriod) } ?: false
            }
        }
    }

    fun nextPeriod() = budgetSelectionController.next()
    fun previousPeriod() = budgetSelectionController.previous()

}