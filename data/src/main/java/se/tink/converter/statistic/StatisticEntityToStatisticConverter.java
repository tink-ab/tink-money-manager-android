package se.tink.converter.statistic;


import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.StatisticEntity;

public class StatisticEntityToStatisticConverter extends
	AbstractConverter<StatisticEntity, Statistic> {

	private ModelConverter modelConverter;

	public StatisticEntityToStatisticConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Statistic convert(StatisticEntity source) {
		Statistic destination = new Statistic();
		Amount amount = modelConverter.map(source.getAmount(), Amount.class);
		destination.setAmount(amount);
		Period period = modelConverter.map(source.getPeriod(), Period.class);
		destination.setPeriod(period);
		return destination;
	}

	@Override
	public Class<StatisticEntity> getSourceClass() {
		return StatisticEntity.class;
	}

	@Override
	public Class<Statistic> getDestinationClass() {
		return Statistic.class;
	}
}
