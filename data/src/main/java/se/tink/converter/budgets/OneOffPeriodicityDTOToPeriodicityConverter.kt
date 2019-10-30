package se.tink.converter.budgets

import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.OneOffPeriodicity
import se.tink.modelConverter.AbstractConverter

class OneOffPeriodicityDTOToPeriodicityConverter(
    val modelConverter: ModelConverter
) : AbstractConverter<OneOffPeriodicityDTO, OneOffPeriodicity>() {

    override fun convert(source: OneOffPeriodicityDTO): OneOffPeriodicity {
        return source.run {
            OneOffPeriodicity(
                start = modelConverter.map(start, DateTime::class.java),
                end = modelConverter.map(end, DateTime::class.java)
            )
        }
    }

    override fun getSourceClass(): Class<OneOffPeriodicityDTO> = OneOffPeriodicityDTO::class.java
    override fun getDestinationClass(): Class<OneOffPeriodicity> =
        OneOffPeriodicity::class.java
}