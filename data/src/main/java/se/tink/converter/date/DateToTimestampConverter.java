package se.tink.converter.date;

import com.google.protobuf.Timestamp;
import java.util.Date;
import se.tink.modelConverter.AbstractConverter;

public class DateToTimestampConverter extends AbstractConverter<Date, Timestamp> {

	@Override
	public Timestamp convert(Date source) {
		Timestamp.Builder builder = Timestamp.newBuilder();
		builder.setSeconds(source.getTime() / 1000);
		builder.setNanos((int) ((source.getTime() % 1000) * 1000));

		return builder.build();
	}

	@Override
	public Class<Date> getSourceClass() {
		return Date.class;
	}

	@Override
	public Class<Timestamp> getDestinationClass() {
		return Timestamp.class;
	}
}
