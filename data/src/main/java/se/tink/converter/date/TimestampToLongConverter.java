package se.tink.converter.date;

import com.google.protobuf.Timestamp;
import java.util.concurrent.TimeUnit;
import se.tink.converter.ModelConverter;
import se.tink.modelConverter.AbstractConverter;

public class TimestampToLongConverter extends AbstractConverter<Timestamp, Long> {

	private final ModelConverter converter;

	public TimestampToLongConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public Long convert(Timestamp source) {
		return TimeUnit.SECONDS.toMillis(source.getSeconds()) + TimeUnit.NANOSECONDS
			.toMillis(source.getNanos());
	}

	@Override
	public Class<Timestamp> getSourceClass() {
		return Timestamp.class;
	}

	@Override
	public Class<Long> getDestinationClass() {
		return Long.class;
	}
}
