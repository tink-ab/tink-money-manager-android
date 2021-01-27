package com.tink.pfmui.budgets.details.transactions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.account.Account
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetTransaction
import com.tink.model.category.CategoryTree
import com.tink.pfmui.R
import com.tink.pfmui.budgets.details.BudgetDetailsDataHolder
import com.tink.pfmui.util.extensions.formatCurrencyRound
import org.threeten.bp.Instant
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.repository.TinkNetworkError
import se.tink.android.repository.account.AccountRepository
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.commons.extensions.*
import se.tink.commons.transactions.ListItem.TransactionItem
import se.tink.commons.transactions.TransactionItemFactory
import javax.inject.Inject

internal class BudgetTransactionListViewModel @Inject constructor(
    private val budgetDetailsDataHolder: BudgetDetailsDataHolder,
    private val transactionItemFactory: TransactionItemFactory,
    budgetRepository: BudgetsRepository,
    categoryRepository: CategoryRepository,
    accountRepository: AccountRepository,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val categoryTree: LiveData<CategoryTree> = categoryRepository.categories
    private val accounts: LiveData<List<Account>> = accountRepository.accounts

    private val budgetTransactionParams: LiveData<BudgetTransactionParams> =
        MediatorLiveData<BudgetTransactionParams>().apply {
            fun update() {
                whenNonNull(
                    budgetDetailsDataHolder.budget.value,
                    budgetDetailsDataHolder.budgetPeriod.value
                ) { budget, budgetPeriod ->
                    value = BudgetTransactionParams(budget.id, budgetPeriod.start, budgetPeriod.end)
                }
            }
            addSource(budgetDetailsDataHolder.budget) { update() }
            addSource(budgetDetailsDataHolder.budgetPeriod) { update() }
        }

    private val budgetTransactionErrorOrValue: LiveData<ErrorOrValue<List<BudgetTransaction>>> =
        Transformations.switchMap(budgetTransactionParams) {
            budgetRepository.transactions(it.id, it.start, it.end)
        }

    val transactionItems: MediatorLiveData<List<TransactionItem>> =
        MediatorLiveData<List<TransactionItem>>().apply {
            fun update() {
                whenNonNull(
                    categoryTree.value,
                    budgetTransactionErrorOrValue.value?.value,
                    accounts.value
                ) { categoryTree, budgetTransactions, _ ->
                    value = budgetTransactions.mapNotNull { budgetTransaction ->
                        categoryTree
                            .findCategoryByCode(budgetTransaction.categoryCode)
                            ?.let { category ->
                                with(budgetTransaction) {
                                    transactionItemFactory.createItem(
                                        id = id,
                                        isUpcoming = false,
                                        category = category,
                                        date = this.date.toDateTime(),
                                        label = description,
                                        amount = amount,
                                        upcomingTransactionData = null
                                    )
                                }
                            }
                    }
                }
            }
            addSource(budgetTransactionErrorOrValue) { update() }
            addSource(categoryTree) { update() }
            addSource(accounts) { update() }
        }

    val error: MediatorLiveData<TinkNetworkError> = MediatorLiveData<TinkNetworkError>().apply {
        fun checkForError() {
            budgetTransactionErrorOrValue.value?.error?.let {
                value = it
            }
        }
        addSource(budgetTransactionErrorOrValue) { checkForError() }
    }

    val loading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(transactionItems) { value = false }
        addSource(error) { value = false }
    }

    val emptyState: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(transactionItems) { transactionItems ->
            value = transactionItems?.isEmpty() == true
        }
    }

    val showPicker: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun updatePickerState() {
            whenNonNull(
                budgetDetailsDataHolder.budget.value,
                loading.value
            ) { budget, loading ->
                value = budget.periodicity is Budget.Periodicity.Recurring && !loading
            }
        }
        addSource(budgetDetailsDataHolder.budget) { updatePickerState() }
        addSource(loading) { updatePickerState() }
    }

    val budgetName get() = budgetDetailsDataHolder.budgetName
    val budgetPeriodInterval get() = budgetDetailsDataHolder.budgetPeriodInterval
    val amountLeft get() = budgetDetailsDataHolder.amountLeft
    val hasNext get() = budgetDetailsDataHolder.hasNext
    val hasPrevious get() = budgetDetailsDataHolder.hasPrevious

    fun showNextPeriod() = budgetDetailsDataHolder.nextPeriod()
    fun showPreviousPeriod() = budgetDetailsDataHolder.previousPeriod()

    val amountLeftColor: LiveData<Int> =
        Transformations.map(amountLeft) {
            if (it > 0) {
                R.attr.tink_expensesColor
            } else {
                R.attr.tink_warningColor
            }
        }

    val amountSpent: LiveData<Int> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            budgetPeriod.spentAmount.value.toBigDecimal().intValueExact()
        }

    val totalAmount: LiveData<Int> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            budgetPeriod.budgetAmount.value.toBigDecimal().intValueExact()
        }

    private val totalAmountStr: LiveData<String> =
        Transformations.map(budgetDetailsDataHolder.budgetPeriod) { budgetPeriod ->
            budgetPeriod.budgetAmount.formatCurrencyRound()?.let {
                context.getString(R.string.tink_budget_details_total_amount, it)
            }
        }

    val budgetProgressStr: MediatorLiveData<String> = MediatorLiveData<String>().apply {
        fun updateText() {
            whenNonNull(
                totalAmountStr.value,
                budgetDetailsDataHolder.budgetPeriod.value
            ) { leftOfTotalAmountStr, budgetPeriod ->
                value =
                    "${(budgetPeriod.budgetAmount - budgetPeriod.spentAmount).formatCurrencyRound()} $leftOfTotalAmountStr"
            }
        }
        addSource(totalAmountStr) { updateText() }
        addSource(budgetDetailsDataHolder.budgetPeriod) { updateText() }
    }
}

data class BudgetTransactionParams(
    val id: String,
    val start: Instant,
    val end: Instant
)