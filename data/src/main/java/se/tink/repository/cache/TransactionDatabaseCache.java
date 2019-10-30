package se.tink.repository.cache;

import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Transaction;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.database.CounterpartDao;
import se.tink.repository.cache.database.TagDao;
import se.tink.repository.cache.database.TransactionDao;
import se.tink.repository.cache.models.CounterpartEntity;
import se.tink.repository.cache.models.TagEntity;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.TransactionWithAllRelations;

public class TransactionDatabaseCache extends AbstractDatabaseCache<List<Transaction>> {

	private final CounterpartDao counterpartDao;
	private TransactionDao transactionDao;
	private TagDao tagDao;
	private final ModelConverter modelConverter;

	public TransactionDatabaseCache(CacheDatabase cacheDatabase, ModelConverter modelConverter) {
		super(cacheDatabase);
		transactionDao = cacheDatabase.transactionDao();
		tagDao = cacheDatabase.tagDao();
		counterpartDao = cacheDatabase.counterpartDao();
		this.modelConverter = modelConverter;
	}

	@Override
	public synchronized List<Transaction> read() {
		List<TransactionWithAllRelations> transactionWithAllRelationsList = transactionDao
			.getAllAndAllRelations();

		return modelConverter.map(transactionWithAllRelationsList, Transaction.class);
	}

	@Override

	public synchronized void clearAndInsert(List<Transaction> item) {
		List<TransactionWithAllRelations> entitiesWithRelations = modelConverter
			.map(item, TransactionWithAllRelations.class);
		List<TagEntity> tags = new ArrayList<>();
		List<CounterpartEntity> counterpartEntities = new ArrayList<>();
		for (TransactionWithAllRelations entityWithRelations : entitiesWithRelations) {
			tags.addAll(entityWithRelations.getTagEntities());
			counterpartEntities.addAll(entityWithRelations.getCounterpartEntities());
		}
		transactionDao.clearAllAndInsert(new ArrayList<TransactionEntity>(entitiesWithRelations));
		tagDao.clearAllAndInsert(tags);
		counterpartDao.clearAllAndInsert(counterpartEntities);
	}

	@Override
	public synchronized void insert(List<Transaction> item) {
		List<TransactionWithAllRelations> entitiesWithRelations = modelConverter
			.map(item, TransactionWithAllRelations.class);
		List<TagEntity> tags = new ArrayList<>();
		List<CounterpartEntity> counterpartEntities = new ArrayList<>();
		for (TransactionWithAllRelations entityWithRelations : entitiesWithRelations) {
			tags.addAll(entityWithRelations.getTagEntities());
			counterpartEntities.addAll(entityWithRelations.getCounterpartEntities());
		}
		transactionDao.insertAll(new ArrayList<TransactionEntity>(entitiesWithRelations));
		tagDao.insertAll(tags);
		counterpartDao.insertAll(counterpartEntities);
	}

	@Override
	public synchronized void update(List<Transaction> item) {
		List<TransactionWithAllRelations> entitiesWithRelations = modelConverter
			.map(item, TransactionWithAllRelations.class);
		List<TagEntity> tags = new ArrayList<>();
		List<CounterpartEntity> counterpartEntities = new ArrayList<>();
		List<String> transactionIds = new ArrayList<>();
		for (TransactionWithAllRelations entityWithRelations : entitiesWithRelations) {
			transactionIds.add(entityWithRelations.getId());
			counterpartEntities.addAll(entityWithRelations.getCounterpartEntities());
			tags.addAll(entityWithRelations.getTagEntities());
		}
		transactionDao.updateAll(new ArrayList<TransactionEntity>(entitiesWithRelations));
		counterpartDao
			.clearWithTransactionIdAndInsertAllCounterparts(transactionIds, counterpartEntities);
		tagDao.clearWithTransactionIdAndInsertAllTags(transactionIds, tags);
	}

	@Override
	public synchronized void delete(List<Transaction> item) {
		List<TransactionWithAllRelations> entitiesWithRelations = modelConverter
			.map(item, TransactionWithAllRelations.class);
		List<TagEntity> tags = new ArrayList<>();
		for (TransactionWithAllRelations entityWithRelations : entitiesWithRelations) {
			tags.addAll(entityWithRelations.getTagEntities());
		}
		transactionDao.deleteAll(new ArrayList<TransactionEntity>(entitiesWithRelations));
		tagDao.deleteAll(tags);
		//No need to delete counterparts, deletion will cascade
	}

	@Override
	public synchronized void clear() {
		transactionDao.clear();
		tagDao.clear();
		counterpartDao.clear();
	}
}
