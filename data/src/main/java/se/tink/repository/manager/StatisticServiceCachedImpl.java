package se.tink.repository.manager;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.core.models.statistic.Statistic;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StreamingService;

public class StatisticServiceCachedImpl implements StatisticService {

	private final List<ObjectChangeObserver<StatisticTree>> changeObserverers;
	private final StreamingService streamingService;
	private final StatisticService uncachedService;
	private final StasticCache cache;

	@Inject
	public StatisticServiceCachedImpl(StreamingService streamingStub,
		StatisticService uncachedService, StasticCache cache) {
		streamingService = streamingStub;
		this.uncachedService = uncachedService;
		changeObserverers = Lists.newArrayList();
		setupStreamingService();
		this.cache = cache;
		addCacheAsObserver();
	}

	private void setupStreamingService() {
		streamingService.subscribeForStatistics(new ObjectChangeObserver<StatisticTree>() {
			@Override
			public void onCreate(StatisticTree item) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<StatisticTree> changeObserver = changeObserverers.get(i);
					changeObserver.onCreate(item);
				}
			}

			@Override
			public void onRead(StatisticTree item) {

				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<StatisticTree> changeObserver = changeObserverers.get(i);
					changeObserver.onRead(item);

				}
			}

			@Override
			public void onUpdate(StatisticTree item) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<StatisticTree> changeObserver = changeObserverers.get(i);
					changeObserver.onUpdate(item);
				}
			}

			@Override
			public void onDelete(StatisticTree item) {
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<StatisticTree> changeObserver = changeObserverers.get(i);
					changeObserver.onDelete(item);
				}
			}
		});
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, List<Type> types) {
		changeObserverers.add(listener);
		readFromCache(listener, types);
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, Type type) {
		List<Type> types = Lists.newLinkedList();
		types.add(type);
		subscribe(listener, types);
	}

	private void addCacheAsObserver() {
		changeObserverers.add(new ObjectChangeObserver<StatisticTree>() {
			@Override
			public void onCreate(StatisticTree item) {
				cache.clearAndInsert(item);
			}

			@Override
			public void onRead(StatisticTree item) {
				cache.clearAndInsert(item);
			}

			@Override
			public void onUpdate(StatisticTree item) {
				cache.update(item);
			}

			@Override
			public void onDelete(StatisticTree item) {
				cache.delete(item);
			}
		});
	}


	private void readFromCache(final ObjectChangeObserver<StatisticTree> listener,
		final List<Statistic.Type> types) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				StatisticTree tree = cache.read(types);
				if (tree != null && !tree.isEmpty()) {
					listener.onRead(tree);
				}
			}
		}).start();
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<StatisticTree> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void refreshStatistics() {
		// TODO: PFMSDK: Do we need to update the cache here?
		uncachedService.refreshStatistics();
	}
}
