package se.tink.repository.manager;

import javax.inject.Inject;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.service.StatisticService;

public class StatisticServiceCachedImpl implements StatisticService {

	private final StatisticService uncachedService;
	private final StasticCache cache;

	@Inject
	public StatisticServiceCachedImpl(StatisticService uncachedService, StasticCache cache) {
		this.uncachedService = uncachedService;
		this.cache = cache;
		addCacheAsObserver();
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener) {
		uncachedService.subscribe(listener);
		readFromCache(listener);
	}

	private void addCacheAsObserver() {
		uncachedService.subscribe(new ObjectChangeObserver<StatisticTree>() {
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


	private void readFromCache(final ObjectChangeObserver<StatisticTree> listener) {
		new Thread(() -> {
			StatisticTree tree = cache.read();
			if (tree != null && !tree.isEmpty()) {
				listener.onRead(tree);
			}
		}).start();
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<StatisticTree> listener) {
		uncachedService.unsubscribe(listener);
	}

	@Override
	public void refreshStatistics() {
		uncachedService.refreshStatistics();
	}
}
