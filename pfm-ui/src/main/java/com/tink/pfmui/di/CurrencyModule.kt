package com.tink.pfmui.di

import com.tink.pfmui.collections.Currencies
import com.tink.pfmui.util.CurrencyUtils
import com.tink.pfmui.util.extensions.formatCurrencyExact
import com.tink.pfmui.util.extensions.formatCurrencyExactExplicitlyPositive
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSign
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSignAndSymbol
import com.tink.pfmui.util.extensions.formatCurrencyExactWithoutSymbol
import dagger.Module
import dagger.Provides
import se.tink.commons.currency.AmountFormatter
import se.tink.core.models.misc.Amount
import javax.inject.Singleton

@Module
internal class CurrencyModule {

    @Provides
    @Singleton
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
                return if (useSymbol && useRounding(currencyCode)) {
                    CurrencyUtils.formatAmountRoundWithCurrencySymbol(amount)
                } else if (!useSymbol) {
                    CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(amount)
                } else if (!useRounding(currencyCode)) {
                    CurrencyUtils.formatAmountExactWithCurrencySymbol(amount)
                } else {
                    CurrencyUtils.formatAmountExactWithoutCurrencySymbol(amount)
                }
            }

            private fun useRounding(currencyCode: String): Boolean =
                currencyCode == "SEK" || currencyCode == "NOK"
        }
}
