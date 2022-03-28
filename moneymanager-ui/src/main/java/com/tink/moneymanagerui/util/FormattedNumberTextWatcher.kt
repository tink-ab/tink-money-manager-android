package com.tink.moneymanagerui.util

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.VisibleForTesting
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import se.tink.commons.extensions.doubleValue
import timber.log.Timber
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.util.Currency
import java.util.Locale
import kotlin.math.min

private const val NO_BREAK_SPACE = 160.toChar()

internal class FormattedNumberTextWatcher(
    locale: Locale,
    private val currencyCode: String
) : TextWatcher {

    private val numberFormat: DecimalFormat = getDecimalFormatForLocale(locale)
        .apply {
            currency = Currency.getInstance(currencyCode)
            maximumFractionDigits = 0
        }

    private val decimalSeparator = numberFormat.decimalFormatSymbols.decimalSeparator
    private val groupingSeparator = numberFormat.decimalFormatSymbols.groupingSeparator
    private val invalidCharsForIntPart = Regex("[^${this.groupingSeparator}\\d]")

    private val invalidCharsForDecimalPart = Regex("[^\\d]")
    private var originalString: String = ""

    private val maximumFractionDigits
        get() = numberFormat.maximumFractionDigits

    private fun getDecimalFormatForLocale(locale: Locale): DecimalFormat {
        return DecimalFormat.getInstance(locale) as DecimalFormat
    }

    fun allowedCharacters(): String = "0123456789$decimalSeparator$groupingSeparator$NO_BREAK_SPACE"

    private fun rawString(s: String): String {
        return s
            .replace("[^${this.decimalSeparator}\\d]".toRegex(), "")
            .replace("$decimalSeparator", ".")
    }

    fun getAmountFromText(text: String): Amount? {
        val amountText = rawString(text)
            .also { if (it.isEmpty()) return null }

        return try {
            val bigDecimal = BigDecimal(amountText)
            val exactNumber = ExactNumber(bigDecimal)
            Amount(exactNumber, numberFormat.currency?.currencyCode ?: currencyCode)
        } catch (e: NumberFormatException) {
            null
        }
    }

    /**
     * Inverse of [getAmountFromText].
     * This will return the text formatted by the numberFormat, without currency symbols.
     */
    fun getTextFromAmount(amount: Amount): String = numberFormat.format(amount.value.doubleValue())

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (originalString == s.toString()) {
            return
        }
        if (s?.isEmpty() == true) {
            originalString = ""
            return
        }
        s?.replace(0, s.length, getFormattedNumber(s.toString()))
    }

    @VisibleForTesting
    fun getFormattedNumber(rawString: String): String {
        return try {
            parseAndFormat(rawString)
        } catch (e: ParseException) {
            Timber.e(e)
            originalString // This will be the previously entered value, meaning it won't update the text.
        }
    }

    @Throws(ParseException::class)
    private fun parseAndFormat(
        rawString: String
    ): String {
        val allowDecimals = maximumFractionDigits > 0
        if (!isInputValid(rawString, allowDecimals)) {
            return originalString
        }
        val parts = rawString.split(decimalSeparator, limit = 2)
        val result = when {
            parts.size == 1 || !allowDecimals -> // No decimals
                numberFormat.parse(parts[0].cleanGrouping()).let { numberFormat.format(it) }

            parts.size == 2 && allowDecimals -> { // We have decimals, or at least a separator.
                val intPart = numberFormat.parse(parts[0].cleanGrouping())
                    .let { numberFormat.format(it) }
                val decimalPart = parts[1]
                    .replace(invalidCharsForDecimalPart, "")
                    .let { it.substring(0, min(it.length, maximumFractionDigits)) }
                "$intPart$decimalSeparator$decimalPart"
            }

            else -> throw ParseException("Invalid number", 0)
        }
        // We have a valid result. Save the originalString before returning the result
        originalString = rawString
        return result
    }

    private fun isInputValid(input: String, allowDecimals: Boolean): Boolean {
        if (!allowDecimals && input.contains(decimalSeparator)) {
            return false
        }
        val parts = input.split(decimalSeparator, limit = 2)
        if (parts.first().isEmpty() || parts.first().contains(invalidCharsForIntPart)) {
            return false
        }
        return true
    }

    private fun String.cleanGrouping(): String {
        return replace("$groupingSeparator", "")
    }
}
