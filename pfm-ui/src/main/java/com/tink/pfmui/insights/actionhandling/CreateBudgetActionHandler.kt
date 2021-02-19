package com.tink.pfmui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class CreateBudgetActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver,
    actionEventBus: ActionEventBus
) : AbstractActionHandler(actionEventBus) {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.CreateBudget)?.let {
            redirectionReceiver.createBudget(it.amount, it.budgetFilter, it.periodicity)
            // TODO: This should be updated as action performed after creating the budget
            actionPerformed(action, insight)
            true
        } ?: false
}
