package se.tink.converter.tracking;

import se.tink.converter.ModelConverter;
import se.tink.core.models.tracking.TrackingRequest;
import se.tink.grpc.v1.models.TrackingTiming;
import se.tink.modelConverter.AbstractConverter;

public class TrackingRequestToTrackingRequestDTOConverter extends
	AbstractConverter<TrackingRequest, se.tink.grpc.v1.rpc.TrackingRequest> {

	private ModelConverter modelConverter;

	public TrackingRequestToTrackingRequestDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.TrackingRequest convert(TrackingRequest source) {
		se.tink.grpc.v1.rpc.TrackingRequest.Builder destination = se.tink.grpc.v1.rpc.TrackingRequest
			.newBuilder();
		if (source.getEvents() != null) {
			destination.addAllEvents(modelConverter
				.map(source.getEvents(),
					se.tink.grpc.v1.models.TrackingEvent.class));
		}
		if (source.getTimings() != null) {
			destination.addAllTimings(modelConverter
				.map(source.getTimings(),
					TrackingTiming.class));
		}
		if (source.getViews() != null) {
			destination.addAllViews(modelConverter
				.map(source.getViews(), se.tink.grpc.v1.models.TrackingView.class));
		}

		return destination.build();
	}

	@Override
	public Class<TrackingRequest> getSourceClass() {
		return TrackingRequest.class;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.TrackingRequest> getDestinationClass() {
		return se.tink.grpc.v1.rpc.TrackingRequest.class;
	}
}
