package com.tink.pfmui.mapper

import com.tink.pfmui.charts.models.PeriodBalance
import se.tink.commons.extensions.doubleValue
import com.tink.model.time.Period
import com.tink.model.statistic.Statistic
import se.tink.modelConverter.AbstractConverter

internal class StatisticToPeriodBalanceConverter(
    private val modelConverter: ModelConverterImplementation
) : AbstractConverter<Statistic, PeriodBalance>() {

    override fun convert(source: Statistic): PeriodBalance = source.toPeriodBalance()

    private fun Statistic.toPeriodBalance() =
        PeriodBalance(
            period.let { modelConverter.map(it, Period::class.java) },
            value.value.doubleValue()
        )

    override fun getSourceClass(): Class<Statistic> {
        return Statistic::class.java
    }

    override fun getDestinationClass(): Class<PeriodBalance> {
        return PeriodBalance::class.java
    }
}