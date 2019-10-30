package se.tink.core.models.transaction

import se.tink.core.models.misc.Amount


data class Counterpart(
        val id: String,
        val partId: String,
        val counterpartTransactionId: String,
        val amount: Amount,
        val transactionAmount: Amount,
        val description: String
)
