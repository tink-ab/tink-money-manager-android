package com.tink.moneymanagerui.insights.actionhandling

import com.tink.service.insight.InsightService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PerformedActionNotifier @Inject constructor(
    eventBus: ActionEventBus,
    private val insightService: InsightService,
    private val dispatcher: DispatcherProvider,
) {
    init {
        eventBus.subscribe { performedAction ->
            CoroutineScope(dispatcher.io()).launch {
                try {
                    insightService.selectAction(performedAction)
                } catch (error: Throwable) {
                    // TODO
                }
            }
        }
    }
}