package se.tink.android.redirection

interface RedirectionReceiver {
    fun showTransactionDetails(transactionId: String)
    fun categorizeTransaction(transactionId: String)
    fun showTransactionListForIds(transactionIds: List<String>)
    fun showAccountDetails(accountId: String)
    fun showCategory(categoryCode: String)
    fun showBudget(id: String, periodStart: String)
}