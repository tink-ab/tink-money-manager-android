package se.tink.commons.extensions

import com.tink.model.category.Category
import com.tink.model.category.CategoryTree
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.time.DayPeriod
import com.tink.model.time.MonthPeriod
import com.tink.model.time.Period
import com.tink.model.time.YearPeriod
import org.joda.time.DateTime
import org.threeten.bp.Instant
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

// Put all extensions on classes from the new core here
// TODO: Refactor later into separate files/classes

fun Amount.abs() = Amount(ExactNumber(value.unscaledValue.absoluteValue, value.scale), currencyCode)

fun BigDecimal.toExactNumber() = ExactNumber(unscaledValue().toLong(), scale().toLong())


fun ExactNumber.subtract(other: ExactNumber) =
    toBigDecimal().subtract(other.toBigDecimal()).toExactNumber()

fun ExactNumber.divide(other: ExactNumber) =
    toBigDecimal().divide(other.toBigDecimal()).toExactNumber()

fun ExactNumber.add(other: ExactNumber) = toBigDecimal().add(other.toBigDecimal()).toExactNumber()
fun ExactNumber.doubleValue() = toBigDecimal().toDouble()
fun ExactNumber.floatValue() = toBigDecimal().toFloat()
fun ExactNumber.absValue() = toBigDecimal().abs().toExactNumber()
fun ExactNumber.longValue() = toBigDecimal().longValueExact()

fun ExactNumber.isInteger() = toBigDecimal().stripTrailingZeros().scale() <= 0

fun ExactNumber.isBiggerThan(other: ExactNumber) = toBigDecimal() > other.toBigDecimal()
fun ExactNumber.isSmallerThan(other: ExactNumber) = toBigDecimal() < other.toBigDecimal()


fun ExactNumber.round() = round(0)

fun ExactNumber.round(decimals: Int) =
    toBigDecimal().setScale(decimals, RoundingMode.HALF_UP).toExactNumber()

val ExactNumberZERO = ExactNumber(0,0)


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

fun CategoryTree.getCategoryByType(type: Category.Type): Category {
    TODO("Core setup")
}

val Amount.isValid get() = currencyCode.isNotEmpty()

val Category.type: Category.Type get() {
    TODO("Core setup")
}

val Category.parent: Category get() {
    TODO("Core setup")
}

fun Category.getNameWithDefaultChildFormat(defaultChildFormat: String?): String? {
    return if (isDefaultChild && parent.children.size > 1) {
        String.format(defaultChildFormat!!, name)
    } else {
        name
    }
}

fun Instant.toDateTime(): DateTime = DateTime(this.toEpochMilli())


//TODO: Naming to reflect what this actually does
fun Period.isAfter(other: Period) = start.isAfter(other.end)

fun Period.isInPeriod(dateTime: DateTime): Boolean {

val instant = Instant.ofEpochMilli(dateTime.millis)

    //Inclusive inside checks.
    val afterStart = instant.isAfter(start) || instant == start
    val beforeStop = instant.isBefore(end) || instant == end

    return afterStart && beforeStop
}

fun Period.toMonthString(): String? {

    fun monthString(year: Int, month: Int) = if (month < 10) "$year-$month" else "$year-0$month"

    return when (this) {
        is YearPeriod -> null
        is DayPeriod -> monthString(year, monthOfYear)
        is MonthPeriod -> monthString(year, monthOfYear)
    }
}

object PeriodUtil {
    @JvmStatic
    fun isAfter(one: Period, other: Period) = one.isAfter(other)

    @JvmStatic
    fun isInPeriod(dateTime: DateTime, period: Period) = period.isInPeriod(dateTime)
}
