package se.tink.repository.service;

import se.tink.core.models.tracking.TrackingConfigurationResponse;
import se.tink.core.models.tracking.TrackingRequest;
import se.tink.repository.MutationHandler;

public interface TrackingService {

	void getTrackingConfiguration(final MutationHandler<TrackingConfigurationResponse> mutationHandler);

	void trackData(TrackingRequest request);

	void createSession();
}
