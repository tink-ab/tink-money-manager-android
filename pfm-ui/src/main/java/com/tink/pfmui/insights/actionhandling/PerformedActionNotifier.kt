package com.tink.pfmui.insights.actionhandling

import se.tink.repository.SimpleMutationHandler
import se.tink.repository.TinkNetworkError
import se.tink.repository.service.InsightService
import javax.inject.Inject

class PerformedActionNotifier @Inject constructor(
    eventBus: ActionEventBus,
    private val insightService: InsightService
) {

    init {
        eventBus.subscribe { performedAction ->
            insightService.selectAction(
                performedAction,
                object : SimpleMutationHandler<Unit>() {
                    override fun onError(error: TinkNetworkError?) {
                        //TODO
                    }
                })
        }
    }
}