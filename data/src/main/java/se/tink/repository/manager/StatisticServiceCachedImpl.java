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
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, List<Type> types) {
		uncachedService.subscribe(listener, types);
		readFromCache(listener, Lists.newArrayList(Statistic.Type.values()));
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, Type type) {
		uncachedService.subscribe(listener, type);
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
		}, Lists.newArrayList(Statistic.Type.values()));
	}


	private void readFromCache(final ObjectChangeObserver<StatisticTree> listener,
		final List<Statistic.Type> types) {
		new Thread(() -> {
			StatisticTree tree = cache.read(types);
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
