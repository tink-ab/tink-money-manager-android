package com.tink.pfmui.di

//import se.tink.repository.cache.BudgetsDatabaseCache
import android.content.Context
import androidx.room.Room
import com.tink.annotations.PfmScope
import com.tink.model.account.Account
import dagger.Binds
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import se.tink.converter.ModelConverter
import com.tink.model.transaction.Transaction
import se.tink.repository.LiveDataSource
import se.tink.repository.cache.Cache
import se.tink.repository.cache.CacheHandle
import se.tink.repository.cache.LiveDataCache
import se.tink.repository.cache.StasticCache
import se.tink.repository.cache.StatisticInMemoryCache
import se.tink.repository.cache.TransactionDatabaseCache
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
        cacheDatabase: CacheDatabase
    ): CacheHandle {
        return CacheHandle(
            cacheDatabase,
            setOf() //TODO: Core setup
        )
    }

    @Provides
    fun transaction(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): Cache<List<Transaction>> {
        return TransactionDatabaseCache(cacheDatabase, modelConverter)
    }

    @Provides
    fun statisticsCache(
        cacheDatabase: CacheDatabase,
        modelConverter: ModelConverter
    ): StasticCache {
        return StatisticInMemoryCache()
    }

//    @Provides
//    fun budgetsCache(
//        cacheDatabase: CacheDatabase
//    ): LiveDataCache<List<BudgetSummary>> {
//        return BudgetsDatabaseCache(cacheDatabase)
//    }
}

@Module
internal interface CacheBindingModule {

    @Binds
    fun bindAccountLiveDataSource(cache: LiveDataCache<List<Account>>): LiveDataSource<List<Account>>
}