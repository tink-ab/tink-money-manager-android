package se.tink.converter.budgets

import com.google.protobuf.Timestamp
import org.joda.time.DateTimeZone
import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.OneOffPeriodicity
import se.tink.modelConverter.AbstractConverter

class OneOffPeriodicityToOneOffPeriodicityDTOCoverter(
    val modelConverter: ModelConverter
) : AbstractConverter<OneOffPeriodicity, OneOffPeriodicityDTO>() {

    override fun convert(source: OneOffPeriodicity): OneOffPeriodicityDTO {
        return OneOffPeriodicityDTO.newBuilder().apply {
            start = modelConverter.map(
                source.start.withZoneRetainFields(DateTimeZone.UTC), // Backend wants the date in UTC
                Timestamp::class.java
            )
            end = modelConverter.map(
                source.end.withZoneRetainFields(DateTimeZone.UTC), // Backend wants the date in UTC
                Timestamp::class.java
            )
        }.build()
    }

    override fun getSourceClass(): Class<OneOffPeriodicity> = OneOffPeriodicity::class.java
    override fun getDestinationClass(): Class<OneOffPeriodicityDTO> =
        OneOffPeriodicityDTO::class.java
}
