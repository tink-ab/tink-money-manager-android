package com.tink.moneymanagerui.insights.actionhandling

import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.misc.Amount


/**
 * This class contains a set of methods that you can implement to perform the necessary logic when
 * a user selects an action for an insight that requires more user interaction.
 */
open class InsightActionHandler {
    /**
     * Handle action where the user wants to view information for a budget matching the id and start period.
     *
     * @param budgetId Identifier for the budget that should be shown
     * @param periodStart The start period for the budget that should be shown
     */
    open fun viewBudget(budgetId: String, periodStart: String): Boolean = false

    /**
     * Handle action where the user wants to create a budget.
     *
     * @param amount An optional budget amount to be set
     * @param filter An optional [BudgetFilter] to be set
     * @param periodicity An optional [BudgetPeriodicity] to be set
     */
    open fun createBudget(
        amount: Amount?,
        filter: BudgetFilter?,
        periodicity: BudgetPeriodicity?
    ): Boolean = false

    /**
     * Handle action where the user wants to categorize a transaction matching the id.
     *
     * @param transactionId Identifier for the transaction that should be categorized
     */
    open fun categorizeTransaction(transactionId: String): Boolean = false

    /**
     * Handle action where the user wants to view transactions matching the list of ids.
     *
     * @param transactionIds List of identifiers for all the transactions that should be shown
     */
    open fun viewTransactions(transactionIds: List<String>): Boolean = false

    /**
     * Handle action where the user wants to view transactions by categories.
     *
     * @param transactionsByCategory A mapping of category code to list of identifiers for all transactions that belong to that category
     */
    open fun viewTransactionsByCategory(
        transactionsByCategory: Map<String, List<String>>
    ): Boolean = false

    /**
     * Handle action where the user wants to make a transfer.
     *
     * @param sourceUri URI for the source account that the transfer should be from
     * @param destinationUri URI for the destination account that the transfer should be to
     * @param amount The amount to be transferred
     */
    open fun showTransfer(
        sourceUri: String,
        destinationUri: String,
        amount: Amount
    ): Boolean = false
}