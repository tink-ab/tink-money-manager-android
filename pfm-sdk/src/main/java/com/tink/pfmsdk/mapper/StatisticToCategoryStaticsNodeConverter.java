package com.tink.pfmsdk.mapper;

import com.tink.pfmsdk.charts.models.CategoryStatisticsNode;
import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.Statistic;
import se.tink.modelConverter.AbstractConverter;

public class StatisticToCategoryStaticsNodeConverter extends
	AbstractConverter<Statistic, CategoryStatisticsNode> {

	private ModelConverter modelConverter;

	public StatisticToCategoryStaticsNodeConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public CategoryStatisticsNode convert(Statistic source) {
		CategoryStatisticsNode node = new CategoryStatisticsNode();
		node.setPeriod(source.getPeriod());
		node.setTotal(modelConverter.map(source.getAmount(), Double.class));
		return node;
	}

	@Override
	public Class<Statistic> getSourceClass() {
		return Statistic.class;
	}

	@Override
	public Class<CategoryStatisticsNode> getDestinationClass() {
		return CategoryStatisticsNode.class;
	}

}
