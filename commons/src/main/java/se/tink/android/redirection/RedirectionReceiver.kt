package se.tink.android.redirection

import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.misc.Amount

interface RedirectionReceiver {
    fun showTransactionDetails(transactionId: String)
    fun categorizeTransaction(transactionId: String)
    fun showTransactionListForIds(transactionIds: List<String>)
    fun showAccountDetails(accountId: String)
    fun showBudget(id: String, periodStart: String)
    fun createBudget(
        name: String? = null,
        amount: Amount? = null,
        filter: BudgetFilter? = null,
        periodicity: BudgetPeriodicity? = null
    )
}
