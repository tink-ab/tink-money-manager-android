package se.tink.repository.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.tink.converter.ModelConverter
import se.tink.core.models.credential.CachedCredential
import se.tink.repository.cache.database.CacheDatabase
import se.tink.repository.cache.models.CredentialsEntity

class CredentialsDatabaseCache(
    val cacheDatabase: CacheDatabase,
    val modelConverter: ModelConverter
) : LiveDataCache<List<CachedCredential>> {

    override fun read(): LiveData<List<CachedCredential>> {
        return Transformations.map(cacheDatabase.credentialsDao().getAll()) {
            modelConverter.map(it, CachedCredential::class.java)
        }
    }

    override fun clearAndInsert(item: List<CachedCredential>) {
        cacheDatabase.credentialsDao()
            .clearAndInsert(modelConverter.map(item, CredentialsEntity::class.java))
    }

    override fun insert(item: List<CachedCredential>) {
        cacheDatabase.credentialsDao()
            .insert(modelConverter.map(item, CredentialsEntity::class.java))
    }

    override fun update(item: List<CachedCredential>) {
        cacheDatabase.credentialsDao()
            .update(modelConverter.map(item, CredentialsEntity::class.java))
    }

    override fun delete(item: List<CachedCredential>) {
        cacheDatabase.credentialsDao()
            .delete(modelConverter.map(item, CredentialsEntity::class.java))
    }

    override fun clear() = cacheDatabase.credentialsDao().clear()
}