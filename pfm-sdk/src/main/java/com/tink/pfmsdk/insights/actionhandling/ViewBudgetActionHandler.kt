package com.tink.pfmsdk.insights.actionhandling

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Inject

class ViewBudgetActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver,
    actionEventBus: ActionEventBus
) : AbstractActionHandler(actionEventBus) {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.ViewBudget)?.let {
            redirectionReceiver.showBudget(it.budgetId, it.periodStartDate.toString())
            actionPerformed(action, insight)
            true
        } ?: false
}
