package se.tink.converter.misc;

import org.joda.time.DateTime;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.PeriodEntity;

public class PeriodEntityToPeriodConverter extends AbstractConverter<PeriodEntity, Period> {

	@Override
	public Period convert(PeriodEntity source) {
		Period destination = new Period();
		destination.setDay(source.getDay());
		destination.setWeek(source.getWeek());
		destination.setMonth(source.getMonth());
		destination.setYear(source.getYear());
		destination.setStart(new DateTime(source.getStart()));
		destination.setStop(new DateTime(source.getStop()));
		return destination;
	}

	@Override
	public Class<PeriodEntity> getSourceClass() {
		return PeriodEntity.class;
	}

	@Override
	public Class<Period> getDestinationClass() {
		return Period.class;
	}
}
