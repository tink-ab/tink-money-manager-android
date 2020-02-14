package com.tink.pfmsdk.insights.actionhandling

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Inject

class CreateTransferActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver,
    actionEventBus: ActionEventBus
) : AbstractActionHandler(actionEventBus) {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.CreateTransfer)?.let {
            redirectionReceiver.showTransfer(it.sourceUri, it.destinationUri, it.amount)
            actionPerformed(action, insight)
            true
        } ?: false
}
