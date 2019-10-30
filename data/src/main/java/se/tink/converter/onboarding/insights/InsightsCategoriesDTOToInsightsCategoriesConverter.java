package se.tink.converter.onboarding.insights;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.AmountByCategory;
import se.tink.core.models.onboarding.insights.InsightsCategories;
import se.tink.modelConverter.AbstractConverter;


public class InsightsCategoriesDTOToInsightsCategoriesConverter extends
	AbstractConverter<se.tink.grpc.v1.models.InsightsCategories, InsightsCategories> {

	private ModelConverter converter;

	public InsightsCategoriesDTOToInsightsCategoriesConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public InsightsCategories convert(se.tink.grpc.v1.models.InsightsCategories source) {
		InsightsCategories destination = new InsightsCategories();
		destination.setTitle(source.getTitle());
		destination.setBody(source.getBody());
		List<AmountByCategory> amountByCategoryList = converter
			.map(source.getAmountByCategoryCodeList(), AmountByCategory.class);
		destination.setAmountByCategoryList(amountByCategoryList);
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.InsightsCategories> getSourceClass() {
		return se.tink.grpc.v1.models.InsightsCategories.class;
	}

	@Override
	public Class<InsightsCategories> getDestinationClass() {
		return InsightsCategories.class;
	}
}
