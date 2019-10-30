package se.tink.repository.cache;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.repository.cache.database.AccountDao;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.models.AccountAndIdentifiers;
import se.tink.repository.cache.models.AccountEntity;
import se.tink.repository.cache.models.AccountEntityAndSourceIdentifier;
import se.tink.repository.cache.models.AccountSourceIdentifierDao;
import se.tink.repository.cache.models.AccountSourceIdentifierEntity;

public class AccountDatabaseCache extends AbstractDatabaseLiveDataCache<List<Account>> {

	private AccountDao accountDao;
	private AccountSourceIdentifierDao accountSourceIdentifierDao;

	private final ModelConverter modelConverter;

	public AccountDatabaseCache(CacheDatabase cacheDatabase, ModelConverter modelConverter) {
		super(cacheDatabase);
		accountDao = cacheDatabase.accountDao();
		accountSourceIdentifierDao = cacheDatabase.accountSourceIdentifierDao();

		this.modelConverter = modelConverter;
	}

    @NonNull
    @Override
    public LiveData<List<Account>> read() {
        return Transformations.map(
                accountDao.getAllWithIdentifiers(),
                accountsAndIdentifiers -> {
                    List<Account> accountList = new ArrayList<>();
                    for (AccountAndIdentifiers item : accountsAndIdentifiers) {
                        Account account = modelConverter.map(item.account, Account.class);
                        account.setIdentifiers(modelConverter.map(item.identifiers, String.class));
                        accountList.add(account);
                    }

                    return accountList;
                });
        // TODO read transfer destinations
        // TODO read account destination types
    }

	@Override
	public synchronized void clearAndInsert(List<Account> item) {
		List<AccountEntityAndSourceIdentifier> accountEntities = modelConverter
			.map(item, AccountEntityAndSourceIdentifier.class);
		List<AccountEntity> entities = new ArrayList<>();
		List<AccountSourceIdentifierEntity> sourceIdentifierEntities = new ArrayList<>();

		for (AccountEntityAndSourceIdentifier accountEntityAndSourceIdentifier : accountEntities) {
			entities.add(accountEntityAndSourceIdentifier.getEntity());
			sourceIdentifierEntities
				.addAll(accountEntityAndSourceIdentifier.getSourceIdentifierEntities());

		}

		accountDao.clearAndInsertAll(entities);
		accountSourceIdentifierDao.clearAndInsertAll(sourceIdentifierEntities);
		//TODO insert transfer destinations
		//TODO Insert account destination types
	}

	@Override
	public synchronized void insert(List<Account> item) {
		List<AccountEntityAndSourceIdentifier> accountEntities = modelConverter
			.map(item, AccountEntityAndSourceIdentifier.class);
		List<AccountEntity> entities = new ArrayList<>();
		List<AccountSourceIdentifierEntity> sourceIdentifierEntities = new ArrayList<>();

		for (AccountEntityAndSourceIdentifier accountEntityAndSourceIdentifier : accountEntities) {
			entities.add(accountEntityAndSourceIdentifier.getEntity());
			sourceIdentifierEntities
				.addAll(accountEntityAndSourceIdentifier.getSourceIdentifierEntities());


		}
		accountDao.insertAll(entities);
		accountSourceIdentifierDao.insertAll(sourceIdentifierEntities);

		//TODO insert transfer destinations
		//TODO Insert account destination types
	}

	@Override
	public synchronized void update(List<Account> items) {
		if (items == null || items.size() < 1) {
			return;
		}
		List<AccountEntityAndSourceIdentifier> accountEntities = modelConverter
			.map(items, AccountEntityAndSourceIdentifier.class);
		List<AccountEntity> entities = new ArrayList<>();
		List<AccountSourceIdentifierEntity> sourceIdentifierEntities = new ArrayList<>();

		for (AccountEntityAndSourceIdentifier accountEntityAndSourceIdentifier : accountEntities) {
			entities.add(accountEntityAndSourceIdentifier.getEntity());
			sourceIdentifierEntities
				.addAll(accountEntityAndSourceIdentifier.getSourceIdentifierEntities());

		}
		accountDao.updateAll(entities);
		accountSourceIdentifierDao.updateAll(sourceIdentifierEntities);

		//TODO update transfer destinations
		//TODO update account destination types
	}

	@Override
	public synchronized void delete(List<Account> items) {
		if (items == null || items.size() < 1) {
			return;
		}
		List<AccountEntityAndSourceIdentifier> accountEntities = modelConverter
			.map(items, AccountEntityAndSourceIdentifier.class);
		List<AccountEntity> entities = new ArrayList<>();
		List<AccountSourceIdentifierEntity> sourceIdentifierEntities = new ArrayList<>();

		for (AccountEntityAndSourceIdentifier accountEntityAndSourceIdentifier : accountEntities) {
			entities.add(accountEntityAndSourceIdentifier.getEntity());
			sourceIdentifierEntities
				.addAll(accountEntityAndSourceIdentifier.getSourceIdentifierEntities());

		}
		accountDao.deleteAll(entities);
		accountSourceIdentifierDao.deleteAll(sourceIdentifierEntities);

		//TODO delete transfer destinations
		//TODO delete account destination types
	}

	@Override
	public synchronized void clear() {
		accountDao.clear();
		accountSourceIdentifierDao.clear();
	}
}
