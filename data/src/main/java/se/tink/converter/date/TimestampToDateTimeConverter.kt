package se.tink.converter.date

import com.google.protobuf.Timestamp
import org.joda.time.DateTime
import se.tink.modelConverter.AbstractConverter
import java.util.concurrent.TimeUnit

class TimestampToDateTimeConverter : AbstractConverter<Timestamp, DateTime>() {
    override fun convert(source: Timestamp) = source.toDateTime()
}

fun Timestamp.toDateTime() =
    DateTime(
        TimeUnit.SECONDS.toMillis(seconds)
                + TimeUnit.NANOSECONDS.toMillis(nanos.toLong())
    )
