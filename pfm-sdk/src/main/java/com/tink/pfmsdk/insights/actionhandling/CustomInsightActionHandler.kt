package com.tink.pfmsdk.insights.actionhandling

import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Singleton

@Singleton
internal object CustomInsightActionHandler : ActionHandler {

    private var tinkInsightActionHandler: TinkInsightActionHandler? = null

    fun setTinkInsightActionHandler(tinkInsightActionHandler: TinkInsightActionHandler) {
        this.tinkInsightActionHandler = tinkInsightActionHandler
    }

    override fun handle(action: InsightAction, insight: Insight): Boolean {
        return when (action.data) {
            is InsightAction.Data.CategorizeExpense -> {
                tinkInsightActionHandler
                    ?.categorizeTransaction((action.data as InsightAction.Data.CategorizeExpense).transactionId)
                    ?: false
            }

            is InsightAction.Data.ViewTransactions -> {
                tinkInsightActionHandler
                    ?.viewTransactions((action.data as InsightAction.Data.ViewTransactions).transactionIds)
                    ?: false
            }

            is InsightAction.Data.ViewTransactionsByCategory -> {
                tinkInsightActionHandler
                    ?.viewTransactionsByCategory((action.data as InsightAction.Data.ViewTransactionsByCategory).transactionsByCategory)
                    ?: false
            }

            is InsightAction.Data.ViewBudget -> {
                (action.data as InsightAction.Data.ViewBudget).let {
                    tinkInsightActionHandler
                        ?.viewBudget(it.budgetId, it.periodStartDate.toString())
                        ?: false
                }
            }

            is InsightAction.Data.CreateTransfer -> {
                (action.data as InsightAction.Data.CreateTransfer).let {
                    tinkInsightActionHandler
                        ?.showTransfer(it.sourceUri, it.destinationUri, it.amount)
                        ?: false
                }
            }

            else -> false // Other types will be handled internally by Tink
        }
    }
}