package se.tink.core.models.property

import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber


data class Property(
        val id: String,
        val address: String,
        val city: String,
        val community: String,
        val postalCode: String,
        val registeredAddress: Boolean,
        val valuation: Amount,
        val loanAccountIds: List<String>,
        val type: Type,
        val averageInterestRate: ExactNumber,
        val totalLoanAmount: Amount
) {
    enum class Type {
        UNKNOWN, HOUSE, APARTMENT, VACATION_HOUSE, UNRECOGNIZED
    }
}