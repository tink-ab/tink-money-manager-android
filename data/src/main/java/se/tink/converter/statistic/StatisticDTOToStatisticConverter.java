package se.tink.converter.statistic;

import se.tink.converter.ModelConverter;
import com.tink.model.misc.Amount;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.grpc.v1.models.StatisticNode;
import se.tink.modelConverter.AbstractConverter;

public class StatisticDTOToStatisticConverter extends AbstractConverter<StatisticNode, Statistic> {

	private final ModelConverter converter;

	public StatisticDTOToStatisticConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public Statistic convert(StatisticNode source) {
		Statistic statistic = new Statistic();
		if (source.getAmount() != null) {
			statistic.setAmount(converter.map(source.getAmount(), Amount.class));
		}
		if (source.getPeriod() != null) {
			statistic.setPeriod(converter.map(source.getPeriod(), Period.class));
		}
		if (source.getChildrenMap() != null && !source.getChildrenMap().isEmpty()) {
			statistic
				.setChildren(converter.map(source.getChildrenMap(), String.class, Statistic.class));
		}
		return statistic;
	}

	@Override
	public Class<StatisticNode> getSourceClass() {
		return StatisticNode.class;
	}

	@Override
	public Class<Statistic> getDestinationClass() {
		return Statistic.class;
	}
}
