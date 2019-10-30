package se.tink.converter.onboarding.insights;

import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.AmountByCategory;
import se.tink.core.models.misc.Amount;
import se.tink.grpc.v1.models.InsightsCategories;
import se.tink.grpc.v1.models.InsightsCategories.AmountByCategoryCode;
import se.tink.modelConverter.AbstractConverter;

public class AmountByCategoryCodeDTOToAmountByCategoryConverter extends
	AbstractConverter<AmountByCategoryCode, AmountByCategory> {

	private final ModelConverter modelConverter;

	public AmountByCategoryCodeDTOToAmountByCategoryConverter(ModelConverter converter) {
		modelConverter = converter;
	}

	@Override
	public AmountByCategory convert(InsightsCategories.AmountByCategoryCode source) {
		AmountByCategory destination = new AmountByCategory();
		destination.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		destination.setCategoryCode(source.getCategoryCode());
		return destination;
	}

	@Override
	public Class<AmountByCategoryCode> getSourceClass() {
		return AmountByCategoryCode.class;
	}

	@Override
	public Class<AmountByCategory> getDestinationClass() {
		return AmountByCategory.class;
	}
}
