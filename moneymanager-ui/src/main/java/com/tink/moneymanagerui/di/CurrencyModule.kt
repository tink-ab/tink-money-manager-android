package com.tink.moneymanagerui.di

import com.tink.moneymanagerui.util.CurrencyUtils
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactExplicitlyPositive
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactWithoutSign
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactWithoutSignAndSymbol
import com.tink.moneymanagerui.util.extensions.formatCurrencyExactWithoutSymbol
import com.tink.annotations.PfmScope
import com.tink.model.misc.Amount
import dagger.Module
import dagger.Provides
import se.tink.commons.currency.AmountFormatter

@Module
internal class CurrencyModule {

    @Provides
    @PfmScope
    fun provideAmountFormatter(): AmountFormatter =
        object : AmountFormatter {
            override fun format(
                amount: Amount,
                useSymbol: Boolean,
                useSign: Boolean,
                explicitlyPositive: Boolean
            ): String {
                return if (!useSymbol && !useSign) {
                    amount.formatCurrencyExactWithoutSignAndSymbol()
                } else if (!useSign) {
                    amount.formatCurrencyExactWithoutSign()
                } else if (!useSymbol) {
                    amount.formatCurrencyExactWithoutSymbol()
                } else if (explicitlyPositive) {
                    amount.formatCurrencyExactExplicitlyPositive()
                } else {
                    amount.formatCurrencyExact()
                }
            }

            override fun format(
                amount: Double,
                currency: String,
                useSymbol: Boolean
            ): String {
                return format(
                    amount,
                    currency,
                    useSymbol,
                    useRounding(currency)
                )
            }

            override fun format(
                amount: Double,
                currency: String,
                useSymbol: Boolean,
                useRounding: Boolean
            ): String {
                return when {
                    !useSymbol && !useRounding ->
                        CurrencyUtils.formatAmountExactWithoutCurrencySymbol(amount, currency)

                    useSymbol && !useRounding ->
                        CurrencyUtils.formatAmountExactWithCurrencySymbol(amount, currency)

                    !useSymbol && useRounding ->
                        CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(amount, currency)

                    else -> CurrencyUtils.formatAmountRoundWithCurrencySymbol(amount, currency)
                }
            }

            private fun useRounding(currencyCode: String): Boolean =
                currencyCode == "SEK" || currencyCode == "NOK"
        }
}
