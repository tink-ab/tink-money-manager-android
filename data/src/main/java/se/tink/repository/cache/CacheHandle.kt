package se.tink.repository.cache

import se.tink.repository.DefaultMutationHandler
import se.tink.repository.MutationHandler
import se.tink.repository.cache.database.CacheDatabase
import java.util.concurrent.Executors

class CacheHandle(
    private val cacheDatabase: CacheDatabase,
    private val inMemoryCaches: Collection<WritableCacheRepository<*>>
) {
    private val executor = Executors.newSingleThreadExecutor()

    fun clearCache() {
        executor.execute {
            cacheDatabase.clearAllTables()
            for (cache in inMemoryCaches) {
                cache.clear(DefaultMutationHandler())
            }
        }
    }
}
