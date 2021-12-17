package se.tink.commons.extensions

import com.tink.model.misc.ExactNumber
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun BigDecimal.toExactNumber() = ExactNumber(unscaledValue().toLong(), scale().toLong())


fun ExactNumber.subtract(other: ExactNumber) =
    toBigDecimal().subtract(other.toBigDecimal()).toExactNumber()

fun ExactNumber.divide(other: ExactNumber): ExactNumber {
    val result = this.toBigDecimal().divide(other.toBigDecimal(), MathContext.DECIMAL32)
    return ExactNumber(result)
}

fun ExactNumber.multiply(other: ExactNumber) =
    toBigDecimal().multiply(other.toBigDecimal()).toExactNumber()

fun ExactNumber.add(other: ExactNumber) = toBigDecimal().add(other.toBigDecimal()).toExactNumber()
fun ExactNumber.doubleValue() = toBigDecimal().toDouble()
fun ExactNumber.floatValue() = toBigDecimal().toFloat()
fun ExactNumber.absValue() = toBigDecimal().abs().toExactNumber()
fun ExactNumber.longValue() = toBigDecimal().longValueExact()

fun ExactNumber.isInteger() = toBigDecimal().stripTrailingZeros().scale() <= 0

fun ExactNumber.isBiggerThan(other: ExactNumber) = toBigDecimal() > other.toBigDecimal()
fun ExactNumber.isSmallerThan(other: ExactNumber) = toBigDecimal() < other.toBigDecimal()

fun ExactNumber.round(decimals: Int) =
    toBigDecimal().setScale(decimals, RoundingMode.HALF_UP).toExactNumber()
