package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class ViewTransactionsByCategoryActionHandler @Inject constructor(
    actionEventBus: ActionEventBus,
    private val redirectionReceiver: RedirectionReceiver
) : AbstractActionHandler(actionEventBus) {

    override fun handle(action: InsightAction, insight: Insight): Boolean =
        (action.data as? InsightAction.Data.ViewTransactionsByCategory)?.run {
            // Flatten the lists of transactions IDs into one list and show it
            transactionsByCategory.values
                .flatten()
                .takeIf { it.isNotEmpty() }
                ?.let { redirectionReceiver.showTransactionListForIds(it) }
            actionPerformed(action, insight)
            true
        } ?: false
}
