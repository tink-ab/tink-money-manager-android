package se.tink.repository.cache.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import se.tink.repository.cache.models.FlagEntity;
import se.tink.repository.cache.models.I18ConfigurationEntity;
import se.tink.repository.cache.models.IdentifiablePeriodEntity;
import se.tink.repository.cache.models.StatisticEntity;
import se.tink.repository.cache.models.budgets.BudgetEntity;

@Database(
        entities = {
            IdentifiablePeriodEntity.class,
            FlagEntity.class,
            I18ConfigurationEntity.class,
            StatisticEntity.class,
            BudgetEntity.class
        },
        version = 1,
        exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {

    public abstract StatisticsDao statisticsDao();

    public abstract BudgetsDao budgetsDao();
}
