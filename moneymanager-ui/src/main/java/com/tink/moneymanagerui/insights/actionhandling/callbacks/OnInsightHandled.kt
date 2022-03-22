package com.tink.moneymanagerui.insights.actionhandling.callbacks

/**
 * Interface definition for a callback to be invoked insight has been handled
 */
fun interface OnInsightHandled {
    /**
     * Called when an insight has been handled
     *
     * @param isActionHandled indicates whether the insight action has been handled
     */
    fun onActionHandled(isActionHandled: Boolean)
}
