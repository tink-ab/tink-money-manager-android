package se.tink.converter.misc;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;

public class PeriodDTOToPeriodConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Period, Period> {

	private final ModelConverter converter;

	public PeriodDTOToPeriodConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public Period convert(se.tink.grpc.v1.models.Period source) {
		Period period = new Period();
		period.setYear(source.getYear());
		period.setMonth(source.getMonth());
		period.setWeek(converter.map(source.getWeek(), Integer.class));
		period.setDay(converter.map(source.getDay(), Integer.class));
		period.setStart(converter.map(source.getStart(), DateTime.class));
		period.setStop(converter.map(source.getStop(), DateTime.class));
		return period;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Period> getSourceClass() {
		return se.tink.grpc.v1.models.Period.class;
	}

	@Override
	public Class<Period> getDestinationClass() {
		return Period.class;
	}
}
