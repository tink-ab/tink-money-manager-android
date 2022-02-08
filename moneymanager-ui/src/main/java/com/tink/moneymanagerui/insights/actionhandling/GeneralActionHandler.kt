package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import com.tink.model.insights.PerformedInsightAction
import javax.inject.Inject


class GeneralActionHandler @Inject constructor(
    val handlers: Set<ActionHandler>,
    private val tracker: InsightsTracker,
    private val actionEventBus: ActionEventBus
) : ActionHandler {

    override fun handle(
        action: InsightAction,
        insight: Insight
    ): Boolean {
        tracker.trackButtonPressEvent()
        if (CustomInsightActionHandler.canHandleAction(action)) {
            // Handle the insight action using the custom insight action handler
            val isHandled = CustomInsightActionHandler.handle(action, insight) { isActionDone ->
                if (isActionDone) {
                    actionPerformed(action, insight)
                }
            }
            // Attempt to handle the insight action using default handlers, if applicable
            if (!isHandled) {
                performDefaultHandling(action, insight)
            }
        } else {
            // Attempt to handle the insight action using default handlers, if applicable
            performDefaultHandling(action, insight)
        }
        return true
    }

    private fun performDefaultHandling(action: InsightAction, insight: Insight) {
        handlers
            .any {
                if (it.handle(action, insight)) {
                    actionPerformed(action, insight)
                    true
                } else {
                    false
                }
            }
    }

    private fun actionPerformed(action: InsightAction, insight: Insight) =
        actionEventBus.postActionPerformed(
            PerformedInsightAction(
                insightId = insight.id,
                userId = "", // Not supported currently
                actionType = action.actionType
            )
        )

}