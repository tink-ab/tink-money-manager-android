package com.tink.pfmsdk.mapper;

import com.tink.pfmsdk.charts.models.PeriodBalance;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.modelConverter.AbstractConverter;

class StatisticToPeriodBalanceConverter extends AbstractConverter<Statistic, PeriodBalance> {

	private ModelConverterImplementation modelConverter;

	public StatisticToPeriodBalanceConverter(ModelConverterImplementation modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public PeriodBalance convert(Statistic source) {
		PeriodBalance pb = new PeriodBalance();
		if (source.getAmount().getValue() != null) {
			pb.setAmount(source.getAmount().getValue().doubleValue());
		}
		if (source.getPeriod() != null) {
			pb.setPeriod(modelConverter.map(source.getPeriod(), Period.class));
		}
		return pb;
	}

	@Override
	public Class<Statistic> getSourceClass() {
		return Statistic.class;
	}

	@Override
	public Class<PeriodBalance> getDestinationClass() {
		return PeriodBalance.class;
	}
}
