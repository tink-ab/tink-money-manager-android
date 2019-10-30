package se.tink.repository.service;


import io.grpc.stub.StreamObserver;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.rpc.AvailableConsentsRequest;
import se.tink.grpc.v1.rpc.ConsentDetailsRequest;
import se.tink.grpc.v1.rpc.GiveConsentRequest;
import se.tink.grpc.v1.rpc.ListConsentsRequest;
import se.tink.grpc.v1.rpc.UserConsentDetailsRequest;
import se.tink.grpc.v1.rpc.UserConsentsListRequest;
import se.tink.grpc.v1.services.ConsentServiceGrpc.ConsentServiceStub;
import se.tink.core.models.consents.AvailableConsentsResponse;
import se.tink.core.models.consents.ConsentDetailsResponse;
import se.tink.core.models.consents.GiveConsentResponse;
import se.tink.core.models.consents.ListConsentsResponse;
import se.tink.core.models.consents.UserConsentDetailsResponse;
import se.tink.core.models.consents.UserConsentsListResponse;
import se.tink.repository.MutationHandler;
import se.tink.repository.TinkNetworkError;

public class ConsentServiceImpl implements ConsentService {

	private final ModelConverter converter;
	private final ConsentServiceStub consentServiceStub;

	public ConsentServiceImpl(ModelConverter converter, ConsentServiceStub consentServiceStub) {
		this.converter = converter;
		this.consentServiceStub = consentServiceStub;
	}

	@Override
	public void listConsents(final MutationHandler<ListConsentsResponse> handler) {

		consentServiceStub.listConsents(ListConsentsRequest.getDefaultInstance(),
			new StreamObserver<se.tink.grpc.v1.rpc.ListConsentsResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.ListConsentsResponse value) {
					handler.onNext(converter.map(value, ListConsentsResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void getConsentDetails(String key,
		final MutationHandler<ConsentDetailsResponse> handler) {
		ConsentDetailsRequest request = ConsentDetailsRequest.newBuilder()
			.setKey(key)
			.build();

		consentServiceStub.consentDetails(request,
			new StreamObserver<se.tink.grpc.v1.rpc.ConsentDetailsResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.ConsentDetailsResponse value) {
					handler.onNext(converter.map(value, ConsentDetailsResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void getAvailableConsents(final MutationHandler<AvailableConsentsResponse> handler) {
		consentServiceStub.availableConsents(AvailableConsentsRequest.getDefaultInstance(),
			new StreamObserver<se.tink.grpc.v1.rpc.AvailableConsentsResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.AvailableConsentsResponse value) {
					handler.onNext(converter.map(value, AvailableConsentsResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void listUserConsents(final MutationHandler<UserConsentsListResponse> handler) {
		consentServiceStub.listUserConsents(UserConsentsListRequest.getDefaultInstance(),
			new StreamObserver<se.tink.grpc.v1.rpc.UserConsentsListResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.UserConsentsListResponse value) {
					handler.onNext(converter.map(value, UserConsentsListResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void giveConsents(se.tink.core.models.consents.GiveConsentRequest consentRequest,
		final MutationHandler<GiveConsentResponse> handler) {
		GiveConsentRequest request = converter.map(consentRequest, GiveConsentRequest.class);
		consentServiceStub.giveConsent(request,
			new StreamObserver<se.tink.grpc.v1.rpc.GiveConsentResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.GiveConsentResponse value) {
					handler.onNext(converter.map(value, GiveConsentResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void getUserConsentDetails(final MutationHandler<UserConsentDetailsResponse> handler) {
		UserConsentDetailsRequest request = UserConsentDetailsRequest.getDefaultInstance();

		consentServiceStub.userConsentDetails(request,
			new StreamObserver<se.tink.grpc.v1.rpc.UserConsentDetailsResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.UserConsentDetailsResponse value) {
					handler.onNext(converter.map(value, UserConsentDetailsResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

}
