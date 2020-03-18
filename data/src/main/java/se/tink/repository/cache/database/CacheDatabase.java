package se.tink.repository.cache.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import se.tink.repository.cache.models.AccountEntity;
import se.tink.repository.cache.models.AccountSourceIdentifierDao;
import se.tink.repository.cache.models.AccountSourceIdentifierEntity;
import se.tink.repository.cache.models.CounterpartEntity;
import se.tink.repository.cache.models.FlagEntity;
import se.tink.repository.cache.models.I18ConfigurationEntity;
import se.tink.repository.cache.models.IdentifiablePeriodEntity;
import se.tink.repository.cache.models.StatisticEntity;
import se.tink.repository.cache.models.TagEntity;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.budgets.BudgetEntity;

@Database(
        entities = {
            IdentifiablePeriodEntity.class,
            TransactionEntity.class,
            TagEntity.class,
            FlagEntity.class,
            I18ConfigurationEntity.class,
            StatisticEntity.class,
            AccountEntity.class,
            AccountSourceIdentifierEntity.class,
            CounterpartEntity.class,
            BudgetEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    public abstract PeriodDao periodDao();

    public abstract TransactionDao transactionDao();

    public abstract TagDao tagDao();

    public abstract UserConfigurationDao userConfigurationDao();

    public abstract StatisticsDao statisticsDao();

    public abstract AccountDao accountDao();

    public abstract AccountSourceIdentifierDao accountSourceIdentifierDao();

    public abstract BudgetsDao budgetsDao();
}
