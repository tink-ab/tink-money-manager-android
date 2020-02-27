package com.tink.pfmsdk.insights.actionhandling

import se.tink.core.models.misc.Amount

open class TinkInsightActionHandler : InsightActionHandler {
    override fun viewBudget(budgetId: String, periodStart: String): Boolean = false

    override fun categorizeTransaction(transactionId: String): Boolean = false

    override fun viewTransactions(transactionIds: List<String>): Boolean = false

    override fun viewTransactionsByCategory(
        transactionsByCategory: Map<String, List<String>>
    ): Boolean = false

    override fun showTransfer(
        sourceUri: String,
        destinationUri: String,
        amount: Amount
    ): Boolean = false

    override fun showCategory(categoryCode: String): Boolean = false
}

internal interface InsightActionHandler {
    fun viewBudget(budgetId: String, periodStart: String): Boolean
    fun categorizeTransaction(transactionId: String): Boolean
    fun viewTransactions(transactionIds: List<String>): Boolean
    fun viewTransactionsByCategory(transactionsByCategory: Map<String, List<String>>): Boolean
    fun showTransfer(sourceUri: String, destinationUri: String, amount: Amount): Boolean
    fun showCategory(categoryCode: String): Boolean
}