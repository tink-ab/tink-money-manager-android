package com.tink.pfmsdk.mapper;

import se.tink.core.models.misc.Amount;
import se.tink.modelConverter.AbstractConverter;

public class AmountToDoubleConverter extends AbstractConverter<Amount, Double> {

	@Override
	public Double convert(Amount source) {
		if (source.getValue() != null) {
			return source.getValue().doubleValue();
		}
		return 0d;
	}

	@Override
	public Class<Amount> getSourceClass() {
		return Amount.class;
	}

	@Override
	public Class<Double> getDestinationClass() {
		return Double.class;
	}

}
