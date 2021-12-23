package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import javax.inject.Inject

class RefreshCredentialActionHandler @Inject constructor() : ActionHandler {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.RefreshCredential)?.let {
            // TODO: Should make a refresh credentials call
            true
        } ?: false
}
