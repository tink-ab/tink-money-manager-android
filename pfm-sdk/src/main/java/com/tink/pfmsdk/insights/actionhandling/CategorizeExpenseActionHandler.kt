package com.tink.pfmsdk.insights.actionhandling

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Inject

class CategorizeExpenseActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver
) : ActionHandler {

    override fun handle(action: InsightAction, insight: Insight): Boolean =
        (action.data as? InsightAction.Data.CategorizeExpense)?.let {
            redirectionReceiver.categorizeTransaction(it.transactionId)
            true
        } ?: false
}
