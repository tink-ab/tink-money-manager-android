package se.tink.converter.budgets

import se.tink.core.models.budgets.Budget.Periodicity.Recurring.PeriodUnit
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.modelConverter.AbstractConverter

class RecurringPeriodicityToRecurringPeriodicityDTOConverter :
    AbstractConverter<RecurringPeriodicity, RecurringPeriodicityDTO>() {

    override fun convert(source: RecurringPeriodicity): RecurringPeriodicityDTO {
        return RecurringPeriodicityDTO.newBuilder().apply {
            periodUnitValue = source.quantity
            periodUnit = convertPeriodUnit(source.unit)
        }.build()
    }

    private fun convertPeriodUnit(periodUnit: PeriodUnit): PeriodUnitDTO {
        return when (periodUnit) {
            PeriodUnit.UNKNOWN -> PeriodUnitDTO.PERIOD_UNIT_UNKNOWN
            PeriodUnit.WEEK -> PeriodUnitDTO.PERIOD_UNIT_WEEK
            PeriodUnit.MONTH -> PeriodUnitDTO.PERIOD_UNIT_MONTH
            PeriodUnit.YEAR -> PeriodUnitDTO.PERIOD_UNIT_YEAR
        }
    }

    override fun getSourceClass(): Class<RecurringPeriodicity> = RecurringPeriodicity::class.java
    override fun getDestinationClass(): Class<RecurringPeriodicityDTO> =
        RecurringPeriodicityDTO::class.java
}
