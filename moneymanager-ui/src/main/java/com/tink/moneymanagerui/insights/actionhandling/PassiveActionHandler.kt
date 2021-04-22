package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import javax.inject.Inject

class PassiveActionHandler @Inject constructor() : ActionHandler {

    override fun handle(
        action: InsightAction,
        insight: Insight
    ): Boolean {
        return when (action.data) {
            is InsightAction.Data.Acknowledge,
            is InsightAction.Data.Dismiss -> {
                return true
            }
            else -> false
        }
    }
}
