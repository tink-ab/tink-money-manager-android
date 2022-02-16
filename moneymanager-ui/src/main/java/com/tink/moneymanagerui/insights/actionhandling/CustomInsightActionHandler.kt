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
            is InsightAction.Data.ViewAccount,
            is InsightAction.Data.ViewTransactionsByCategory,
            is InsightAction.Data.RefreshCredential -> true
            else -> false
        }

    fun handle(
        action: InsightAction,
        insight: Insight,
        onComplete: (isActionDone: Boolean) -> Unit
    ): Boolean {
        return when (action.data) {
            is InsightAction.Data.CategorizeExpense -> {
                insightActionHandler
                    ?.categorizeExpense(
                        (action.data as InsightAction.Data.CategorizeExpense).transactionId
                    ) { isActionDone ->
                        onComplete.invoke(isActionDone)
                    }
                    ?: false
            }

            is InsightAction.Data.CategorizeTransactions -> {
                insightActionHandler
                    ?.categorizeTransactions(
                        (action.data as InsightAction.Data.CategorizeTransactions).transactionIds
                    ) { isActionDone ->
                        onComplete.invoke(isActionDone)
                    }
                    ?: false
            }

            is InsightAction.Data.RefreshCredential -> {
                insightActionHandler?.refreshCredentials(
                        (action.data as InsightAction.Data.RefreshCredential).credentialId
                    ) { isActionDone ->
                        onComplete.invoke(isActionDone)
                    } ?: false
            }

            is InsightAction.Data.ViewAccount -> {
                val isActionHandled = insightActionHandler
                    ?.viewAccount((action.data as InsightAction.Data.ViewAccount).accountId)
                    ?: false
                if (isActionHandled) {
                    onComplete.invoke(true)
                    true
                } else {
                    false
                }
            }

            is InsightAction.Data.ViewTransactions -> {
                val isActionHandled = insightActionHandler
                    ?.viewTransactions((action.data as InsightAction.Data.ViewTransactions).transactionIds)
                    ?: false
                // TODO: Move this into a separate function to avoid duplicity
                if (isActionHandled) {
                    onComplete.invoke(true)
                    true
                } else {
                    false
                }
            }

            is InsightAction.Data.ViewTransactionsByCategory -> {
                val isActionHandled = insightActionHandler
                    ?.viewTransactionsByCategory((action.data as InsightAction.Data.ViewTransactionsByCategory).transactionsByCategory)
                    ?: false
                if (isActionHandled) {
                    onComplete.invoke(true)
                    true
                } else {
                    false
                }
            }

            is InsightAction.Data.ViewBudget -> {
                (action.data as InsightAction.Data.ViewBudget).let {
                    val isActionHandled = insightActionHandler
                        ?.viewBudget(it.budgetId, it.periodStartDate.toString())
                        ?: false
                    if (isActionHandled) {
                        onComplete.invoke(true)
                        true
                    } else {
                        false
                    }
                }
            }

            is InsightAction.Data.CreateBudget -> {
                (action.data as InsightAction.Data.CreateBudget).let {
                    insightActionHandler
                        ?.createBudget(it.amount, it.budgetFilter, it.periodicity) { isActionDone ->
                            onComplete.invoke(isActionDone)
                        }
                        ?: false
                }
            }

            is InsightAction.Data.CreateTransfer -> {
                (action.data as InsightAction.Data.CreateTransfer).let {
                    insightActionHandler
                        ?.initiateTransfer(
                            it.sourceUri,
                            it.sourceAccountNumber,
                            it.destinationUri,
                            it.destinationAccountNumber,
                            it.amount
                        ) { isActionDone ->
                            onComplete.invoke(isActionDone)
                        }
                        ?: false
                }
            }

            else -> false // Return false so these action types are handled internally
        }
    }
}