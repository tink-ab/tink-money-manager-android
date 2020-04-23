package se.tink.converter.tracking;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.tracking.TrackingView;
import se.tink.modelConverter.AbstractConverter;

public class TrackingViewToTrackingViewDTOConverter extends
	AbstractConverter<TrackingView, se.tink.grpc.v1.models.TrackingView> {

	private ModelConverter modelConverter;

	public TrackingViewToTrackingViewDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.TrackingView convert(TrackingView source) {
		se.tink.grpc.v1.models.TrackingView.Builder destination = se.tink.grpc.v1.models.TrackingView
			.newBuilder();
		destination.setDate(modelConverter.map(source.getDate(), Timestamp.class));
		if (source.getName() != null) {
			destination.setName(source.getName());
		}

		return destination.build();
	}

	@Override
	public Class<TrackingView> getSourceClass() {
		return TrackingView.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.TrackingView> getDestinationClass() {
		return se.tink.grpc.v1.models.TrackingView.class;
	}
}