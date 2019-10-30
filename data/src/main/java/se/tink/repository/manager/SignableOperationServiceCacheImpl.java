package se.tink.repository.manager;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.repository.ChangeObserver;
import se.tink.repository.DefaultMutationHandler;
import se.tink.repository.cache.WritableCacheRepository;
import se.tink.repository.service.SignableOperationService;
import se.tink.repository.service.StreamingService;

public class SignableOperationServiceCacheImpl implements SignableOperationService {

	private final WritableCacheRepository<SignableOperation> cache;
	private final StreamingService streamingService;
	private final List<ChangeObserver<SignableOperation>> changeObserverers;

	@Inject
	public SignableOperationServiceCacheImpl(
		StreamingService streamingService,
		final WritableCacheRepository<SignableOperation> cache
	) {
		this.streamingService = streamingService;
		this.cache = cache;
		this.changeObserverers = Lists.newArrayList();

		addCacheAsStreamingObserver();
		setupStreamingService();
	}


	@Override
	public void subscribe(ChangeObserver<SignableOperation> listener) {
		changeObserverers.add(listener);
		readFromCache(listener);
	}

	private void readFromCache(final ChangeObserver<SignableOperation> listener) {
		cache.read(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<SignableOperation> listener) {
		changeObserverers.remove(listener);
	}

	private void setupStreamingService() {
		streamingService.subscribeForSignableOperations(new ChangeObserver<SignableOperation>() {
			@Override
			public void onCreate(List<SignableOperation> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<SignableOperation> changeObserver = changeObserverers.get(i);
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<SignableOperation> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<SignableOperation> changeObserver = changeObserverers.get(i);
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<SignableOperation> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<SignableOperation> changeObserver = changeObserverers.get(i);
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<SignableOperation> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<SignableOperation> changeObserver = changeObserverers.get(i);
					changeObserver.onDelete(items);
				}
			}
		});
	}

	private void addCacheAsStreamingObserver() {
		changeObserverers.add(new ChangeObserver<SignableOperation>() {
			@Override
			public void onCreate(List<SignableOperation> items) {
				cache.add(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onRead(List<SignableOperation> items) {
				cache.reset(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onUpdate(List<SignableOperation> items) {
				cache.update(items, new DefaultMutationHandler<>());
			}

			@Override
			public void onDelete(List<SignableOperation> items) {
				cache.delete(items, new DefaultMutationHandler<>());
			}
		});
	}
}
