package se.tink.repository.cache;


import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.database.FollowExpenseCategoryCodeDao;
import se.tink.repository.cache.database.FollowHistoricalAmountDao;
import se.tink.repository.cache.database.FollowItemDao;
import se.tink.repository.cache.database.FollowItemTransactionDao;
import se.tink.repository.cache.database.FollowSavingsAccountIdDao;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.follow.FollowExpenseCategoryCodeEntity;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;
import se.tink.repository.cache.models.follow.FollowItemEntity;
import se.tink.repository.cache.models.follow.FollowItemTransactionEntity;
import se.tink.repository.cache.models.follow.FollowItemWithRelations;
import se.tink.repository.cache.models.follow.FollowSavingAccountIdEntity;

public class FollowItemDatabaseCache extends AbstractDatabaseCache<List<FollowItem>> {

	private FollowItemDao followItemDao;
	private FollowHistoricalAmountDao followHistoricalAmountDao;
	private FollowItemTransactionDao followItemTransactionDao;
	private FollowExpenseCategoryCodeDao followExpenseCategoryCodeDao;
	private FollowSavingsAccountIdDao followSavingsAccountIdDao;

	private final ModelConverter modelConverter;

	public FollowItemDatabaseCache(CacheDatabase cacheDatabase, ModelConverter modelConverter) {
		super(cacheDatabase);
		followItemDao = cacheDatabase.followItemDao();
		followHistoricalAmountDao = cacheDatabase.followHistoricalAmountDao();
		followItemTransactionDao = cacheDatabase.followItemTransactionDao();
		followExpenseCategoryCodeDao = cacheDatabase.followExpenseCategoryCodeDao();
		followSavingsAccountIdDao = cacheDatabase.followSavingsAccountIdDao();
		this.modelConverter = modelConverter;
	}

	@Override
	public synchronized List<FollowItem> read() {

		List<FollowItemWithRelations> entities = followItemDao.getAll();
		List<FollowItem> destination = modelConverter.map(entities, FollowItem.class);

		for (FollowItem item : destination) {
			List<TransactionEntity> transactionEntities = followItemTransactionDao
				.getTransactionsForFollowItem(item.getId());
		}

		return destination;
	}

	@Override
	public synchronized void clearAndInsert(List<FollowItem> items) {
		List<FollowItemEntity> entities = new ArrayList<>();
		List<FollowHistoricalAmountEntity> historicalAmounts = new ArrayList<>();
		List<FollowItemTransactionEntity> followItemTransactionEntities = new ArrayList<>();
		List<FollowExpenseCategoryCodeEntity> categoryCodeEntities = new ArrayList<>();
		List<FollowSavingAccountIdEntity> accountIdEntities = new ArrayList<>();

		convert(items, entities, historicalAmounts, followItemTransactionEntities,
			categoryCodeEntities, accountIdEntities);

		followHistoricalAmountDao.clearAndInsertAll(historicalAmounts);
		followItemDao.clearAndInsertAll(entities);
		followExpenseCategoryCodeDao.clearAndInsertAll(categoryCodeEntities);
		followSavingsAccountIdDao.clearAndInsertAll(accountIdEntities);
		followItemTransactionDao.clearAndInsertAll(followItemTransactionEntities);
	}

	@Override
	public synchronized void insert(List<FollowItem> items) {

		List<FollowItemEntity> entities = new ArrayList<>();
		List<FollowHistoricalAmountEntity> historicalAmounts = new ArrayList<>();
		List<FollowItemTransactionEntity> followItemTransactionEntities = new ArrayList<>();
		List<FollowExpenseCategoryCodeEntity> categoryCodeEntities = new ArrayList<>();
		List<FollowSavingAccountIdEntity> accountIdEntities = new ArrayList<>();

		convert(items, entities, historicalAmounts, followItemTransactionEntities,
			categoryCodeEntities, accountIdEntities);

		followHistoricalAmountDao.insertAll(historicalAmounts);
		followItemDao.insertAll(entities);
		followExpenseCategoryCodeDao.insertAll(categoryCodeEntities);
		followSavingsAccountIdDao.insertAll(accountIdEntities);
		followItemTransactionDao.insertAll(followItemTransactionEntities);
	}

	@Override
	public synchronized void update(List<FollowItem> items) {
		List<FollowItemEntity> entities = new ArrayList<>();
		List<FollowHistoricalAmountEntity> historicalAmounts = new ArrayList<>();
		List<FollowItemTransactionEntity> followItemTransactionEntities = new ArrayList<>();
		List<FollowExpenseCategoryCodeEntity> categoryCodeEntities = new ArrayList<>();
		List<FollowSavingAccountIdEntity> accountIdEntities = new ArrayList<>();

		convert(items, entities, historicalAmounts, followItemTransactionEntities,
			categoryCodeEntities, accountIdEntities);

		followHistoricalAmountDao.updateAll(historicalAmounts);
		followItemDao.updateAll(entities);
		followExpenseCategoryCodeDao.updateAll(categoryCodeEntities);
		followSavingsAccountIdDao.updateAll(accountIdEntities);
		followItemTransactionDao.updateAll(followItemTransactionEntities);
	}

	@Override
	public synchronized void delete(List<FollowItem> items) {
		List<FollowItemEntity> entities = new ArrayList<>();
		List<FollowHistoricalAmountEntity> historicalAmounts = new ArrayList<>();
		List<FollowItemTransactionEntity> followItemTransactionEntities = new ArrayList<>();
		List<FollowExpenseCategoryCodeEntity> categoryCodeEntities = new ArrayList<>();
		List<FollowSavingAccountIdEntity> accountIdEntities = new ArrayList<>();

		convert(items, entities, historicalAmounts, followItemTransactionEntities,
			categoryCodeEntities, accountIdEntities);
		followItemDao.deleteAll(entities);
		//The rest will cascade
	}

	@Override
	public synchronized void clear() {
		followItemDao.clear();
	}

	private synchronized void convert(List<FollowItem> source, List<FollowItemEntity> entities,
		List<FollowHistoricalAmountEntity> historicalAmounts,
		List<FollowItemTransactionEntity> followItemTransactionEntities,
		List<FollowExpenseCategoryCodeEntity> categoryCodeEntities,
		List<FollowSavingAccountIdEntity> accountIdEntities) {

		List<FollowItemWithRelations> followItemWithRelations = modelConverter
			.map(source, FollowItemWithRelations.class);

		for (FollowItemWithRelations item : followItemWithRelations) {
			entities.add(item.getFollowItemEntity());
			if (item.getTransactionEntities() != null) {
				followItemTransactionEntities.addAll(item.getTransactionEntities());
			}
			if (item.getHistoricalAmounts() != null) {
				historicalAmounts.addAll(item.getHistoricalAmounts());
			}

			if (item.getExpenseCriteriaCodes() != null) {
				for (String code : item.getExpenseCriteriaCodes()) {
					FollowExpenseCategoryCodeEntity codeEntity = new FollowExpenseCategoryCodeEntity();
					codeEntity.setCategoryCode(code);
					codeEntity.setFollowItemId(item.getFollowItemEntity().getId());
					categoryCodeEntities.add(codeEntity);
				}
			}

			if (item.getSavingsAccountIds() != null) {
				for (String accountId : item.getSavingsAccountIds()) {
					FollowSavingAccountIdEntity accountIdEntity = new FollowSavingAccountIdEntity();
					accountIdEntity.setAccountId(accountId);
					accountIdEntity.setFollowItemId(item.getFollowItemEntity().getId());
					accountIdEntities.add(accountIdEntity);
				}
			}
		}

	}
}
