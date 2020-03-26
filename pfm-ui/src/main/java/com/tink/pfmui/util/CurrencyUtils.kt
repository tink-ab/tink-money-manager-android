package com.tink.pfmui.util

import androidx.annotation.VisibleForTesting
import com.google.common.collect.ImmutableList
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.pfmui.collections.Currencies
import se.tink.commons.extensions.absValue
import se.tink.commons.extensions.divide
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.isBiggerThan
import se.tink.commons.extensions.isInteger
import se.tink.commons.extensions.isSmallerThan
import se.tink.commons.extensions.longValue
import se.tink.commons.extensions.round
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.HashMap
import java.util.Locale
import kotlin.collections.set

object CurrencyUtils {
    // When using dynamic currency format:
    // if amount >= DYNAMIC_ROUNDING_THRESHOLD then rounded format is applied
    // else exact format is applied.
    private val DYNAMIC_ROUNDING_THRESHOLDS = DynamicRoundingThresholds
    private const val NON_BREAKING_WHITESPACE = 160.toChar()
    private val ZERO = ExactNumber(0, 0)
    private val THOUSAND = ExactNumber(1000, 0)
    private val MILLION =
        ExactNumber(1000000, 0)
    private val BILLION =
        ExactNumber(1000000000, 0)
    private val UNITS: MutableMap<String, ImmutableList<StringExactNumberPair>> =
        HashMap()

    fun getAmountLabel(amount: Amount): String {
        return formatCurrency(amount)
    }

    fun formatCurrency(amount: Amount): String {
        return formatCurrencyWithAmountSign(amount)
    }

