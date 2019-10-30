package se.tink.converter.transaction

import se.tink.converter.ModelConverter
import se.tink.core.models.transaction.Counterpart
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.AmountEntity
import se.tink.repository.cache.models.CounterpartEntity

class CounterpartToCounterpartEntityConverter(val converter: ModelConverter) : AbstractConverter<Counterpart, CounterpartEntity>() {
    override fun getSourceClass(): Class<Counterpart> {
        return Counterpart::class.java
    }

    override fun getDestinationClass(): Class<CounterpartEntity> {
        return CounterpartEntity::class.java
    }

    override fun convert(source: Counterpart): CounterpartEntity {

        with(source) {
            val dstAmount = converter.map(amount, AmountEntity::class.java)
            val dstTransactionAmount = converter.map(transactionAmount, AmountEntity::class.java)
            return CounterpartEntity(
                    id = id,
                    partId = partId,
                    transactionId = counterpartTransactionId,
                    counterpartTransactionId = counterpartTransactionId,
                    description = description,
                    amount = dstAmount,
                    transactionAmount = dstTransactionAmount
            )
            //transaction id needs to be set from outside
        }
    }

}
