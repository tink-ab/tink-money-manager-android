package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowData;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;

public class FollowDataToFollowDataDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.FollowData, FollowData> {

	private final ModelConverter converter;

	public FollowDataToFollowDataDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public FollowData convert(se.tink.grpc.v1.models.FollowData source) {
		FollowData destination = new FollowData();
		if (source.getPeriod() != null) {
			destination.setPeriod(converter.map(source.getPeriod(), Period.class));
		}

		if (source.getHistoricalAmountsList() != null) {
			destination.setHistoricalAmounts(
				converter
					.map(source.getHistoricalAmountsList(),
						PeriodExactNumberPair.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.FollowData> getSourceClass() {
		return se.tink.grpc.v1.models.FollowData.class;
	}

	@Override
	public Class<FollowData> getDestinationClass() {
		return FollowData.class;
	}

}
