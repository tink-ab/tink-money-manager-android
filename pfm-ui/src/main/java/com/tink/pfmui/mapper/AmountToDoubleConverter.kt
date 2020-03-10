package com.tink.pfmui.mapper

import se.tink.core.models.misc.Amount
import se.tink.modelConverter.AbstractConverter

internal class AmountToDoubleConverter :
    AbstractConverter<Amount, Double>() {
    override fun convert(source: Amount): Double {
        return if (source.value != null) {
            source.value.doubleValue()
        } else 0.0
    }

    override fun getSourceClass(): Class<Amount> {
        return Amount::class.java
    }

    override fun getDestinationClass(): Class<Double> {
        return Double::class.java
    }
}