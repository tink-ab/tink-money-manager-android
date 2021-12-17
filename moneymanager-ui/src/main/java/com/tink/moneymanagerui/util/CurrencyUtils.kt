package com.tink.moneymanagerui.util

import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.moneymanagerui.collections.Currencies
import se.tink.commons.extensions.absValue
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.isBiggerThan
import se.tink.commons.extensions.isInteger
import se.tink.commons.extensions.isSmallerThan
import se.tink.commons.extensions.round
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.HashMap
import java.util.Locale
import kotlin.collections.set
import kotlin.math.roundToLong

object CurrencyUtils {
    // When using dynamic currency format:
    // if amount >= DYNAMIC_ROUNDING_THRESHOLD then rounded format is applied
    // else exact format is applied.
    private val DYNAMIC_ROUNDING_THRESHOLDS = DynamicRoundingThresholds
    private val ZERO = ExactNumber(0, 0)
    private val THOUSAND = ExactNumber(1000, 0)
    private val MILLION =
        ExactNumber(1000000, 0)
    private val BILLION =
        ExactNumber(1000000000, 0)
    private val UNITS: MutableMap<String, List<StringExactNumberPair>> =
        HashMap()

    private const val EXACT_DIGITS = 2

    private const val DEFAULT_CURRENCY_CODE = "EUR"

    fun formatCurrencyRound(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.ROUND or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatCurrencyRoundWithoutSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.ROUND or CurrencyFormat.SYMBOL)
    }

    fun formatCurrencyExactWithoutSign(amount: Amount): String {
        return formatCurrency(amount, CurrencyFormat.EXACT or CurrencyFormat.SYMBOL)
    }

    fun formatCurrencyExact(amount: Amount): String {
        return formatCurrency(
            amount,
            CurrencyFormat.EXACT or CurrencyFormat.SYMBOL or CurrencyFormat.AMOUNT_SIGN
        )
    }

    fun formatCurrencyExactIfNotInteger(amount: Amount, additionalFlags: Int = 0): String {
        return formatCurrency(
            amount,
            CurrencyFormat.EXACT_OR_INTEGER or CurrencyFormat.SYMBOL or additionalFlags
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

    fun formatCurrency(
        amount: Amount,
        currencyFormat: Int,
        explicitPositive: Boolean
    ): String {
        val absValue: ExactNumber = amount.value.absValue()
        var currencyCode = amount.currencyCode
        if (currencyCode.isBlank()) {
            currencyCode = DEFAULT_CURRENCY_CODE
        }
        var formatted: String
        formatted = if (currencyFormat and CurrencyFormat.ROUND == CurrencyFormat.ROUND) {
            formatAmountRound(absValue, currencyCode)
        } else if (currencyFormat and CurrencyFormat.EXACT == CurrencyFormat.EXACT) {
            formatAmountExact(absValue, currencyCode)
//        } else if (currencyFormat and CurrencyFormat.SHORT == CurrencyFormat.SHORT) {
//            formatShort(absValue, currencyCode)
        } else if(currencyFormat and CurrencyFormat.EXACT_OR_INTEGER == CurrencyFormat.EXACT_OR_INTEGER) {
            formatAmountExactIfNotInteger(absValue, currencyCode)
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

    fun formatAmountRoundWithoutCurrencySymbol(amount: Double, currency: String?): String {
        return formatAmountRound(amount, currency, false)
    }

    @JvmStatic
    fun formatAmountRoundWithCurrencySymbol(amount: Double, currency: String): String {
        return formatAmountRound(amount, currency, true)
    }

    private fun formatAmountRound(
        amount: Double,
        currency: String?,
        useCurrencySymbol: Boolean
    ): String {
        val format = getDecimalFormat(currency, 0)
        var formatted = format.format(amount.roundToLong())
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

    private fun formatAmount(
        amount: ExactNumber,
        decimals: Int,
        currencyCode: String?
    ): String {
        return getDecimalFormat(currencyCode, decimals)
            .format(amount.round(decimals).toBigDecimal())
    }

    private fun formatIntegerAmount(
        amount: ExactNumber,
        currencyCode: String
    ): String {
        val currencySymbol = try {
            Currency.getInstance(currencyCode).symbol
        } catch (exception: Exception) {
            Currency.getInstance(Currencies.getSharedInstance().defaultCurrencyCode).symbol
        }
        return "$currencySymbol${amount.toBigDecimal().toLong()}"
    }

    // TODO: Remove deprecated Currencies usage
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

    fun formatAmountExactWithoutCurrencySymbol(amount: Double, currency: String?): String {
        return formatAmount(amount, 2, currency, false);
    }

    fun formatCurrencyExactWithoutSignAndSymbol(amount: Amount, currency: String?): String {
        return formatAmount(amount.value.absValue().doubleValue(), 2, currency, false)
    }

    fun formatAmountExactWithCurrencySymbol(amount: Double, currency: String?): String {
        return formatAmount(amount, 2, currency, true);
    }

    private fun formatAmountExact(
        amount: ExactNumber,
        currencyCode: String
    ): String {
        return formatAmount(amount, EXACT_DIGITS, currencyCode)
    }

    private fun formatAmountExactIfNotInteger(
        amount: ExactNumber,
        currencyCode: String
    ): String {
        return if (amount.isInteger()) {
            formatIntegerAmount(amount, currencyCode)
        } else {
            formatAmountExact(amount, currencyCode)
        }
    }

    private fun formatAmount(amount: Double, decimals: Int, currency: String?, useCurrencySymbol: Boolean): String {
        val format = getDecimalFormat(currency, decimals)
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
        const val EXACT_OR_INTEGER = 0x40 // Keep as integer if no decimals, otherwise exact
    }

    init {
        val enUsMap =
            listOf(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("k", THOUSAND),
                    StringExactNumberPair("mm", MILLION),
                    StringExactNumberPair("bn", BILLION)
                )
        val svSeMap =
            listOf(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("t", THOUSAND),
                    StringExactNumberPair("mn", MILLION),
                    StringExactNumberPair("md", BILLION)
                )
        val frFRMap =
            listOf(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("m", THOUSAND),
                    StringExactNumberPair("mn", MILLION),
                    StringExactNumberPair("md", BILLION)
                )
        val defaultMap =
            listOf(
                    StringExactNumberPair("", ZERO),
                    StringExactNumberPair("k", THOUSAND),
                    StringExactNumberPair("M", MILLION),
                    StringExactNumberPair("G", BILLION)
                )
        val nlNlMap =
            listOf(
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