    fun formatCurrencyRound(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.ROUND or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatCurrencyRoundWithoutSignAndSymbol(amount: Amount): String {
        val amountWithoutSign: Double = amount.value.doubleValue()
        return formatAmountRoundWithoutCurrencySymbol(amountWithoutSign)
    }

    fun formatCurrencyRoundWithoutSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.ROUND or CurrencyFormat.SYMBOL)
    }

    fun formatCurrencyRoundWithoutSymbol(amount: Amount): String {
        return formatAmountRoundWithoutCurrencySymbol(amount.value.doubleValue())
    }

    fun formatCurrencyExact(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.EXACT or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatCurrencyRoundWithoutAmountSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.ROUND or CurrencyFormat.SYMBOL)
    }

    fun formatCurrencyWithAmountSign(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.DYNAMIC or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatCurrencyWithAmountSignExact(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.DYNAMIC or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
                    or CurrencyFormat.EXACT
        )
    }

    fun formatCurrencyWithoutAmountSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.DYNAMIC or CurrencyFormat.SYMBOL)
    }

    fun formatCurrencyWithExplicitPositive(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.DYNAMIC or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN,
            true
        )
    }

    @VisibleForTesting
    fun formatCurrency(
        amount: Amount,
        currencyFormat: Int,
        explicitPositive: Boolean
    ): String {
        val absValue: ExactNumber = amount.value.absValue()
        var currencyCode = amount.currencyCode
        if (currencyCode == null) {
            currencyCode = Currencies.getSharedInstance().defaultCurrencyCode
        }
        var formatted: String
        formatted = if (currencyFormat and CurrencyFormat.ROUND == CurrencyFormat.ROUND) {
            formatAmountRound(absValue, currencyCode)
        } else if (currencyFormat and CurrencyFormat.EXACT == CurrencyFormat.EXACT) {
            formatAmountExact(absValue, currencyCode)
        } else if (currencyFormat and CurrencyFormat.SHORT == CurrencyFormat.SHORT) {
            formatShort(absValue, currencyCode)
        } else { // CurrencyFormat.DYNAMIC
            if (absValue.toBigDecimal().toInt() < DYNAMIC_ROUNDING_THRESHOLDS.get(currencyCode) &&
                absValue.isBiggerThan(ZERO)
            ) {
                formatAmount(absValue, 2, currencyCode)
            } else {
                formatAmountRound(absValue, currencyCode)
            }
        }
        if (currencyFormat and CurrencyFormat.AMOUNT_SIGN == CurrencyFormat.AMOUNT_SIGN) {
            if (amount.value.isSmallerThan(ZERO)) {
                formatted = "-$formatted"
            } else if (explicitPositive) {
                formatted = "+$formatted"
            }
        }
        return formatted
    }

    fun formatCurrency(amount: Amount, currencyFormat: Int): String {
        return formatCurrency(amount, currencyFormat, false)
    }

    fun formatAmountRound(
        amount: ExactNumber,
        currencyCode: String?
    ): String {
        return formatAmount(amount, 0, currencyCode)
    }

    fun formatAmountRoundWithoutCurrencySymbol(amount: Double): String {
        return formatAmountRound(amount, false)
    }

    @JvmStatic
    fun formatAmountRoundWithCurrencySymbol(amount: Double): String {
        return formatAmountRound(amount, true)
    }

    private fun formatAmountRound(
        amount: Double,
        useCurrencySymbol: Boolean
    ): String {
        val format = getDecimalFormat(null, 0)
        var formatted = format.format(Math.round(amount))
        if (!useCurrencySymbol) {
            val symbol =
                (format as DecimalFormat).decimalFormatSymbols.currencySymbol
            if (formatted.contains(symbol)) {
                //Replace the currency symbol and the surrounding space, if it's before or after the symbol, or no space.
                formatted = formatted.replace(symbol + "\\s".toRegex(), "")
                formatted = formatted.replace("\\s" + symbol.toRegex(), "")
                formatted = formatted.replace(symbol.toRegex(), "")
            }
        }
        return formatted
    }

    val minusSign: Char
        get() {
            val format = getDecimalFormat(null, 0)
            return (format as DecimalFormat).decimalFormatSymbols.minusSign
        }

    private fun formatAmount(
        amount: ExactNumber,
        decimals: Int,
        currencyCode: String?
    ): String {
        return getDecimalFormat(currencyCode, decimals)
            .format(amount.round(decimals).toBigDecimal())
    }

    private fun getDecimalFormat(
        currencyCode: String?,
        decimals: Int
    ): NumberFormat {
        var currencyCode = currencyCode
        var locale = Locale.getDefault()
        val userProfile =
            Currencies.getSharedInstance().userProfile
        if (userProfile != null) {
            val localeCode = userProfile.locale
            val splits = localeCode.split("_").toTypedArray()
            locale = Locale(splits[0], userProfile.market)
        }
        if (currencyCode == null) {
            currencyCode = Currencies.getSharedInstance().defaultCurrencyCode
        }
        val format = DecimalFormat.getCurrencyInstance(locale)
        if (!currencyCode.isEmpty()) {
            format.currency = Currency.getInstance(currencyCode)
        }
        format.minimumFractionDigits = decimals
        format.maximumFractionDigits = decimals
        return format
    }

    fun formatCurrencyExactWithExplicitPositive(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.EXACT or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN,
            true
        )
    }

    fun formatCurrencyExactWithoutSymbol(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.EXACT or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatAmountExactWithoutCurrencySymbol(amount: Double): String {
        return formatAmount(amount, 2, false);
    }

    fun formatCurrencyExactWithoutSignAndSymbol(amount: Amount): String {
        return formatAmount(amount.value.absValue().doubleValue(), 2, false)
    }

    fun formatAmountExactWithCurrencySymbol(amount: Double): String {
        return formatAmount(amount, 2, true);
    }

    fun formatCurrencyExactWithoutSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.EXACT or CurrencyFormat.SYMBOL)
    }

    fun formatAmountExact(amount: ExactNumber): String {
        return formatAmount(amount, amount.scale.toInt(), null)
    }

    private fun formatAmountExact(
        amount: ExactNumber,
        currencyCode: String
    ): String {
        return formatAmount(amount, amount.scale.toInt(), currencyCode)
    }

    private fun formatAmount(amount: Double, decimals: Int, useCurrencySymbol: Boolean): String {
        val format = getDecimalFormat(null, decimals)
        var formatted = format.format(amount)
        if (!useCurrencySymbol) {
            val symbol = (format as DecimalFormat).decimalFormatSymbols.currencySymbol
            if (formatted.contains(symbol)) {
                //Replace the currency symbol and the surrounding space, if it's before or after the symbol, or no space.
                formatted = formatted.replace(symbol + "\\s".toRegex(), "")
                formatted = formatted.replace("\\s" + symbol.toRegex(), "")
                formatted = formatted.replace(symbol.toRegex(), "")
            }
        }
        return formatted
    }


    fun formatShort(
        value: ExactNumber,
        currencyCode: String?
    ): String {
        var unit: StringExactNumberPair? = null
        var locale = Locale.getDefault()
        val userProfile =
            Currencies.getSharedInstance()
                .userProfile
        if (userProfile != null) {
            locale = Locale(userProfile.locale)
        }
        val language = locale.language
        val localeUnits = UNITS[language]!!
        for (unitCandidate in localeUnits) {
            unit = if (value.compareTo(unitCandidate.e) >= 0) {
                unitCandidate
            } else {
                break
            }
        }
        if (unit == null) {
            return java.lang.String.format(locale, "%d", value.longValue())
        }
        val valueInUnit: ExactNumber = value.divide(unit.e)
        var amount =
            formatAmountRoundWithoutCurrencySymbol(valueInUnit.doubleValue())
        amount = amount.replace("\\s".toRegex(), "")
        val decimalFormat: NumberFormat
        // Check if value in unit is an integer.
        decimalFormat = if (valueInUnit.isInteger()) {
            getDecimalFormat(currencyCode, 0)
        } else {
            getDecimalFormat(currencyCode, 1)
        }
        val symbol =
            (decimalFormat as DecimalFormat).decimalFormatSymbols
                .currencySymbol
        return String.format("%s%s %s", amount, unit.s, symbol)
    }

    fun formatGetAmountSign(amount: Amount): String {
        if (amount.value.isSmallerThan(ZERO)) {
            return "-"
        } else if (amount.value.isBiggerThan(ZERO)) {
            return "+"
        }
        return ""
    }

    fun isAmountLessThanZero(amount: Amount): Boolean {
        return if (amount.value.isSmallerThan(ZERO)) {
            true
        } else false
    }

    private class StringExactNumberPair(
        val s: String,
        val e: ExactNumber
    )

    object CurrencyFormat {
        // Value dependent format (exact if very small, rounded otherwise).
        const val NONE = 0x0
        const val SYMBOL = 0x1 // Include currency symbol.
        const val SHORT = 0x2
        const val EXACT = 0x4
        const val ROUND = 0x8
        const val DYNAMIC = 0x10
        const val VERY_SHORT = 0x16
        const val AMOUNT_SIGN = 0x20 // Include amount sign
    }

    init {
        val enUsMap =
            ImmutableList
                .of(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("k", THOUSAND),
                    StringExactNumberPair("mm", MILLION),
                    StringExactNumberPair("bn", BILLION)
                )
        val svSeMap =
            ImmutableList
                .of(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("t", THOUSAND),
                    StringExactNumberPair("mn", MILLION),
                    StringExactNumberPair("md", BILLION)
                )
        val frFRMap =
            ImmutableList
                .of(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("m", THOUSAND),
                    StringExactNumberPair("mn", MILLION),
                    StringExactNumberPair("md", BILLION)
                )
        val defaultMap =
            ImmutableList
                .of(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("k", THOUSAND),
                    StringExactNumberPair("M", MILLION),
                    StringExactNumberPair("G", BILLION)
                )
        val nlNlMap =
            ImmutableList
                .of(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("dzd", THOUSAND),
                    StringExactNumberPair("mln", MILLION),
                    StringExactNumberPair("mjd", BILLION)
                )
        UNITS["en"] = enUsMap
        UNITS["sv"] = svSeMap
        UNITS["fr"] = frFRMap
        UNITS[""] = defaultMap
        UNITS["nl"] = nlNlMap
    }
}
