package se.tink.converter.transaction

import se.tink.converter.ModelConverter
import se.tink.core.models.misc.Amount
import se.tink.core.models.transaction.Counterpart
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.CounterpartEntity


class CounterpartEntityToCounterpartConverter(val converter: ModelConverter) : AbstractConverter<CounterpartEntity, Counterpart>() {
    override fun getDestinationClass(): Class<Counterpart> {
        return Counterpart::class.java
    }

    override fun getSourceClass(): Class<CounterpartEntity> {
        return CounterpartEntity::class.java
    }

    override fun convert(source: CounterpartEntity): Counterpart {
        return with(source) {
            val destAmount = converter.map(amount, Amount::class.java)
            val destTransactionAmount = converter.map(transactionAmount, Amount::class.java)
            Counterpart(
                    id = id,
                    partId = partId,
                    counterpartTransactionId = counterpartTransactionId,
                    amount = destAmount,
                    transactionAmount = destTransactionAmount,
                    description = description
            )
        }
    }

}
