package se.tink.converter.statistic;


import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.Statistic;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AmountEntity;
import se.tink.repository.cache.models.PeriodEntity;
import se.tink.repository.cache.models.StatisticEntity;

public class StatisticToStatisticEntityConverter extends
	AbstractConverter<Statistic, StatisticEntity> {

	private ModelConverter modelConverter;

	public StatisticToStatisticEntityConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public StatisticEntity convert(Statistic source) {
		StatisticEntity destination = new StatisticEntity();
		AmountEntity amountEntity = modelConverter.map(source.getAmount(), AmountEntity.class);
		destination.setAmount(amountEntity);
		PeriodEntity period = modelConverter.map(source.getPeriod(), PeriodEntity.class);
		destination.setPeriod(period);
		return destination;
	}

	@Override
	public Class<Statistic> getSourceClass() {
		return Statistic.class;
	}

	@Override
	public Class<StatisticEntity> getDestinationClass() {
		return StatisticEntity.class;
	}
}
