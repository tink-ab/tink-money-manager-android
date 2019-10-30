package se.tink.repository.service;

import io.grpc.stub.StreamObserver;
import se.tink.converter.ModelConverter;
import se.tink.core.models.tracking.TrackingConfigurationResponse;
import se.tink.core.models.tracking.TrackingRequest;
import se.tink.grpc.v1.rpc.GetTrackingConfigurationRequest;
import se.tink.grpc.v1.rpc.GetTrackingConfigurationResponse;
import se.tink.grpc.v1.rpc.TrackingSessionRequest;
import se.tink.grpc.v1.rpc.TrackingSessionResponse;
import se.tink.grpc.v1.services.TrackingServiceGrpc;
import se.tink.repository.MutationHandler;
import se.tink.repository.TinkNetworkError;
import timber.log.Timber;

public class TrackingServiceImpl implements TrackingService {

	private final TrackingServiceGrpc.TrackingServiceStub service;
	private final ModelConverter converter;


	public TrackingServiceImpl(TrackingServiceGrpc.TrackingServiceStub stub,
		ModelConverter converter) {
		this.service = stub;
		this.converter = converter;
	}

	@Override
	public void getTrackingConfiguration(final MutationHandler<TrackingConfigurationResponse> mutationHandler) {
		service.getTrackingConfiguration(GetTrackingConfigurationRequest.getDefaultInstance(),
			new StreamObserver<GetTrackingConfigurationResponse>(){
				@Override
				public void onNext(GetTrackingConfigurationResponse value) {
					mutationHandler.onNext(converter.map(value, TrackingConfigurationResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					mutationHandler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					mutationHandler.onCompleted();
				}
			});
	}

	@Override
	public void trackData(TrackingRequest request) {
//        service.trackData(converter.map(request, se.tink.grpc.v1.rpc.TrackingRequest.class), new StreamObserver<TrackingResponse>() {
//            @Override
//            public void onNext(TrackingResponse value) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//        });
	}

	@Override
	public void createSession() {
		service.createSession(TrackingSessionRequest.getDefaultInstance(),
			new StreamObserver<TrackingSessionResponse>() {
				@Override
				public void onNext(TrackingSessionResponse value) {
					Timber.tag("create session").d("onNext: %s", value.getSessionId());
				}

				@Override
				public void onError(Throwable t) {
					Timber.tag("create session").e(t);
					t.printStackTrace();
				}

				@Override
				public void onCompleted() {
					Timber.tag("create session").d("onCompleted");
				}
			});
	}
}
