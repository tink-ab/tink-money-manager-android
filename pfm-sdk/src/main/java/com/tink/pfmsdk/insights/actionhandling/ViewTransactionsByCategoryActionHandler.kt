package com.tink.pfmsdk.insights.actionhandling

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Inject

class ViewTransactionsByCategoryActionHandler @Inject constructor(
    actionEventBus: ActionEventBus,
    private val redirectionReceiver: RedirectionReceiver
) : AbstractActionHandler(actionEventBus) {

    override fun handle(action: InsightAction, insight: Insight): Boolean =
        (action.data as? InsightAction.Data.ViewTransactionsByCategory)?.run {
            redirectionReceiver.showCategory(true)
            actionPerformed(action, insight)
            true
        } ?: false
}
