package com.tink.pfmui.mapper

import com.tink.model.misc.Amount
import se.tink.commons.extensions.doubleValue
import se.tink.modelConverter.AbstractConverter

internal class AmountToDoubleConverter :
    AbstractConverter<Amount, Double>() {
    override fun convert(source: Amount): Double {
        return source.value.doubleValue()
    }

    override fun getSourceClass(): Class<Amount> {
        return Amount::class.java
    }

    override fun getDestinationClass(): Class<Double> {
        return Double::class.java
    }
}