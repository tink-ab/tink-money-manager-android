package se.tink.converter.budgets

import se.tink.core.models.budgets.Budget.Periodicity.Recurring.PeriodUnit
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.modelConverter.AbstractConverter

class RecurringPeriodicityDTOToPeriodicityConverter :
    AbstractConverter<RecurringPeriodicityDTO, RecurringPeriodicity>() {

    override fun convert(source: RecurringPeriodicityDTO): RecurringPeriodicity {
        return source.run {
            RecurringPeriodicity(
                unit = convertPeriodUnit(periodUnit),
                quantity = periodUnitValue
            )
        }
    }

    private fun convertPeriodUnit(unit: PeriodUnitDTO?): PeriodUnit =
        when (unit) {
            null,
            PeriodUnitDTO.UNRECOGNIZED,
            PeriodUnitDTO.PERIOD_UNIT_UNKNOWN -> PeriodUnit.UNKNOWN
            PeriodUnitDTO.PERIOD_UNIT_WEEK -> PeriodUnit.WEEK
            PeriodUnitDTO.PERIOD_UNIT_MONTH -> PeriodUnit.MONTH
            PeriodUnitDTO.PERIOD_UNIT_YEAR -> PeriodUnit.YEAR
        }

    override fun getSourceClass(): Class<RecurringPeriodicityDTO> =
        RecurringPeriodicityDTO::class.java

    override fun getDestinationClass(): Class<RecurringPeriodicity> =
        RecurringPeriodicity::class.java
}
