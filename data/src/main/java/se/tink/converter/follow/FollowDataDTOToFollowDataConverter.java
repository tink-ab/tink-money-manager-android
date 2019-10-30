package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowData;
import se.tink.modelConverter.AbstractConverter;

public class FollowDataDTOToFollowDataConverter extends
	AbstractConverter<FollowData, se.tink.grpc.v1.models.FollowData> {

	private final ModelConverter converter;

	public FollowDataDTOToFollowDataConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.FollowData convert(FollowData source) {
		se.tink.grpc.v1.models.FollowData.Builder destination = se.tink.grpc.v1.models.FollowData
			.newBuilder();
		if (source.getPeriod() != null) {
			destination
				.setPeriod(converter.map(source.getPeriod(), se.tink.grpc.v1.models.Period.class));
		}

		if (source.getHistoricalAmounts() != null) {
			destination.addAllHistoricalAmounts(
				(converter
					.map(source.getHistoricalAmounts(),
						se.tink.grpc.v1.models.PeriodExactNumberPair.class)));
		}
		return destination.build();
	}

	@Override
	public Class<FollowData> getSourceClass() {
		return FollowData.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.FollowData> getDestinationClass() {
		return se.tink.grpc.v1.models.FollowData.class;
	}
}