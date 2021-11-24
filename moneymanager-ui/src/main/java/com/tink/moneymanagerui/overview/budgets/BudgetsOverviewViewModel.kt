package com.tink.moneymanagerui.overview.budgets

import androidx.lifecycle.*
import com.tink.model.budget.Budget
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.budgets.details.isBiggerThanOrEqual
import com.tink.moneymanagerui.extensions.formattedPeriod
import se.tink.android.categories.CategoryRepository
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

    private val budgetSummaries = budgetsRepository.budgets
    private val categories = categoryRepository.categories

    val budgetItems: LiveData<List<BudgetOverviewItem>> =
        MediatorLiveData<List<BudgetOverviewItem>>().apply {
            fun update() {
                whenNonNull(
                    categories.value,
                    budgetSummaries.value
                ) { categories, budgets ->
                    _loading.postValue(false)
                    budgets.mapNotNull { summary ->
                        val budgetSpecification = summary.budgetSpecification
                        val icon = when {
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
                        val periodicity = budgetSpecification.periodicity
                        icon?.let {
                            val budgetPeriod = summary.budgetPeriod
                            BudgetOverviewItem(
                                budgetId = budgetSpecification.id,
                                icon = it,
                                name = budgetSpecification.name,
                                progress = budgetPeriod.budgetAmount.value
                                    .subtract(budgetPeriod.spentAmount.value)
                                    .absValue()
                                    .takeIf { amountLeft -> amountLeft.isBiggerThanOrEqual(EXACT_NUMBER_ZERO) }
                                    ?.toBigDecimal()
                                    ?.toInt()
                                    ?: budgetPeriod.budgetAmount.value.toBigDecimal().toInt(),
                                progressMax = budgetPeriod.budgetAmount.value.toBigDecimal().toInt(),
                                periodLabel = when (periodicity) {
                                    is Budget.Periodicity.OneOff -> periodicity.formattedPeriod(
                                        dateUtils
                                    )

                                    is Budget.Periodicity.Recurring -> periodicity.formattedPeriod(
                                        budgetPeriod,
                                        dateUtils
                                    )
                                },
                                budgetAmount = budgetPeriod.budgetAmount,
                                spentAmount = budgetPeriod.spentAmount
                            )
                        }
                    }.let(::postValue)
                }
            }
            addSource(categories) { update() }
            addSource(budgetSummaries) { update() }
        }

    private val _loading = MutableLiveData<Boolean>().apply { value = true }
    val loading: LiveData<Boolean> = _loading

    val hasBudgets: LiveData<Boolean> =
        Transformations.map(budgetItems) { it?.isNotEmpty() == true }
}
