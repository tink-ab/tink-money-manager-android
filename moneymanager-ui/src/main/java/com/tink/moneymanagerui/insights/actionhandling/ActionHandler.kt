package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction

interface ActionHandler {

    /**
     * @param action The [InsightAction] that needs to be handled
     * @param insight The [Insight] for which the action is being handled
     */
    fun handle(
        action: InsightAction,
        insight: Insight
    ): Boolean
}
