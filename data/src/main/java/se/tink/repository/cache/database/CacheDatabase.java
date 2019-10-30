package se.tink.repository.cache.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import se.tink.repository.cache.models.AccountEntity;
import se.tink.repository.cache.models.AccountSourceIdentifierDao;
import se.tink.repository.cache.models.AccountSourceIdentifierEntity;
import se.tink.repository.cache.models.CategoryDao;
import se.tink.repository.cache.models.CategoryEntity;
import se.tink.repository.cache.models.CounterpartEntity;
import se.tink.repository.cache.models.CredentialsEntity;
import se.tink.repository.cache.models.FlagEntity;
import se.tink.repository.cache.models.I18ConfigurationEntity;
import se.tink.repository.cache.models.IdentifiablePeriodEntity;
import se.tink.repository.cache.models.StatisticEntity;
import se.tink.repository.cache.models.TagEntity;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.budgets.BudgetEntity;
import se.tink.repository.cache.models.follow.FollowExpenseCategoryCodeEntity;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;
import se.tink.repository.cache.models.follow.FollowItemEntity;
import se.tink.repository.cache.models.follow.FollowItemTransactionEntity;
import se.tink.repository.cache.models.follow.FollowSavingAccountIdEntity;

@Database(
        entities = {
            CategoryEntity.class,
            IdentifiablePeriodEntity.class,
            TransactionEntity.class,
            TagEntity.class,
            FlagEntity.class,
            I18ConfigurationEntity.class,
            StatisticEntity.class,
            AccountEntity.class,
            FollowItemEntity.class,
            FollowHistoricalAmountEntity.class,
            FollowItemTransactionEntity.class,
            FollowExpenseCategoryCodeEntity.class,
            FollowSavingAccountIdEntity.class,
            AccountSourceIdentifierEntity.class,
            CounterpartEntity.class,
            CredentialsEntity.class,
            BudgetEntity.class
        },
        version = 12,
        exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract PeriodDao periodDao();

    public abstract TransactionDao transactionDao();

    public abstract TagDao tagDao();

    public abstract CounterpartDao counterpartDao();

    public abstract UserConfigurationDao userConfigurationDao();

    public abstract StatisticsDao statisticsDao();

    public abstract AccountDao accountDao();

    public abstract FollowItemDao followItemDao();

    public abstract FollowHistoricalAmountDao followHistoricalAmountDao();

    public abstract FollowItemTransactionDao followItemTransactionDao();

    public abstract FollowExpenseCategoryCodeDao followExpenseCategoryCodeDao();

    public abstract FollowSavingsAccountIdDao followSavingsAccountIdDao();

    public abstract AccountSourceIdentifierDao accountSourceIdentifierDao();

    public abstract CredentialsDao credentialsDao();

    public abstract BudgetsDao budgetsDao();
}
