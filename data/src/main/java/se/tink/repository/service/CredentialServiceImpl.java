package se.tink.repository.service;

import androidx.annotation.NonNull;
import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.extensions.CredentialExtensionsKt;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.misc.Field;
import se.tink.grpc.v1.rpc.CancelSupplementInformationRequest;
import se.tink.grpc.v1.rpc.CancelSupplementInformationResponse;
import se.tink.grpc.v1.rpc.CreateCredentialRequest;
import se.tink.grpc.v1.rpc.CreateCredentialResponse;
import se.tink.grpc.v1.rpc.DeleteCredentialRequest;
import se.tink.grpc.v1.rpc.DeleteCredentialResponse;
import se.tink.grpc.v1.rpc.DisableCredentialRequest;
import se.tink.grpc.v1.rpc.DisableCredentialResponse;
import se.tink.grpc.v1.rpc.EnableCredentialRequest;
import se.tink.grpc.v1.rpc.EnableCredentialResponse;
import se.tink.grpc.v1.rpc.ListCredentialsRequest;
import se.tink.grpc.v1.rpc.ListCredentialsResponse;
import se.tink.grpc.v1.rpc.RefreshCredentialsRequest;
import se.tink.grpc.v1.rpc.RefreshCredentialsResponse;
import se.tink.grpc.v1.rpc.SupplementInformationRequest;
import se.tink.grpc.v1.rpc.SupplementInformationResponse;
import se.tink.grpc.v1.rpc.ThirdPartyCallbackRequest;
import se.tink.grpc.v1.rpc.UpdateCredentialRequest;
import se.tink.grpc.v1.rpc.UpdateCredentialResponse;
import se.tink.grpc.v1.services.CredentialServiceGrpc;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;
import se.tink.repository.TinkNetworkError;
import se.tink.repository.VoidStreamObserver;

public class CredentialServiceImpl implements CredentialService {

	private final CredentialServiceGrpc.CredentialServiceStub service;
	private final StreamingService streamingService;
	private final ModelConverter converter;
	private final List<ChangeObserver<Credential>> changeObserverers;

	@Inject
	public CredentialServiceImpl(StreamingService streamingStub, ModelConverter converter,
		CredentialServiceGrpc.CredentialServiceStub service) {
		streamingService = streamingStub;
		this.converter = converter;
		changeObserverers = Lists.newArrayList();
		this.service = service;
		setupStreamingService();
	}

	private void setupStreamingService() {
		streamingService.subscribeForCredentials(new ChangeObserver<Credential>() {
			@Override
			public void onCreate(List<Credential> items) {
				for (ChangeObserver<Credential> changeObserver : changeObserverers) {
					changeObserver.onCreate(Lists.newArrayList(items));
				}
			}

			@Override
			public void onRead(List<Credential> items) {
				for (ChangeObserver<Credential> changeObserver : changeObserverers) {
					changeObserver.onRead(Lists.newArrayList(items));
				}
			}

			@Override
			public void onUpdate(List<Credential> items) {
				for (ChangeObserver<Credential> changeObserver : changeObserverers) {
					changeObserver.onUpdate(Lists.newArrayList(items));
				}
			}

			@Override
			public void onDelete(List<Credential> items) {
				for (ChangeObserver<Credential> changeObserver : changeObserverers) {
					changeObserver.onDelete(Lists.newArrayList(items));
				}
			}
		});
	}

	@Override
	public void list(final MutationHandler<List<Credential>> handler) {

		service.listCredentials(ListCredentialsRequest.getDefaultInstance(),
			new SimpleStreamObserver<ListCredentialsResponse>(handler) {
				@Override
				public void onNext(ListCredentialsResponse item) {
					List<Credential> credentials = converter
						.map(item.getCredentialsList(), Credential.class);
					handler.onNext(credentials);
				}
			});
	}

