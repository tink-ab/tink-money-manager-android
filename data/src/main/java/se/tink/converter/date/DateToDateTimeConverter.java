package se.tink.converter.date;

import java.util.Date;
import org.joda.time.DateTime;
import se.tink.modelConverter.AbstractConverter;

public class DateToDateTimeConverter extends AbstractConverter<Date, DateTime> {

	@Override
	public DateTime convert(Date source) {
		return new DateTime(source.getTime());
	}

	@Override
	public Class<Date> getSourceClass() {
		return Date.class;
	}

	@Override
	public Class<DateTime> getDestinationClass() {
		return DateTime.class;
	}
}
