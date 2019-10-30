package se.tink.repository.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Period;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.database.PeriodDao;
import se.tink.repository.cache.models.IdentifiablePeriodEntity;
import se.tink.repository.cache.models.PeriodEntity;

public class PeriodDatabaseCache extends AbstractDatabaseCache<Map<String, Period>> {

	private PeriodDao dao;
	private ModelConverter converter;


	public PeriodDatabaseCache(CacheDatabase cacheDatabase, ModelConverter converter) {
		super(cacheDatabase);
		dao = cacheDatabase.periodDao();
		this.converter = converter;
	}

	@Override
	public synchronized Map<String, Period> read() {
		List<IdentifiablePeriodEntity> entities = dao.getAll();
		Map<String, Period> periodMap = convert(entities);
		return periodMap;
	}

	@Override
	public synchronized void clearAndInsert(Map<String, Period> item) {
		List<IdentifiablePeriodEntity> entities = convert(item);
		dao.clearAllAndInsert(entities);
	}

	@Override
	public synchronized void insert(Map<String, Period> item) {
		List<IdentifiablePeriodEntity> entities = convert(item);
		dao.insertAll(entities);
	}

	@Override
	public synchronized void update(Map<String, Period> item) {
		List<IdentifiablePeriodEntity> entities = convert(item);
		dao.updateAll(entities);
	}

	@Override
	public synchronized void delete(Map<String, Period> item) {
		List<IdentifiablePeriodEntity> entities = convert(item);
		dao.deleteAll(entities);
	}

	@Override
	public synchronized void clear() {
		dao.clear();
	}

	private ArrayList<IdentifiablePeriodEntity> convert(Map<String, Period> source) {
		ArrayList<IdentifiablePeriodEntity> destination = new ArrayList<>();
		for (String key : source.keySet()) {
			IdentifiablePeriodEntity idPeriod = new IdentifiablePeriodEntity();
			PeriodEntity entity = converter.map(source.get(key), PeriodEntity.class);
			idPeriod.setPeriodEntity(entity);
			idPeriod.setPeriodKey(key);
			destination.add(idPeriod);
		}
		return destination;
	}

	private HashMap<String, Period> convert(List<IdentifiablePeriodEntity> source) {
		HashMap<String, Period> destination = new HashMap<>();
		for (IdentifiablePeriodEntity entity : source) {
			String key = entity.getPeriodKey();
			Period period = converter.map(entity.getPeriodEntity(), Period.class);
			destination.put(key, period);
		}
		return destination;
	}
}
