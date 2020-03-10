package com.tink.pfmui.insights.actionhandling

import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import javax.inject.Singleton

@Singleton
internal object CustomInsightActionHandler : ActionHandler {

    private var insightActionHandler: InsightActionHandler? = null

    fun setInsightActionHandler(insightActionHandler: InsightActionHandler) {
        this.insightActionHandler = insightActionHandler
    }

    override fun handle(action: InsightAction, insight: Insight): Boolean {
        return when (action.data) {
            is InsightAction.Data.CategorizeExpense -> {
                insightActionHandler
                    ?.categorizeTransaction((action.data as InsightAction.Data.CategorizeExpense).transactionId)
                    ?: false
            }

            is InsightAction.Data.ViewTransactions -> {
                insightActionHandler
                    ?.viewTransactions((action.data as InsightAction.Data.ViewTransactions).transactionIds)
                    ?: false
            }

            is InsightAction.Data.ViewTransactionsByCategory -> {
                insightActionHandler
                    ?.viewTransactionsByCategory((action.data as InsightAction.Data.ViewTransactionsByCategory).transactionsByCategory)
                    ?: false
            }

            is InsightAction.Data.ViewBudget -> {
                (action.data as InsightAction.Data.ViewBudget).let {
                    insightActionHandler
                        ?.viewBudget(it.budgetId, it.periodStartDate.toString())
                        ?: false
                }
            }

            is InsightAction.Data.CreateTransfer -> {
                (action.data as InsightAction.Data.CreateTransfer).let {
                    insightActionHandler
                        ?.showTransfer(it.sourceUri, it.destinationUri, it.amount)
                        ?: false
                }
            }

            else -> false // Other types will be handled internally by Tink
        }
    }
}