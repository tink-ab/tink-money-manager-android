package se.tink.repository.manager;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.cache.Cache;
import se.tink.repository.service.FollowService;
import se.tink.repository.service.StreamingService;

public class FollowRepositoryCachedImpl implements FollowService {

	private final FollowService service;
	private final Cache<List<FollowItem>> cache;
	private final StreamingService streamingService;
	private final ModelConverter converter;
	private final List<ChangeObserver<FollowItem>> changeObserverers;

	@Inject
	public FollowRepositoryCachedImpl(final FollowService service,
		StreamingService streamingService,
		final Cache<List<FollowItem>> cache, final ModelConverter converter) {
		this.service = service;
		this.streamingService = streamingService;
		this.cache = cache;
		this.converter = converter;
		this.changeObserverers = Lists.newArrayList();

		addCacheAsStreamingObserver();
		setupStreamingService();
	}

	@Override
	public void subscribe(ChangeObserver<FollowItem> listener) {
		changeObserverers.add(listener);
		readFromCache(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<FollowItem> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void createFollowItem(FollowItem item,
		final MutationHandler<FollowItem> handler) {

		service.createFollowItem(item, handler);
	}

	@Override
	public void deleteFollowItem(FollowItem item, MutationHandler<Void> handler) {
		service.deleteFollowItem(item, handler);
	}

	@Override
	public void updateFollowItem(FollowItem item, MutationHandler<FollowItem> mutationHandler) {
		service.updateFollowItem(item, mutationHandler);
	}

	@Override
	public void getDailyHistoryForFollowItem(FollowItem item,
		MutationHandler<List<PeriodExactNumberPair>> mutationHandler) {
			service.getDailyHistoryForFollowItem(item, mutationHandler);
	}

	private void addCacheAsStreamingObserver() {
		changeObserverers.add(new ChangeObserver<FollowItem>() {
			@Override
			public void onCreate(List<FollowItem> items) {
				cache.insert(items);
			}

			@Override
			public void onRead(List<FollowItem> items) {
				cache.clearAndInsert(items);
			}

			@Override
			public void onUpdate(List<FollowItem> items) {
				cache.update(items);
			}

			@Override
			public void onDelete(List<FollowItem> items) {
				cache.delete(items);
			}
		});
	}

	private void setupStreamingService() {
		streamingService.subscribeForFollowItems(new ChangeObserver<FollowItem>() {
			@Override
			public void onCreate(List<FollowItem> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<FollowItem> changeObserver = changeObserverers.get(i);
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<FollowItem> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<FollowItem> changeObserver = changeObserverers.get(i);
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<FollowItem> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<FollowItem> changeObserver = changeObserverers.get(i);
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<FollowItem> items) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ChangeObserver<FollowItem> changeObserver = changeObserverers.get(i);
					changeObserver.onDelete(items);
				}
			}
		});
	}

	private void readFromCache(final ChangeObserver<FollowItem> listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<FollowItem> items = cache.read();
				if (items != null && items.size() > 0) {
					listener.onRead(items);
				}
			}
		}).start();
//		cache.list(new MutationHandler<List<FollowItem>>() {
//			@Override
//			public void onError(TinkNetworkError error) {}
//
//			@Override
//			public void onCompleted() {}
//
//			@Override
//			public void onNext(List<FollowItem> item) {
//				if (!item.isEmpty()) {
//					listener.onRead(item);
//				}
//			}
//		});
	}

}
