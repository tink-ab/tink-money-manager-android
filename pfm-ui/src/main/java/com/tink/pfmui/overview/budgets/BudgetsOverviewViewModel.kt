package com.tink.pfmui.overview.budgets

import android.content.Context
import androidx.lifecycle.*
import com.tink.model.budget.Budget
import com.tink.model.misc.Amount
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.pfmui.budgets.details.isBiggerThanOrEqual
import com.tink.pfmui.extensions.formattedPeriod
import com.tink.pfmui.util.extensions.formatCurrencyRound
import com.tink.pfmui.util.extensions.formatCurrencyRoundWithoutSign
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
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
    private val dateUtils: DateUtils,
    @ApplicationScoped private val context: Context
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
                                resource = R.drawable.tink_transaction_tag,
                                color = R.attr.tink_transferColor,
                                backgroundColor = R.attr.tink_transferLightColor
                            )

                            budgetSpecification.filter.freeTextQuery.isNotEmpty() -> BudgetOverviewItem.Icon(
                                resource = R.drawable.tink_search,
                                color = R.attr.tink_transferColor,
                                backgroundColor = R.attr.tink_transferLightColor
                            )

                            budgetSpecification.filter.categories.isNotEmpty() -> categories
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
                                spentDescription = composeSpentString(
                                    budgetPeriod.spentAmount,
                                    budgetPeriod.budgetAmount
                                ),
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
                                overspent = budgetPeriod.run { spentAmount > budgetAmount }
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

    private fun composeSpentString(spentAmount: Amount, budgetAmount: Amount): String {
        val delta = budgetAmount - spentAmount
        return if (delta.value.isSmallerThan(EXACT_NUMBER_ZERO)) {
            context.getString(R.string.tink_overview_budget_over)
                .format(delta.formatCurrencyRoundWithoutSign())
        } else {
            context.getString(R.string.tink_overview_budget_left_of)
                .format(delta.formatCurrencyRound(), budgetAmount.formatCurrencyRound())
        }
    }
}
