package com.tink.pfmsdk.insights.actionhandling

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Inject

class ViewTransactionsActionHandler @Inject constructor(
    actionEventBus: ActionEventBus,
    private val redirectionReceiver: RedirectionReceiver
) : AbstractActionHandler(actionEventBus) {

    override fun handle(action: InsightAction, insight: Insight): Boolean =
        (action.data as? InsightAction.Data.ViewTransactions)?.run {
            when {
                transactionIds.isEmpty() -> return@run false
                transactionIds.size == 1 -> redirectionReceiver.showTransactionDetails(
                    transactionIds.first()
                )
                else -> redirectionReceiver.showTransactionListForIds(transactionIds)
            }
            actionPerformed(action, insight)
            true
        } ?: false
}
