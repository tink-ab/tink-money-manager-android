package se.tink.repository.cache.models

import androidx.room.Embedded
import androidx.room.Relation

class AccountAndIdentifiers {
    @Embedded lateinit var account: AccountEntity
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId",
        entity = AccountSourceIdentifierEntity::class
    )
    lateinit var identifiers: List<AccountSourceIdentifierEntity>
}