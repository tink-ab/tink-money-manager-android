package se.tink.repository.cache.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import se.tink.repository.cache.database.TableNames

@Entity(tableName = TableNames.COUNTERPARTS)
@ForeignKey(parentColumns = ["id"], childColumns = ["transactionId"], entity = TransactionEntity::class, onDelete = ForeignKey.CASCADE)
data class CounterpartEntity constructor(
        @PrimaryKey var id: String = "",
        var partId: String = "",
        var transactionId: String = "",
        var counterpartTransactionId: String = "",
        var description: String = "",
        @Embedded var amount: AmountEntity = AmountEntity(),
        @Embedded(prefix = "transactionAmount") var transactionAmount: AmountEntity = AmountEntity()
)
