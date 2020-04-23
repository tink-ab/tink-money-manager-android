package se.tink.converter.tracking;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.tracking.TrackingTiming;
import se.tink.modelConverter.AbstractConverter;

public class TrackingTimingToTrackingTimingDTOConverter extends
	AbstractConverter<TrackingTiming, se.tink.grpc.v1.models.TrackingTiming> {

	private ModelConverter modelConverter;

	public TrackingTimingToTrackingTimingDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.TrackingTiming convert(TrackingTiming source) {
		se.tink.grpc.v1.models.TrackingTiming.Builder destination = se.tink.grpc.v1.models.TrackingTiming
			.newBuilder();

		destination.setDate(modelConverter.map(source.getDate(), Timestamp.class));
		if (source.getCategory() != null) {
			destination.setCategory(source.getCategory());
		}
		if (source.getLabel() != null) {
			destination.setLabel(source.getLabel());
		}
		if (source.getName() != null) {
			destination.setName(source.getName());
		}
		destination.setTime(modelConverter.map(source.getTime(), Timestamp.class));

		return destination.build();
	}

	@Override
	public Class<TrackingTiming> getSourceClass() {
		return TrackingTiming.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.TrackingTiming> getDestinationClass() {
		return se.tink.grpc.v1.models.TrackingTiming.class;
	}
}
