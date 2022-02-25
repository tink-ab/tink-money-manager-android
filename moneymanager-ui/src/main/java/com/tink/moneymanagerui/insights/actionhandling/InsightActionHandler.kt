package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.insights.InsightAction
import com.tink.model.misc.Amount


/**
 * This class contains a set of methods that you can implement to perform the necessary logic when
 * a user selects an action for an insight that requires more user interaction.
 *
 * All the methods have a [Boolean] return type to indicate if the action is handled by this handler or not.
 * If you are overriding a method to perform custom logic for the action, the method should return `true`.
 *
 * For methods with a `onComplete` lambda block parameter, you have to invoke the lambda block
 * when the task related to the requested action has completed or been cancelled.
 * If the action has completed successfully, the block can be invoked with the boolean value set to `true`. Eg: onComplete.invoke(true)
 * If the action has failed or is cancelled, the block can be invoked with the boolean value set to `false`. Eg: onComplete.invoke(false)
 * If you don’t invoke the `onComplete` block, the insight will remain in the list and will not be archived.
 */
open class InsightActionHandler {
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
     * @param onComplete The lambda block to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun createBudget(
        amount: Amount?,
        filter: BudgetFilter?,
        periodicity: BudgetPeriodicity?,
        onComplete: (isActionDone: Boolean) -> Unit
    ): Boolean = false

    /**
     * Handle action where the user wants to categorize a transaction matching the id.
     * This corresponds to [InsightAction.Type.CATEGORIZE_EXPENSE] action type.
     *
     * @param transactionId Identifier for the transaction that should be categorized
     * @param onComplete The lambda block to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun categorizeExpense(
        transactionId: String,
        onComplete: (isActionDone: Boolean) -> Unit
    ): Boolean = false

    /**
     * Handle action where the user wants to categorize multiple transactions matching a list of ids.
     * This corresponds to [InsightAction.Type.CATEGORIZE_TRANSACTIONS] action type.
     *
     * @param transactionIds List of identifiers for all the transactions that should be categorized
     * @param onComplete The lambda block to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun categorizeTransactions(
        transactionIds: List<String>,
        onComplete: (isActionDone: Boolean) -> Unit
    ): Boolean = false

    /**
     * Handle action where the user requests to refresh an aggregated credential.
     * This corresponds to [InsightAction.Type.REFRESH_CREDENTIAL] action type.
     *
     * @param credentialId The id of the aggregated credential
     * @param onComplete The lambda block to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun refreshCredentials(
        credentialId: String,
        onComplete: (isActionDone: Boolean) -> Unit
    ) : Boolean = false

    /**
     * Handle action where the user wants to view account matching the id.
     * This corresponds to [InsightAction.Type.VIEW_ACCOUNT] and [InsightAction.Type.VIEW_TRANSACTIONS] action types.
     *
     * @param accountId Identifiers for the account that should be shown
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun viewAccount(accountId: String): Boolean = false

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
     * @param onComplete The lambda block to invoke to indicate if the action has completed successfully or not
     * @return `true` if the action will be handled, `false` otherwise
     */
    open fun initiateTransfer(
        sourceUri: String?,
        sourceAccountNumber: String?,
        destinationUri: String?,
        destinationAccountNumber: String?,
        amount: Amount?,
        onComplete: (isActionDone: Boolean) -> Unit
    ): Boolean = false
}
