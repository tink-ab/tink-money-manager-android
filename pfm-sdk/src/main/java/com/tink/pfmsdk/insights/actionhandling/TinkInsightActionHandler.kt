package com.tink.pfmsdk.insights.actionhandling

import se.tink.core.models.misc.Amount

interface TinkInsightActionHandler {
    fun viewBudget(budgetId: String, periodStart: String): Boolean
    fun categorizeTransaction(transactionId: String): Boolean
    fun viewTransactions(transactionIds: List<String>): Boolean
    fun viewTransactionsByCategory(transactionsByCategory: Map<String, List<String>>): Boolean
    fun showTransfer(sourceUri: String, destinationUri: String, amount: Amount): Boolean
    fun showCategory(categoryCode: String): Boolean
}