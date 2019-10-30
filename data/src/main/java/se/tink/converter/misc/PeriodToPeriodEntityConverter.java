package se.tink.converter.misc;

import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.PeriodEntity;

public class PeriodToPeriodEntityConverter extends AbstractConverter<Period, PeriodEntity> {

	@Override
	public PeriodEntity convert(Period source) {
		PeriodEntity destination = new PeriodEntity();
		destination.setDay(source.getDay());
		destination.setWeek(source.getWeek());
		destination.setMonth(source.getMonth());
		destination.setYear(source.getYear());
		destination.setStart(source.getStart().toInstant().getMillis());
		destination.setStop(source.getStop().toInstant().getMillis());
		return destination;
	}

	@Override
	public Class<Period> getSourceClass() {
		return Period.class;
	}

	@Override
	public Class<PeriodEntity> getDestinationClass() {
		return PeriodEntity.class;
	}
}
