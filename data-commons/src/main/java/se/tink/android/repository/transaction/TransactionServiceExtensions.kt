package se.tink.android.repository.transaction

import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.service.transaction.TransactionService


suspend fun TransactionService.fetchAllTransactions(
    period: Period,
    categoryId: String
): List<Transaction> {

    var offset = 0
    val transactions = mutableListOf<Transaction>()

    while (true) {

        val newTransactions = listTransactions(
            period = period, categoryId = categoryId, offset = offset
        )

        if (newTransactions.isNotEmpty()) {
            transactions += newTransactions
            offset += newTransactions.size
        } else {
            return transactions
        }
    }
}
