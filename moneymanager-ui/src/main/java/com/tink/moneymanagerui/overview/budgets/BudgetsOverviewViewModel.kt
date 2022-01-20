package com.tink.moneymanagerui.overview.budgets

import androidx.lifecycle.*
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetSummary
import com.tink.model.category.CategoryTree
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.budgets.details.isBiggerThanOrEqual
import com.tink.moneymanagerui.extensions.formattedPeriod
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import se.tink.android.categories.CategoryRepository
import se.tink.android.livedata.requireValue
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.extensions.*
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class BudgetsOverviewViewModel @Inject constructor(
    budgetsRepository: BudgetsRepository,
    categoryRepository: CategoryRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    private val budgetSummaries = budgetsRepository.budgetsState
    private val categories = categoryRepository.categoriesState

    private val budgetItemsState: MediatorLiveData<BudgetsState> =
        MediatorLiveData<BudgetsState>().apply {
            value = BudgetsState()

            addSource(categories) {
                value = BudgetsState(requireValue.budgets, it)
            }
            addSource(budgetSummaries) {
                value = BudgetsState(it, requireValue.categories)
            }
        }

    val overallBudgetState: LiveData<ResponseState<List<BudgetOverviewItem>>> = budgetItemsState.map {
        it.overallState
    }

    private fun getBudgetIcon(
        budgetSpecification: Budget.Specification,
        categories: CategoryTree,
    ) = when {
        budgetSpecification.filter.tags.isNotEmpty() -> BudgetOverviewItem.Icon(
            resource = R.attr.tink_icon_transaction_tag,
            color = R.attr.tink_expensesIconColor,
            backgroundColor = R.attr.tink_expensesIconBackgroundColor
        )

        budgetSpecification.filter.freeTextQuery.isNotEmpty() -> BudgetOverviewItem.Icon(
            resource = R.attr.tink_icon_category_search,
            color = R.attr.tink_expensesIconColor,
            backgroundColor = R.attr.tink_expensesIconBackgroundColor
        )

        budgetSpecification.filter.categories.size > 1 -> {
            BudgetOverviewItem.Icon(
                resource = R.attr.tink_icon_category_all_expenses,
                color = R.attr.tink_expensesIconColor,
                backgroundColor = R.attr.tink_expensesIconBackgroundColor
            )
        }

        budgetSpecification.filter.categories.size == 1 -> categories
            .findCategoryByCode(
                budgetSpecification.filter.categories.first().code
            )
            ?.let { category ->
                BudgetOverviewItem.Icon(
                    resource = category.getIcon(),
                    color = category.iconColor(),
                    backgroundColor = category.iconBackgroundColor()
                )
            }

        else -> null
    }

    private fun getBudgetOverviewItemProgress(budgetPeriod: Budget.Period): Int {
        return budgetPeriod.budgetAmount.value
            .subtract(budgetPeriod.spentAmount.value)
            .absValue()
            .takeIf { amountLeft -> amountLeft.isBiggerThanOrEqual(EXACT_NUMBER_ZERO) }
            ?.toBigDecimal()
            ?.toInt()
            ?: budgetPeriod.budgetAmount.value.toBigDecimal().toInt()
    }

    private fun getBudgetOverviewItemPeriodLabel(
        periodicity: Budget.Periodicity,
        budgetPeriod: Budget.Period,
    ): String {
        return when (periodicity) {
            is Budget.Periodicity.OneOff -> periodicity.formattedPeriod(
                dateUtils
            )

            is Budget.Periodicity.Recurring -> periodicity.formattedPeriod(
                budgetPeriod,
                dateUtils
            )
        }
    }

    inner class BudgetsState(
        val budgets: ResponseState<List<BudgetSummary>> = LoadingState,
        val categories: ResponseState<CategoryTree> = LoadingState
    ) {

        internal val overallState: ResponseState<List<BudgetOverviewItem>> =
            when {
                budgets is SuccessState && categories is SuccessState -> {

                    val budgetOverviews: List<BudgetOverviewItem> = budgets.data.mapNotNull { summary ->
                        val budgetSpecification = summary.budgetSpecification
                        val icon = getBudgetIcon(budgetSpecification, categories.data)
                        val periodicity = budgetSpecification.periodicity

                        icon?.let {
                            val budgetPeriod = summary.budgetPeriod
                            BudgetOverviewItem(
                                budgetId = budgetSpecification.id,
                                icon = it,
                                name = budgetSpecification.name,
                                progress = getBudgetOverviewItemProgress(budgetPeriod),
                                progressMax = budgetPeriod.budgetAmount.value.toBigDecimal().toInt(),
                                periodLabel = getBudgetOverviewItemPeriodLabel(
                                    periodicity,
                                    budgetPeriod
                                ),
                                budgetAmount = budgetPeriod.budgetAmount,
                                spentAmount = budgetPeriod.spentAmount
                            )
                        }
                    }
                    SuccessState(budgetOverviews)
                }
                budgets is ErrorState || categories is ErrorState -> ErrorState("")
                else -> LoadingState
            }
    }
}
