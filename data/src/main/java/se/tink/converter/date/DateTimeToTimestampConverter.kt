package se.tink.converter.date

import com.google.protobuf.Timestamp
import org.joda.time.DateTime
import se.tink.modelConverter.AbstractConverter

class DateTimeToTimestampConverter : AbstractConverter<DateTime, Timestamp>() {
    override fun convert(source: DateTime): Timestamp = source.toTimestamp()
}

fun DateTime.toTimestamp(): Timestamp = Timestamp.newBuilder()
    .setSeconds(millis / 1000)
    .setNanos((millis % 1000 * 1000).toInt())
    .build()