	@Override
	public void create(Credential credential, final MutationHandler<Credential> handler) {
		CreateCredentialRequest request = CreateCredentialRequest.newBuilder()
			.setProviderName(credential.getProviderName())
			.putAllFields(credential.fieldsMap())
			.build();

		service
			.createCredential(request, new SimpleStreamObserver<CreateCredentialResponse>(handler) {
				@Override
				public void onNext(CreateCredentialResponse value) {
					handler.onNext(converter.map(value.getCredential(), Credential.class));
				}
			});
	}

	@Override
	public void delete(Credential credential, final MutationHandler<Void> handler) {

		DeleteCredentialRequest request = DeleteCredentialRequest.newBuilder()
			.setCredentialId(credential.getId()).build();

		service
			.deleteCredential(request, new SimpleStreamObserver<DeleteCredentialResponse>(handler) {
				@Override
				public void onNext(DeleteCredentialResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void enableCredential(Credential credential, final MutationHandler<Void> handler) {
		EnableCredentialRequest.Builder request = EnableCredentialRequest.newBuilder();
		request.setCredentialId(credential.getId());

		service.enableCredential(request.build(),
			new SimpleStreamObserver<EnableCredentialResponse>(handler) {
				@Override
				public void onNext(EnableCredentialResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void disableCredential(Credential credential, final MutationHandler<Void> handler) {
		DisableCredentialRequest.Builder builder = DisableCredentialRequest.newBuilder();
		builder.setCredentialId(credential.getId());
		service.disableCredential(builder.build(),
			new SimpleStreamObserver<DisableCredentialResponse>(handler) {
				@Override
				public void onNext(DisableCredentialResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void update(String credentialId, List<Field> fields, final MutationHandler<Credential> handler) {
		UpdateCredentialRequest request = UpdateCredentialRequest.newBuilder()
			.setCredentialId(credentialId)
			.putAllFields(CredentialExtensionsKt.toMap(fields))
			.build();

		service
			.updateCredential(request, new SimpleStreamObserver<UpdateCredentialResponse>(handler) {
				@Override
				public void onNext(UpdateCredentialResponse value) {
					handler.onNext(converter.map(value.getCredential(), Credential.class));
				}
			});
	}

	@Override
	public void refresh(List<String> credentialIds, final MutationHandler<Void> handler) {
		RefreshCredentialsRequest.Builder builder = RefreshCredentialsRequest.newBuilder();
		builder.addAllCredentialIds(credentialIds);
		RefreshCredentialsRequest request = builder.build();

		service.refreshCredentials(request,
			new SimpleStreamObserver<RefreshCredentialsResponse>(handler) {
				@Override
				public void onNext(RefreshCredentialsResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void supplementInformation(Credential credential, final MutationHandler<Void> handler) {
		SupplementInformationRequest.Builder builder = SupplementInformationRequest.newBuilder();
		builder.putAllSupplementalInformationFields(credential.getSupplementalInformationMap());
		builder.setCredentialId(credential.getId());
		service.supplementInformation(builder.build(),
			new VoidStreamObserver<SupplementInformationResponse>(handler));
	}

	@Override
	public void cancelSupplementalInformation(@NonNull Credential credential,
		final MutationHandler<Void> handler) {
		CancelSupplementInformationRequest request = CancelSupplementInformationRequest
			.newBuilder()
			.setCredentialId(credential.getId())
			.build();
		service.cancelSupplementInformation(request,
			new StreamObserver<CancelSupplementInformationResponse>() {
				@Override
				public void onNext(CancelSupplementInformationResponse value) {
					handler.onNext(null);
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
	public void subscribe(ChangeObserver<Credential> listener) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<Credential> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void thirdPartyCallback(String state, Map<String, String> parameters,
		MutationHandler<Void> handler) {
		ThirdPartyCallbackRequest.Builder builder = ThirdPartyCallbackRequest.newBuilder();
		builder.setState(state);
		builder.putAllParameters(parameters);
		service.thirdPartyCallback(builder.build(), new VoidStreamObserver<>(handler));
	}
}
