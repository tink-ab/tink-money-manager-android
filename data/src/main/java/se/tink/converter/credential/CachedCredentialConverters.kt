package se.tink.converter.credential

import se.tink.core.models.credential.CachedCredential
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.CredentialsEntity

class CachedCredentialToCredentialsEntity :
    AbstractConverter<CachedCredential, CredentialsEntity>() {
    override fun convert(source: CachedCredential): CredentialsEntity {
        return CredentialsEntity(source.id, source.disabled)
    }
}

class CredentialsEntityToCachedCredential :
    AbstractConverter<CredentialsEntity, CachedCredential>() {
    override fun convert(source: CredentialsEntity): CachedCredential {
        return CachedCredential(source.id, source.disabled)
    }
}