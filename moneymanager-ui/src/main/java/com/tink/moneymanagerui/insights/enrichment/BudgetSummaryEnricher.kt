package com.tink.moneymanagerui.insights.enrichment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.misc.Amount
import se.tink.android.livedata.map
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.minus
import se.tink.commons.extensions.plus
import se.tink.commons.extensions.sum
import se.tink.commons.extensions.whenNonNull
import javax.inject.Inject


internal class BudgetSummaryEnricher @Inject constructor(
    budgetsRepository: BudgetsRepository,
    private val amountFormatter: AmountFormatter
) : InsightsEnricher {

    private val budgets = budgetsRepository.budgets.map {
        it.map { list -> list.budgetSpecification }
    }

    override fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>> =
        MediatorLiveData<List<Insight>>().apply {
            fun update() {
                input.value
                    ?.onEach { insight ->

                        when (val data = insight.data) {
                            is InsightData.BudgetSummaryOverspentData ->
                                dataForBudgetSummaryOverspent(data)

                            is InsightData.BudgetSummaryAchievedData ->
                                dataForBudgetSummaryAchieved(data)

                            else -> null
                        }?.let {
                            insight.viewDetails = it
                        }
                    }?.let {
                        value = it
                    }
            }

            addSource(budgets) { update() }
            addSource(input) { update() }
        }


    private fun dataForBudgetSummaryOverspent(data: InsightData.BudgetSummaryOverspentData) =
        dataForBudgetSummary(
            data.achievedBudgets,
            data.overspentBudgets,
            data.overspentAmount,
            overspent = true
        )

    private fun dataForBudgetSummaryAchieved(data: InsightData.BudgetSummaryAchievedData) =
        dataForBudgetSummary(
            data.achievedBudgets,
            data.overspentBudgets,
            data.savedAmount,
            overspent = false
        )

    private fun dataForBudgetSummary(
        achievedBudgets: List<InsightData.BudgetIdToPeriod>,
        overspentBudgets: List<InsightData.BudgetIdToPeriod>,
        differenceAmount: Amount,
        overspent: Boolean
    ): BudgetSummaryViewDetails? {

        val achievedIds = achievedBudgets.map { it.budgetId }
        val overspentIds = overspentBudgets.map { it.budgetId }

        val items = budgets.value
            ?.mapNotNull {
                val iconType =
                    with(it.filter) {
                        when {
                            categories.isNotEmpty() -> IconTypeViewDetails.Category(categories.first().code)
                            tags.isNotEmpty() -> IconTypeViewDetails.Tag
                            freeTextQuery.isNotEmpty() -> IconTypeViewDetails.Search
                            else -> return@mapNotNull null
                        }
                    }
                val budgetState =
                    when {
                        achievedIds.contains(it.id) -> BudgetState.SUCCESS
                        overspentIds.contains(it.id) -> BudgetState.OVERSPENT
                        else -> return@mapNotNull null
                    }
                BudgetSummaryDetailItem(budgetState, iconType)
            }

        val targetAmountSum = budgets.value
            ?.filter { (achievedIds + overspentIds).contains(it.id) }
            ?.map { it.amount }
            ?.takeIf { it.isNotEmpty() }
            ?.sum()

        return whenNonNull(
            items,
            targetAmountSum
        ) { budgetItems, targetAmount ->

            val progress = if (overspent) {
                1.0
            } else {
                (targetAmount - differenceAmount).value.doubleValue() / targetAmount.value.doubleValue()
            }

            val spentAmount =
                if (overspent) {
                    targetAmount + differenceAmount
                } else {
                    targetAmount - differenceAmount
                }

            val targetAmountText = amountFormatter.format(targetAmount)
            val spentAmountText = amountFormatter.format(spentAmount)

            BudgetSummaryViewDetails(
                budgetItems,
                targetAmountText,
                spentAmountText,
                progress
            )
        }
    }
}
