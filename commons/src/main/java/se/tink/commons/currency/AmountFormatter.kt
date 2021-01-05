package se.tink.commons.currency

import com.tink.model.misc.Amount

interface AmountFormatter {
    fun format(
        amount: Amount,
        useSymbol: Boolean = true,
        useSign: Boolean = true,
        explicitlyPositive: Boolean = false
    ): String

    fun format(
        amount: Double,
        currency: String,
        useSymbol: Boolean = true
    ): String

    fun format(
        amount: Double,
        currency: String,
        useSymbol: Boolean = true,
        useRounding: Boolean = false
    ): String
}
