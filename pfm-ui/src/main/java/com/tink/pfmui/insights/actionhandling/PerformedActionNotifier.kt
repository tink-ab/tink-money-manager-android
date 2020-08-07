package com.tink.pfmui.insights.actionhandling

import com.tink.service.insight.InsightService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class PerformedActionNotifier @Inject constructor(
    eventBus: ActionEventBus,
    private val insightService: InsightService
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        eventBus.subscribe { performedAction ->
            scope.launch {
                try {
                    insightService.selectAction(performedAction)
                } catch (error: Throwable) {
                    // TODO
                }
            }
        }
    }
}