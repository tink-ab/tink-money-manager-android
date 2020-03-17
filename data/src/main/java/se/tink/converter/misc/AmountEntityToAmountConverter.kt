package se.tink.converter.misc


import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.AmountEntity

class AmountEntityToAmountConverter : AbstractConverter<AmountEntity, Amount>() {
    override fun convert(source: AmountEntity): Amount = source.toCoreModel()
    override fun getSourceClass(): Class<AmountEntity> = AmountEntity::class.java
    override fun getDestinationClass(): Class<Amount> = Amount::class.java
}

fun AmountEntity.toCoreModel() =
    Amount(
        value = ExactNumber(unscaledValue, scale),
        currencyCode = currencyCode
    )
