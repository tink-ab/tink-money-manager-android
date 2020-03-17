package com.tink.pfmui.insights.actionhandling

import com.tink.service.handler.ResultHandler
import com.tink.service.insight.InsightService
import javax.inject.Inject

class PerformedActionNotifier @Inject constructor(
    eventBus: ActionEventBus,
    private val insightService: InsightService
) {
    init {
        eventBus.subscribe { performedAction ->
            insightService.selectAction(
                performedAction,
                ResultHandler({}, {
                    //TODO
                })
            )
        }
    }
}