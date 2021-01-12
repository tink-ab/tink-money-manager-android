package com.tink.pfmui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class ViewTransactionsActionHandler @Inject constructor(
    actionEventBus: ActionEventBus,
    private val redirectionReceiver: RedirectionReceiver
) : AbstractActionHandler(actionEventBus) {

    override fun handle(action: InsightAction, insight: Insight): Boolean =
        (action.data as? InsightAction.Data.ViewTransactions)?.run {
            when {
                transactionIds.isEmpty() -> return@run false
                // TODO: PFMSDK: Show the list of transactions until we have a transaction details screen
//                transactionIds.size == 1 -> redirectionReceiver.showTransactionDetails(
//                    transactionIds.first()
//                )
                else -> redirectionReceiver.showTransactionListForIds(transactionIds)
            }
            actionPerformed(action, insight)
            true
        } ?: false
}
