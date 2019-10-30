package se.tink.converter.statistic;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.statistic.Statistic;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.modelConverter.AbstractConverter;

public class StatisticTreeDTOStatisticTreeConverter extends
	AbstractConverter<se.tink.grpc.v1.models.StatisticTree, StatisticTree> {

	private ModelConverter modelConverter;

	public StatisticTreeDTOStatisticTreeConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public StatisticTree convert(se.tink.grpc.v1.models.StatisticTree source) {
		StatisticTree destination = new StatisticTree();

		if (source.getBalancesByAccountIdMap() != null) {
			destination.setBalancesByAccountId(
				modelConverter
					.map(source.getBalancesByAccountIdMap(), String.class, Statistic.class));
		}
		if (source.getBalancesByAccountGroupTypeMap() != null) {
			destination.setBalancesByAccountGroupType(
				modelConverter
					.map(source.getBalancesByAccountGroupTypeMap(), String.class, Statistic.class));
		}
		if (source.getExpensesByCategoryCodeMap() != null) {
			destination.setExpensesByCategoryCode(modelConverter
				.map(source.getExpensesByCategoryCodeMap(), String.class, Statistic.class));
		}
		if (source.getIncomeByCategoryCodeMap() != null) {
			destination.setIncomeByCategoryCode(
				modelConverter
					.map(source.getIncomeByCategoryCodeMap(), String.class, Statistic.class));
		}
		if (source.getLeftToSpendMap() != null) {
			destination.setLeftToSpend(
				modelConverter.map(source.getLeftToSpendMap(), String.class, Statistic.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.StatisticTree> getSourceClass() {
		return se.tink.grpc.v1.models.StatisticTree.class;
	}

	@Override
	public Class<StatisticTree> getDestinationClass() {
		return StatisticTree.class;
	}
}
