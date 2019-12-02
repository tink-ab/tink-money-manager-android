package se.tink.repository.cache;

import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.database.StatisticsDao;
import se.tink.repository.cache.helpers.StatisticsTreeConverter;
import se.tink.repository.cache.models.StatisticEntity;

public class StatisticDatabaseCache implements StasticCache, Clearable {

	private ModelConverter modelConverter;

	private StatisticsDao statisticsDao;
	private Flowable<List<StatisticEntity>> allStatistics;
	private CacheDatabase cacheDatabase;

	public StatisticDatabaseCache(CacheDatabase cacheDatabase, ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
		this.cacheDatabase = cacheDatabase;
		DataWipeManager.sharedInstance().register(this);
		statisticsDao = cacheDatabase.statisticsDao();
		allStatistics = statisticsDao.getAll();
	}

	@Override
	public synchronized StatisticTree read() {
		List<StatisticEntity> databaseEntities = allStatistics.blockingFirst();
		StatisticTree tree = StatisticsTreeConverter.builder(databaseEntities, modelConverter)
			.build();
		return tree;
	}

	@Override
	public synchronized void clearAndInsert(StatisticTree item) {
		List<StatisticEntity> statisticEntities = new ArrayList<>();

		StatisticsTreeConverter.flattener(modelConverter).flattenTree(item, statisticEntities);

		statisticsDao.clearAllAndInsert(statisticEntities);
	}

	@Override
	public synchronized void insert(StatisticTree item) {
		List<StatisticEntity> statisticEntities = new ArrayList<>();

		StatisticsTreeConverter.flattener(modelConverter).flattenTree(item, statisticEntities);

		statisticsDao.insertAll(statisticEntities);
	}

	@Override
	public synchronized void update(StatisticTree item) {
		List<StatisticEntity> statisticEntities = new ArrayList<>();

		StatisticsTreeConverter.flattener(modelConverter).flattenTree(item, statisticEntities);

		statisticsDao.upsert(statisticEntities);
	}

	@Override
	public synchronized void delete(StatisticTree item) {
		List<StatisticEntity> statisticEntities = new ArrayList<>();

		StatisticsTreeConverter.flattener(modelConverter).flattenTree(item, statisticEntities);

		statisticsDao.deleteAll(statisticEntities);
	}

	@Override
	public synchronized void clear() {
		statisticsDao.clear();
	}

}
