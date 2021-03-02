package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

class CreateTransferActionHandler @Inject constructor(
    private val redirectionReceiver: RedirectionReceiver,
    actionEventBus: ActionEventBus
) : AbstractActionHandler(actionEventBus) {
    override fun handle(action: InsightAction, insight: Insight) =
        (action.data as? InsightAction.Data.CreateTransfer)?.let {
            // TODO: PFMSDK: Implement this when we support transfers in the SDK
//            redirectionReceiver.showTransfer(it.sourceUri, it.destinationUri, it.amount)
//            actionPerformed(action, insight)
//            true
            false
        } ?: false
}
