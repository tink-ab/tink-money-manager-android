package com.tink.pfmui.mapper

import com.tink.pfmui.charts.models.PeriodBalance
import se.tink.commons.extensions.doubleValue
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.Statistic
import se.tink.modelConverter.AbstractConverter

internal class StatisticToPeriodBalanceConverter(
    private val modelConverter: ModelConverterImplementation
) : AbstractConverter<Statistic, PeriodBalance>() {

    override fun convert(source: Statistic): PeriodBalance = source.toPeriodBalance()

    private fun Statistic.toPeriodBalance() =
        PeriodBalance(
            period?.let { modelConverter.map(it, Period::class.java) },
            amount?.value?.doubleValue() ?: 0.0
        )

    override fun getSourceClass(): Class<Statistic> {
        return Statistic::class.java
    }

    override fun getDestinationClass(): Class<PeriodBalance> {
        return PeriodBalance::class.java
    }
}