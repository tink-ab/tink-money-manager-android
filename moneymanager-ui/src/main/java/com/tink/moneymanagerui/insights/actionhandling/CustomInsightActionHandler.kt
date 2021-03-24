package com.tink.moneymanagerui.insights.actionhandling

import com.tink.annotations.PfmScope
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction

@PfmScope
internal object CustomInsightActionHandler {

    private var insightActionHandler: InsightActionHandler? = null

    fun setInsightActionHandler(insightActionHandler: InsightActionHandler) {
        this.insightActionHandler = insightActionHandler
    }

    fun canHandleAction(action: InsightAction): Boolean =
        insightActionHandler != null && action.canBeHandled()

    private fun InsightAction.canBeHandled(): Boolean =
        when (data) {
            is InsightAction.Data.ViewBudget,
            is InsightAction.Data.CreateBudget,
            is InsightAction.Data.CreateTransfer,
            is InsightAction.Data.CategorizeExpense,
            is InsightAction.Data.CategorizeTransactions,
            is InsightAction.Data.ViewTransactions,
            is InsightAction.Data.ViewTransactionsByCategory -> true
            else -> false
        }

    fun handle(
        action: InsightAction,
        insight: Insight,
        onComplete: (isActionDone: Boolean) -> Unit
    ) {
        when (action.data) {
            is InsightAction.Data.CategorizeExpense -> {
                insightActionHandler
                    ?.categorizeExpense(
                        (action.data as InsightAction.Data.CategorizeExpense).transactionId
                    ) { isActionDone ->
                        onComplete.invoke(isActionDone)
                    }
            }

            is InsightAction.Data.CategorizeTransactions -> {
                insightActionHandler
                    ?.categorizeTransactions(
                        (action.data as InsightAction.Data.CategorizeTransactions).transactionIds
                    ) { isActionDone ->
                        onComplete.invoke(isActionDone)
                    }
            }

            is InsightAction.Data.ViewTransactions -> {
                insightActionHandler
                    ?.viewTransactions((action.data as InsightAction.Data.ViewTransactions).transactionIds)
                onComplete.invoke(true)
            }

            is InsightAction.Data.ViewTransactionsByCategory -> {
                insightActionHandler
                    ?.viewTransactionsByCategory((action.data as InsightAction.Data.ViewTransactionsByCategory).transactionsByCategory)
                onComplete.invoke(true)
            }

            is InsightAction.Data.ViewBudget -> {
                (action.data as InsightAction.Data.ViewBudget).let {
                    insightActionHandler?.viewBudget(it.budgetId, it.periodStartDate.toString())
                    onComplete.invoke(true)
                }
            }

            is InsightAction.Data.CreateBudget -> {
                (action.data as InsightAction.Data.CreateBudget).let {
                    insightActionHandler
                        ?.createBudget(it.amount, it.budgetFilter, it.periodicity) { isActionDone ->
                            onComplete.invoke(isActionDone)
                        }
                }
            }

            is InsightAction.Data.CreateTransfer -> {
                (action.data as InsightAction.Data.CreateTransfer).let {
                    insightActionHandler
                        ?.initiateTransfer(it.sourceUri, it.destinationUri, it.amount) {isActionDone ->
                            onComplete.invoke(isActionDone)
                        }
                }
            }

            else -> onComplete.invoke(false) // Just a fallback but these other action types are handled internally
        }
    }
}