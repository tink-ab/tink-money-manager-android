package se.tink.converter.misc;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;

public class PeriodToPeriodDTOConverter extends
	AbstractConverter<Period, se.tink.grpc.v1.models.Period> {

	private final ModelConverter converter;

	public PeriodToPeriodDTOConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public se.tink.grpc.v1.models.Period convert(Period source) {
		se.tink.grpc.v1.models.Period.Builder period = se.tink.grpc.v1.models.Period.newBuilder();
		period.setYear(source.getYear());
		period.setMonth(source.getMonth());
		period.setWeek(source.getWeek());
		period.setDay(source.getDay());
		period.setStart(converter.map(source.getStart(), Timestamp.class));
		period.setStop(converter.map(source.getStop(), Timestamp.class));
		return period.build();
	}

	@Override
	public Class<Period> getSourceClass() {
		return Period.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Period> getDestinationClass() {
		return se.tink.grpc.v1.models.Period.class;
	}
}
