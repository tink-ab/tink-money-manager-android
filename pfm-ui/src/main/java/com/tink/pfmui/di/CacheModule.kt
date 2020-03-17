package com.tink.pfmui.di

import android.content.Context
import androidx.room.Room
import com.tink.annotations.PfmScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.converter.ModelConverter
import se.tink.core.models.Category
import se.tink.core.models.account.Account
import com.tink.model.budget.BudgetSummary
import se.tink.core.models.misc.Period
import se.tink.core.models.provider.Provider
import se.tink.core.models.transaction.Transaction
import se.tink.core.models.user.UserConfiguration
import se.tink.repository.LiveDataSource
import se.tink.repository.cache.AccountDatabaseCache
//import se.tink.repository.cache.BudgetsDatabaseCache
import se.tink.repository.cache.Cache
import se.tink.repository.cache.CacheHandle
import se.tink.repository.cache.CategoryDatabaseCache
import se.tink.repository.cache.CategoryTreeCache
import se.tink.repository.cache.InMemoryCache
import se.tink.repository.cache.LiveDataCache
import se.tink.repository.cache.PeriodDatabaseCache
import se.tink.repository.cache.StasticCache
import se.tink.repository.cache.StatisticInMemoryCache
import se.tink.repository.cache.TransactionDatabaseCache
import se.tink.repository.cache.UserConfigurationDatabaseCache
import se.tink.repository.cache.WritableCacheRepository
import se.tink.repository.cache.database.CacheDatabase

@Module(includes = [CacheBindingModule::class])
internal class CacheModule {

    @Provides
    @PfmScope
    fun cacheDatabase(@ApplicationScoped context: Context): CacheDatabase {
        return Room.inMemoryDatabaseBuilder(context, CacheDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun cacheHandle(
        cacheDatabase: CacheDatabase,
        providerCache: WritableCacheRepository<Provider>
    ): CacheHandle {
        return CacheHandle(
            cacheDatabase,
            setOf(providerCache)
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
        return UserConfigurationDatabaseCache(
            cacheDatabase,
            modelConverter
        )
    }

    @Provides
    fun statisticsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): StasticCache {
        return StatisticInMemoryCache()
    }

    @Provides
    fun accountsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): LiveDataCache<List<Account>> {
        return AccountDatabaseCache(cacheDatabase, modelConverter)
    }

//    @Provides
//    fun budgetsCache(
//        cacheDatabase: CacheDatabase
//    ): LiveDataCache<List<BudgetSummary>> {
//        return BudgetsDatabaseCache(cacheDatabase)
//    }

    @Provides
    @PfmScope
    fun providerCache(): WritableCacheRepository<Provider> {
        return InMemoryCache()
    }
}

@Module
internal interface CacheBindingModule {

    @Binds
    fun bindAccountLiveDataSource(cache: LiveDataCache<List<Account>>): LiveDataSource<List<Account>>
}