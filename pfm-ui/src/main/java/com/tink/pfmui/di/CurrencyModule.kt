package com.tink.pfmui.di

import com.tink.pfmui.util.extensions.formatCurrencyExplicitlyPositive
import com.tink.pfmui.util.extensions.formatCurrencyRound
import com.tink.pfmui.util.extensions.formatCurrencyRoundWithoutSign
import com.tink.pfmui.util.extensions.formatCurrencyRoundWithoutSignAndSymbol
import com.tink.pfmui.util.extensions.formatCurrencyRoundWithoutSymbol
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
                    amount.formatCurrencyRoundWithoutSignAndSymbol() ?: ""
                } else if (!useSign) {
                    amount.formatCurrencyRoundWithoutSign() ?: ""
                } else if (!useSymbol) {
                    amount.formatCurrencyRoundWithoutSymbol() ?: ""
                } else if (explicitlyPositive) {
                    amount.formatCurrencyExplicitlyPositive() ?: ""
                } else {
                    amount.formatCurrencyRound() ?: ""
                }
            }
        }
}
