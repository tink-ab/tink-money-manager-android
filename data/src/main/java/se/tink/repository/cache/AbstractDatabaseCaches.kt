package se.tink.repository.cache

import se.tink.privacy.Clearable
import se.tink.privacy.DataWipeManager
import se.tink.repository.cache.database.CacheDatabase

abstract class AbstractDatabaseCache<T>(
    protected val cacheDatabase: CacheDatabase
) : Cache<T>, Clearable {

    init {
        DataWipeManager.sharedInstance().register(this)
    }
}

abstract class AbstractDatabaseLiveDataCache<T>(
    protected val cacheDatabase: CacheDatabase
) : LiveDataCache<T>, Clearable {

    init {
        DataWipeManager.sharedInstance().register(this)
    }
}