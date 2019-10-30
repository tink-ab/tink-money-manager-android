package se.tink.repository.service;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.provider.Provider;
import se.tink.grpc.v1.rpc.ListProvidersResponse;
import se.tink.grpc.v1.rpc.ProviderListRequest;
import se.tink.grpc.v1.rpc.ProviderSuggestRequest;
import se.tink.grpc.v1.services.ProviderServiceGrpc;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class ProviderServiceImpl implements ProviderService {

	private final ProviderServiceGrpc.ProviderServiceStub service;
	private final List<ChangeObserver<Provider>> changeObserverers;
	private final StreamingService streamingService;
	private final ModelConverter converter;

	@Inject
	public ProviderServiceImpl(ProviderServiceGrpc.ProviderServiceStub stub, StreamingService streamingStub,
		ModelConverter converter) {
		service = stub;
		streamingService = streamingStub;
		this.converter = converter;
		changeObserverers = Lists.newArrayList();
		setupStreamingService();
	}

	private void setupStreamingService() {
		streamingService.subscribeForProviders(new ChangeObserver<Provider>() {
			@Override
			public void onCreate(List<Provider> items) {
				for (ChangeObserver<Provider> changeObserver : changeObserverers) {
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<Provider> items) {
				for (ChangeObserver<Provider> changeObserver : changeObserverers) {
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<Provider> items) {
				for (ChangeObserver<Provider> changeObserver : changeObserverers) {
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<Provider> items) {
				for (ChangeObserver<Provider> changeObserver : changeObserverers) {
					changeObserver.onDelete(items);
				}
			}
		});
	}

	@Override
	public void listSuggestions(final MutationHandler<List<Provider>> handler) {
		service.suggest(ProviderSuggestRequest.getDefaultInstance(),
			new SimpleStreamObserver<ListProvidersResponse>(handler) {
				@Override
				public void onNext(ListProvidersResponse value) {
					handler.onNext(converter.map(value.getProvidersList(), Provider.class));
				}
			});
	}

	@Override
	public void subscribe(ChangeObserver<Provider> listener) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<Provider> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void listProviders(MutationHandler<List<Provider>> mutationHandler, boolean includeDemoProviders) {
		service.listProviders(ProviderListRequest.newBuilder().setIncludeTestType(includeDemoProviders).build(),
				new SimpleStreamObserver<ListProvidersResponse>(mutationHandler) {
					@Override
					public void onNext(ListProvidersResponse value) {
						List<Provider> providers = converter.map(value.getProvidersList(), Provider.class);
						mutationHandler.onNext(providers);
					}
				});
	}
}
