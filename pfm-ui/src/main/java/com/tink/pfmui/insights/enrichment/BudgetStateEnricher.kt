package com.tink.pfmui.insights.enrichment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import se.tink.android.livedata.map
import se.tink.android.repository.budget.BudgetsRepository
import javax.inject.Inject


internal class BudgetStateEnricher @Inject constructor(
    private val budgetsRepository: BudgetsRepository
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
                            is InsightData.BudgetResultData -> getViewDetails(data.budgetId)
                            is InsightData.BudgetCloseData -> getViewDetails(data.budgetId)
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

    private fun getViewDetails(budgetId: String) =
        budgets.value
            ?.find { it.id == budgetId }
            ?.let { budget ->
                with(budget.filter) {
                    when {
                        categories.isNotEmpty() -> IconTypeViewDetails.Category(categories.first().code)
                        tags.isNotEmpty() -> IconTypeViewDetails.Tag
                        freeTextQuery.isNotEmpty() -> IconTypeViewDetails.Search
                        else -> null
                    }
                }
            }
}
