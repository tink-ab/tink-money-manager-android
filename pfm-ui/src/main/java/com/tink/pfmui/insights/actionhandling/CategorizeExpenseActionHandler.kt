package com.tink.pfmui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
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
