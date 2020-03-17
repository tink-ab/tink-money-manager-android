package se.tink.converter.misc


import com.tink.model.misc.Amount
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.AmountEntity

class AmountToAmountEntityConverter : AbstractConverter<Amount, AmountEntity>() {
    override fun convert(source: Amount): AmountEntity = source.toEntity()
    override fun getSourceClass(): Class<Amount> = Amount::class.java
    override fun getDestinationClass(): Class<AmountEntity> = AmountEntity::class.java
}

fun Amount.toEntity() =
    AmountEntity().also {
        it.unscaledValue = this.value.unscaledValue
        it.scale = this.value.scale
        it.currencyCode = this.currencyCode
    }