package se.tink.converter.misc


import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.AmountEntity

class AmountEntityToAmountConverter : AbstractConverter<AmountEntity, Amount>() {
    override fun convert(source: AmountEntity): Amount = source.toCoreModel()
    override fun getSourceClass(): Class<AmountEntity> = AmountEntity::class.java
    override fun getDestinationClass(): Class<Amount> = Amount::class.java
}

fun AmountEntity.toCoreModel() =
    Amount().also {
        it.value = ExactNumber(unscaledValue, scale)
        it.currencyCode = currencyCode
    }