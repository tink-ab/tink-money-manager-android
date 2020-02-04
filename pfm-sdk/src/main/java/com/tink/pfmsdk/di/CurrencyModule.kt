package com.tink.pfmsdk.di

import com.tink.pfmsdk.util.extensions.formatCurrencyExplicitlyPositive
import com.tink.pfmsdk.util.extensions.formatCurrencyRound
import com.tink.pfmsdk.util.extensions.formatCurrencyRoundWithoutSign
import com.tink.pfmsdk.util.extensions.formatCurrencyRoundWithoutSignAndSymbol
import com.tink.pfmsdk.util.extensions.formatCurrencyRoundWithoutSymbol
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
