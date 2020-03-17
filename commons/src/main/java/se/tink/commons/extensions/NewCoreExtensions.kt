package se.tink.commons.extensions

import com.tink.model.category.Category
import com.tink.model.category.CategoryTree
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import java.math.BigDecimal
import kotlin.math.absoluteValue

// Put all extensions on classes from the new core here
// TODO: Refactor later into separate files/classes

fun Amount.abs() = Amount(ExactNumber(value.unscaledValue.absoluteValue, value.scale), currencyCode)

// Preliminary function to ease transition between new and old core
fun se.tink.core.models.misc.Amount.toNewAmount() =
    Amount(ExactNumber(value.unscaledValue, value.scale), currencyCode)

fun BigDecimal.toExactNumber() = ExactNumber(unscaledValue().toLong(), scale().toLong())


fun ExactNumber.subtract(other: ExactNumber) =
    toBigDecimal().subtract(other.toBigDecimal()).toExactNumber()

fun ExactNumber.divide(other: ExactNumber) =
    toBigDecimal().divide(other.toBigDecimal()).toExactNumber()

fun ExactNumber.add(other: ExactNumber) = toBigDecimal().add(other.toBigDecimal()).toExactNumber()
fun ExactNumber.doubleValue() = toBigDecimal().toDouble()


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


fun Iterable<Amount>.sum(): Amount = reduce { sum, amount -> sum + amount }

private fun requireSameCurrencyCode(first: Amount, second: Amount) = require(
    first.currencyCode == second.currencyCode
) { "Wrong currency code. Expected: ${first.currencyCode}, was: ${second.currencyCode}" }

fun CategoryTree.findCategoryByCode(code: String): Category {
    TODO("Core setup")
}
