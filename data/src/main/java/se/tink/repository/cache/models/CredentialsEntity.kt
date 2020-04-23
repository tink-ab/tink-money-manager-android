package se.tink.repository.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.tink.repository.cache.database.TableNames

@Entity(tableName = TableNames.CREDENTIALS)
data class CredentialsEntity(
    @PrimaryKey var id: String = "",
    var disabled: Boolean = false
)