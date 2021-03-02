package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import com.tink.model.insights.PerformedInsightAction
import javax.inject.Inject

interface ActionHandler {

    /**
     * @return true if the action was handled, false otherwise
     */
    fun handle(
        action: InsightAction,
        insight: Insight
    ): Boolean
}

abstract class AbstractActionHandler(
    private val actionEventBus: ActionEventBus
) : ActionHandler {

    private val userId: String = "TODO" //TODO

    fun actionPerformed(action: InsightAction, insight: Insight) =
        actionEventBus.postActionPerformed(
            PerformedInsightAction(
                insightId = insight.id,
                userId = userId,
                actionType = action.actionType
            )
        )

}

class GeneralActionHandler @Inject constructor(
    val handlers: Set<ActionHandler>,
    private val tracker: InsightsTracker
) : ActionHandler {

    override fun handle(
        action: InsightAction,
        insight: Insight
    ): Boolean {
        tracker.trackButtonPressEvent()
        return CustomInsightActionHandler.handle(action, insight)
                || handlers.any { it.handle(action, insight) }
    }

}
