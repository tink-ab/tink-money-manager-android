package se.tink.converter.tracking;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.tracking.TrackingEvent;
import se.tink.modelConverter.AbstractConverter;

public class TrackingEventToTrackingDTOConverter extends
	AbstractConverter<TrackingEvent, se.tink.grpc.v1.models.TrackingEvent> {

	private ModelConverter modelConverter;

	public TrackingEventToTrackingDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.TrackingEvent convert(TrackingEvent source) {
		se.tink.grpc.v1.models.TrackingEvent.Builder destination = se.tink.grpc.v1.models.TrackingEvent
			.newBuilder();
		destination.setDate(modelConverter.map(source.getDate(), Timestamp.class));
		if (source.getLabel() != null) {
			destination.setLabel(source.getLabel());
		}
		if (source.getCategory() != null) {
			destination.setCategory(source.getCategory());
		}
		if (source.getAction() != null) {
			destination.setAction(source.getAction());
		}
		if (source.getValue() != null) {
			destination.setDefaultValue(source.getValue());
		}
		return destination.build();
	}

	@Override
	public Class<TrackingEvent> getSourceClass() {
		return TrackingEvent.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.TrackingEvent> getDestinationClass() {
		return se.tink.grpc.v1.models.TrackingEvent.class;
	}
}
