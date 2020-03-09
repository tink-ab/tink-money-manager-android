package com.tink.pfmsdk.insights.actionhandling

import se.tink.core.models.misc.Amount

open class InsightActionHandler {
    open fun viewBudget(budgetId: String, periodStart: String): Boolean = false

    open fun categorizeTransaction(transactionId: String): Boolean = false

    open fun viewTransactions(transactionIds: List<String>): Boolean = false

    open fun viewTransactionsByCategory(
        transactionsByCategory: Map<String, List<String>>
    ): Boolean = false

    open fun showTransfer(
        sourceUri: String,
        destinationUri: String,
        amount: Amount
    ): Boolean = false
}