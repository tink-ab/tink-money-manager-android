package com.tink.pfmsdk.insights.actionhandling

import com.tink.pfmsdk.extensions.isTrue
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import se.tink.core.models.insights.PerformedInsightAction
import javax.inject.Inject

// TODO: PFMSDK: Make this interface internal
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
                userId = userId
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
        if (!CustomInsightActionHandler.handle(action, insight).isTrue()) {
            handlers.any { it.handle(action, insight) }
        }
        return false
    }

}
