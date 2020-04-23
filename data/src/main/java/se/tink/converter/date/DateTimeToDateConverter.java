package se.tink.converter.date;

import java.util.Date;
import org.joda.time.DateTime;
import se.tink.modelConverter.AbstractConverter;

public class DateTimeToDateConverter extends AbstractConverter<DateTime, Date> {

	@Override
	public Date convert(DateTime source) {
		return source.toDate();
	}

	@Override
	public Class<DateTime> getSourceClass() {
		return DateTime.class;
	}

	@Override
	public Class<Date> getDestinationClass() {
		return Date.class;
	}
}
