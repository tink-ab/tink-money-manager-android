package se.tink.converter.statistic;

import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.Statistic;
import se.tink.grpc.v1.models.CurrencyDenominatedAmount;
import se.tink.grpc.v1.models.Period;
import se.tink.grpc.v1.models.StatisticNode;
import se.tink.modelConverter.AbstractConverter;

public class StatisticToStatisticDTOConverter extends AbstractConverter<Statistic, StatisticNode> {

	private final ModelConverter converter;

	public StatisticToStatisticDTOConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public StatisticNode convert(Statistic source) {
		StatisticNode.Builder statistic = StatisticNode.newBuilder();
		if (source.getAmount() != null) {
			statistic.setAmount(converter.map(source.getAmount(), CurrencyDenominatedAmount.class));
		}
		if (source.getPeriod() != null) {
			statistic.setPeriod(converter.map(source.getPeriod(), Period.class));
		}
		if (source.hasChildren()) {
			statistic.putAllChildren(
				converter.map(source.getChildren(), String.class, StatisticNode.class));
		}

		return statistic.build();
	}

	@Override
	public Class<Statistic> getSourceClass() {
		return Statistic.class;
	}

	@Override
	public Class<StatisticNode> getDestinationClass() {
		return StatisticNode.class;
	}
}