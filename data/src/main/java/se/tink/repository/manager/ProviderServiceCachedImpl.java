package se.tink.repository.manager;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.core.models.provider.Provider;
import se.tink.repository.ChangeObserver;
import se.tink.repository.DefaultMutationHandler;
import se.tink.repository.MutationHandler;
import se.tink.repository.cache.WritableCacheRepository;
import se.tink.repository.service.ProviderService;
import se.tink.repository.service.StreamingService;

public class ProviderServiceCachedImpl implements ProviderService {

	private final ProviderService service;
	private final WritableCacheRepository<Provider> cache;
	private final StreamingService streamingService;
	private final List<ChangeObserver<Provider>> changeObservers;

	@Inject
	public ProviderServiceCachedImpl(
		ProviderService service,
		WritableCacheRepository<Provider> cache,
		StreamingService streamingServiceStub
	) {
		this.service = service;
		this.cache = cache;
		this.streamingService = streamingServiceStub;
		this.changeObservers = Lists.newLinkedList();

		addCacheAsStreamingObserver();
		setupStreamingService();
	}

	@Override
	public void subscribe(ChangeObserver<Provider> listener) {
		changeObservers.add(listener);
		readFromCache(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<Provider> listener) {
		changeObservers.remove(listener);
	}

	@Override
	public void listSuggestions(MutationHandler<List<Provider>> handler) {
		service.listSuggestions(handler);
	}


	private void readFromCache(final ChangeObserver<Provider> listener) {
		cache.read(listener);
	}

	private void setupStreamingService() {
		streamingService.subscribeForProviders(new ChangeObserver<Provider>() {
			@Override
			public void onCreate(List<Provider> items) {
				for (int i = changeObservers.size() - 1; i >= 0; i--) {
					ChangeObserver<Provider> changeObserver = changeObservers.get(i);
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<Provider> items) {
				for (int i = changeObservers.size() - 1; i >= 0; i--) {
					ChangeObserver<Provider> changeObserver = changeObservers.get(i);
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<Provider> items) {
				for (int i = changeObservers.size() - 1; i >= 0; i--) {
					ChangeObserver<Provider> changeObserver = changeObservers.get(i);
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<Provider> items) {
				for (int i = changeObservers.size() - 1; i >= 0; i--) {
					ChangeObserver<Provider> changeObserver = changeObservers.get(i);
					changeObserver.onDelete(items);
				}
			}
		});
	}

	private void addCacheAsStreamingObserver() {
		changeObservers.add(new ChangeObserver<Provider>() {
			@Override
			public void onCreate(List<Provider> items) {
				cache.add(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onRead(List<Provider> items) {
				cache.reset(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onUpdate(List<Provider> items) {
				cache.update(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onDelete(List<Provider> items) {
				cache.delete(items, new DefaultMutationHandler<>());
			}
		});
	}

	public void listProviders(MutationHandler<List<Provider>> mutationHandler, boolean includeDemoProviders) {
		service.listProviders(mutationHandler, includeDemoProviders);
	}
}