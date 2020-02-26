package se.tink.android.redirection

import se.tink.core.models.misc.Amount

interface RedirectionReceiver {
    fun showTransactionDetails(transactionId: String)
    fun categorizeTransaction(transactionId: String)
    fun showTransactionListForIds(transactionIds: List<String>)
    fun showAccountDetails(accountId: String)
    fun showTransfer(transferId: String)
    fun showTransfer(
        sourceAccountId: String,
        destinationAccountId: String,
        amount: Amount
    )
    fun showCategory(categoryCode: String)
    fun showBudget(id: String, periodStart: String)
}