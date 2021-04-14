package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class CreateBudgetActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver
) : ActionHandler {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.CreateBudget)?.let {
            redirectionReceiver.createBudget(it.amount, it.budgetFilter, it.periodicity)
            // TODO: This should be updated as action performed after creating the budget
            true
        } ?: false
}
