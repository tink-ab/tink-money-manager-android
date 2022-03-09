package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class ViewAccountActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver
) : ActionHandler {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.ViewAccount)?.let {
            redirectionReceiver.showAccountDetails(it.accountId)
            true
        } ?: false
}
