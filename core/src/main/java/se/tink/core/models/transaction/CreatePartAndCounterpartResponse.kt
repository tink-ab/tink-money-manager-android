package se.tink.core.models.transaction


data class CreatePartAndCounterpartResponse(
        private val transaction: Transaction,
        private val counterpartTransaction: Transaction
)
