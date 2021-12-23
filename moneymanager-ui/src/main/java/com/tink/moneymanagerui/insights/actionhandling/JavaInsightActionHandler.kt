package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.insights.InsightAction
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.insights.actionhandling.callbacks.OnInsightHandled

/**
 * This class is only meant to be used if you can't use [InsightActionHandler]. It has the same methods
 * as [InsightActionHandler] but uses explicit callbacks instead of lambdas, intended to be used if you're
 * not using Kotlin. If will internally create an [InsightActionHandler]
 */
open class JavaInsightActionHandler {
    /**
     * Handle action where the user wants to view information for a budget matching the id and start period.
     * This corresponds to [InsightAction.Type.VIEW_BUDGET] action type.
     *
     * @param budgetId Identifier for the budget that should be shown
     * @param periodStart The start period for the budget that should be shown
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun viewBudget(budgetId: String, periodStart: String): Boolean = false

    /**
     * Handle action where the user wants to create a budget.
     * This corresponds to [InsightAction.Type.CREATE_BUDGET] action type.
     *
     * @param amount An optional budget amount to be set
     * @param filter An optional [BudgetFilter] to be set
     * @param periodicity An optional [BudgetPeriodicity] to be set
     * @param onHandled The [OnInsightHandled] to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun createBudget(
        amount: Amount?,
        filter: BudgetFilter?,
        periodicity: BudgetPeriodicity?,
        onHandled: OnInsightHandled
    ): Boolean = false

    /**
     * Handle action where the user wants to categorize a transaction matching the id.
     * This corresponds to [InsightAction.Type.CATEGORIZE_EXPENSE] action type.
     *
     * @param transactionId Identifier for the transaction that should be categorized
     * @param onHandled The [OnInsightHandled] to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun categorizeExpense(
        transactionId: String,
        onHandled: OnInsightHandled
    ): Boolean = false

    /**
     * Handle action where the user wants to categorize multiple transactions matching a list of ids.
     * This corresponds to [InsightAction.Type.CATEGORIZE_TRANSACTIONS] action type.
     *
     * @param transactionIds List of identifiers for all the transactions that should be categorized
     * @param onHandled The [OnInsightHandled] to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun categorizeTransactions(
        transactionIds: List<String>,
        onHandled: OnInsightHandled
    ): Boolean = false

    /**
     * Handle action where the user requests to refresh an aggregated credential.
     * This corresponds to [InsightAction.Type.REFRESH_CREDENTIAL] action type.
     *
     * @param credentialId The id of the aggregated credential
     * @param onHandled The [OnInsightHandled] to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun refreshCredentials(
        credentialId: String,
        onHandled: OnInsightHandled
    ) : Boolean = false

    /**
     * Handle action where the user wants to view transactions matching the list of ids.
     * This corresponds to [InsightAction.Type.VIEW_TRANSACTION] and [InsightAction.Type.VIEW_TRANSACTIONS] action types.
     *
     * @param transactionIds List of identifiers for all the transactions that should be shown
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun viewTransactions(transactionIds: List<String>): Boolean = false

    /**
     * Handle action where the user wants to view transactions by categories.
     * This corresponds to [InsightAction.Type.VIEW_TRANSACTIONS_BY_CATEGORY] action type.
     *
     * @param transactionsByCategory A mapping of category code to list of identifiers for all transactions that belong to that category
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun viewTransactionsByCategory(
        transactionsByCategory: Map<String, List<String>>
    ): Boolean = false

    /**
     * Handle action where the user wants to make a transfer.
     * This corresponds to [InsightAction.Type.CREATE_TRANSFER] action type.
     *
     * @param sourceUri URI for the source account that the transfer should be from
     * @param sourceAccountNumber The account number to initiate a transfer from
     * @param destinationUri URI for the destination account that the transfer should be to
     * @param destinationAccountNumber The account number to initiate a transfer to
     * @param amount The amount to be transferred
     * @param onHandled The [OnInsightHandled] callback to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun initiateTransfer(
        sourceUri: String?,
        sourceAccountNumber: String?,
        destinationUri: String?,
        destinationAccountNumber: String?,
        amount: Amount?,
        onHandled: OnInsightHandled
    ): Boolean = false

    internal fun toInsightActionHandler(): InsightActionHandler {
        return object : InsightActionHandler() {
            override fun viewBudget(budgetId: String, periodStart: String): Boolean {
                return this@JavaInsightActionHandler.viewBudget(budgetId, periodStart)
            }

            override fun createBudget(
                amount: Amount?,
                filter: BudgetFilter?,
                periodicity: BudgetPeriodicity?,
                onComplete: (isActionDone: Boolean) -> Unit
            ): Boolean {
                return this@JavaInsightActionHandler.createBudget(amount, filter, periodicity, onComplete)
            }

            override fun categorizeExpense(
                transactionId: String,
                onComplete: (isActionDone: Boolean) -> Unit
            ): Boolean {
                return this@JavaInsightActionHandler.categorizeExpense(transactionId, onComplete)
            }

            override fun categorizeTransactions(
                transactionIds: List<String>,
                onComplete: (isActionDone: Boolean) -> Unit
            ): Boolean {
                return this@JavaInsightActionHandler.categorizeTransactions(transactionIds, onComplete)
            }

            override fun refreshCredentials(
                credentialId: String,
                onComplete: (isActionDone: Boolean) -> Unit
            ): Boolean {
                return this@JavaInsightActionHandler.refreshCredentials(credentialId, onComplete)
            }

            override fun viewTransactions(transactionIds: List<String>): Boolean {
                return this@JavaInsightActionHandler.viewTransactions(transactionIds)
            }

            override fun viewTransactionsByCategory(transactionsByCategory: Map<String, List<String>>): Boolean {
                return this@JavaInsightActionHandler.viewTransactionsByCategory(transactionsByCategory)
            }

            override fun initiateTransfer(
                sourceUri: String?,
                sourceAccountNumber: String?,
                destinationUri: String?,
                destinationAccountNumber: String?,
                amount: Amount?,
                onComplete: (isActionDone: Boolean) -> Unit
            ): Boolean {
                return this@JavaInsightActionHandler.initiateTransfer(
                    sourceUri,
                    sourceAccountNumber,
                    destinationUri,
                    destinationAccountNumber,
                    amount,
                    onComplete
                )
            }
        }
    }
}
