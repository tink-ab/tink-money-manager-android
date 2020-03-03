package com.tink.pfmsdk.insights.actionhandling

import se.tink.core.models.misc.Amount

open class InsightActionHandler {
    fun viewBudget(budgetId: String, periodStart: String): Boolean = false

    fun categorizeTransaction(transactionId: String): Boolean = false

    fun viewTransactions(transactionIds: List<String>): Boolean = false

    fun viewTransactionsByCategory(
        transactionsByCategory: Map<String, List<String>>
    ): Boolean = false

    fun showTransfer(
        sourceUri: String,
        destinationUri: String,
        amount: Amount
    ): Boolean = false

    fun showCategory(categoryCode: String): Boolean = false
}