package se.tink.commons.extensions

import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import java.math.BigDecimal
import kotlin.math.absoluteValue

fun Amount.abs() = Amount(ExactNumber(value.unscaledValue.absoluteValue, value.scale), currencyCode)

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
    return Amount(value.divide(BigDecimal(double).toExactNumber()), currencyCode)
}

fun Iterable<Amount>.average(): Amount =
    map {
        it to 1.0
    }.reduce { sum, pair ->
        sum.first + pair.first to sum.second + pair.second
    }.let { pair ->
        pair.first / pair.second
    }


fun Iterable<Amount>.sumOrNull(): Amount? =
    reduceOrNull { sum, amount -> sum + amount }

val Amount.isValid get() = currencyCode.isNotEmpty()

private fun requireSameCurrencyCode(first: Amount, second: Amount) = require(
    first.currencyCode == second.currencyCode
) { "Wrong currency code. Expected: ${first.currencyCode}, was: ${second.currencyCode}" }
