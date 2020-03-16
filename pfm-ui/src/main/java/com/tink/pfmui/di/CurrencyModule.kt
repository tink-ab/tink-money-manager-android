package com.tink.pfmui.di

import com.tink.pfmui.collections.Currencies
import com.tink.pfmui.util.CurrencyUtils
import com.tink.pfmui.util.extensions.formatCurrencyExact
import com.tink.pfmui.util.extensions.formatCurrencyExactExplicitlyPositive
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSign
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSignAndSymbol
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSymbol
import com.tink.annotations.PfmScope
import dagger.Module
import dagger.Provides
import se.tink.commons.currency.AmountFormatter
import se.tink.core.models.misc.Amount

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
                    amount.formatCurrencyExactWithoutSignAndSymbol() ?: ""
                } else if (!useSign) {
                    amount.formatCurrencyExactWithoutSign() ?: ""
                } else if (!useSymbol) {
                    amount.formatCurrencyExactWithoutSymbol() ?: ""
                } else if (explicitlyPositive) {
                    amount.formatCurrencyExactExplicitlyPositive() ?: ""
                } else {
                    amount.formatCurrencyExact() ?: ""
                }
            }

            override fun format(amount: Double, useSymbol: Boolean): String {
                val currencyCode = Currencies.getSharedInstance().defaultCurrencyCode
                return when {
                    !useSymbol && !useRounding(currencyCode) ->
                        CurrencyUtils.formatAmountExactWithoutCurrencySymbol(amount)

                    useSymbol && !useRounding(currencyCode) ->
                        CurrencyUtils.formatAmountExactWithCurrencySymbol(amount)

                    !useSymbol && useRounding(currencyCode) ->
                        CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(amount)

                    else -> CurrencyUtils.formatAmountRoundWithCurrencySymbol(amount)
                }
            }

            private fun useRounding(currencyCode: String): Boolean =
                currencyCode == "SEK" || currencyCode == "NOK"
        }
}
