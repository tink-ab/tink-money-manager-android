package se.tink.core.extensions

import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import java.math.BigDecimal

fun Amount.adjustSignWith(other: Amount): Amount {
    val sign = other.value.asBigDecimal().signum()
    val signedValue = value.absValue().multiply(ExactNumber(sign.toBigDecimal()))
    return Amount(signedValue, currencyCode)
}

operator fun Amount.compareTo(other: Amount): Int {
    requireSameCurrencyCode(this, other)
    return value.compareTo(other.value)
}

operator fun Amount.minus(other: Amount): Amount {
    requireSameCurrencyCode(this, other)
    return Amount(value.subtract(other.value), currencyCode)
}

operator fun Amount.plus(other: Amount): Amount {
    requireSameCurrencyCode(this, other)
    return Amount(value.add(other.value), currencyCode)
}

operator fun Amount.div(double: Double): Amount {
    return Amount(value.divide(ExactNumber(BigDecimal(double))), currencyCode)
}

fun Iterable<Amount>.average(): Amount =
    map {
        it to 1.0
    }.reduce { sum, pair ->
        sum.first + pair.first to sum.second + pair.second
    }.let { pair ->
        pair.first / pair.second
    }


fun Iterable<Amount>.sum(): Amount = reduce { sum, amount -> sum + amount}

private fun requireSameCurrencyCode(first: Amount, second: Amount) = require(
    first.currencyCode == second.currencyCode
) { "Wrong currency code. Expected: ${first.currencyCode}, was: ${second.currencyCode}" }
