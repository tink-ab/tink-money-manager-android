package se.tink.repository.cache

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import se.tink.converter.ModelConverter
import se.tink.core.models.Category
import se.tink.core.models.account.Account
import se.tink.core.models.budgets.BudgetSummary
import se.tink.core.models.credential.CachedCredential
import se.tink.core.models.credential.Credential
import se.tink.core.models.follow.FollowItem
import se.tink.core.models.misc.Period
import se.tink.core.models.provider.Provider
import se.tink.core.models.transaction.Transaction
import se.tink.core.models.user.UserConfiguration
import se.tink.repository.LiveDataSource
import se.tink.repository.cache.database.CacheDatabase
import java.io.File
import javax.inject.Singleton

@Module(includes = [CacheBindingModule::class])
class CacheModule(private val context: Context, private val diskCacheAllowed: Boolean) {

    @Provides
    @Singleton
    fun cacheDatabase(): CacheDatabase {
        val dbFile = File(context.cacheDir, "cache_db")

        return if (!diskCacheAllowed) {
            Room.inMemoryDatabaseBuilder(context, CacheDatabase::class.java)
                .fallbackToDestructiveMigration()
                .build()
        } else {
            Room.databaseBuilder(context, CacheDatabase::class.java, dbFile.absolutePath)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    @Provides
    fun cacheHandle(
        cacheDatabase: CacheDatabase,
        providerCache: WritableCacheRepository<Provider>,
        credentialCache: WritableCacheRepository<Credential>
    ): CacheHandle {
        return CacheHandle(
            cacheDatabase,
            setOf(providerCache, credentialCache)
        )
    }

    @Provides
    fun categoryCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<List<Category>> {
        return CategoryDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun categoryTreeCache(categoryCache: Cache<List<Category>>): CategoryTreeCache {
        return CategoryTreeCache(categoryCache)
    }

    @Provides
    fun periodCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<Map<String, Period>> {
        return PeriodDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun transaction(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<List<Transaction>> {
        return TransactionDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun userConfigurationCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<UserConfiguration> {
        return UserConfigurationDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun statisticsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): StasticCache {
        return if (diskCacheAllowed) {
            StatisticDatabaseCache(cacheDatabase, modelConverter)
        } else StatisticInMemoryCache()
    }

    @Provides
    fun accountsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): LiveDataCache<List<Account>> {
        return AccountDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun followItemsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<List<FollowItem>> {
        return FollowItemDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun budgetsCache(
        cacheDatabase: CacheDatabase
    ): LiveDataCache<List<BudgetSummary>> {
        return BudgetsDatabaseCache(cacheDatabase)
    }

    @Provides
    fun credentialsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): LiveDataCache<List<CachedCredential>> {
        return CredentialsDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    @Singleton
    fun providerCache(): WritableCacheRepository<Provider> {
        return InMemoryCache()
    }

    @Provides
    @Singleton
    fun credentialCache(): WritableCacheRepository<Credential> {
        return InMemoryCache()
    }
}

@Module
interface CacheBindingModule {

    @Binds
    fun bindAccountLiveDataSource(cache: LiveDataCache<List<Account>>): LiveDataSource<List<Account>>
}