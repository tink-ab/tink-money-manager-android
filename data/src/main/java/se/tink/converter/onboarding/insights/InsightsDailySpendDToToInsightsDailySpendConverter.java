package se.tink.converter.onboarding.insights;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.onboarding.insights.AmountByWeekday;
import se.tink.core.models.onboarding.insights.InsightsDailySpend;
import se.tink.modelConverter.AbstractConverter;

public class InsightsDailySpendDToToInsightsDailySpendConverter extends
	AbstractConverter<se.tink.grpc.v1.models.InsightsDailySpend, InsightsDailySpend> {

	private ModelConverter converter;

	public InsightsDailySpendDToToInsightsDailySpendConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public InsightsDailySpend convert(se.tink.grpc.v1.models.InsightsDailySpend source) {
		InsightsDailySpend destination = new InsightsDailySpend();
		destination.setTitle(source.getTitle());
		destination.setBody(source.getBody());
		List<AmountByWeekday> amountByWeekdayList = converter
			.map(source.getAmountByWeekdayList(), AmountByWeekday.class);
		destination.setAmountByWeekdayList(amountByWeekdayList);
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.InsightsDailySpend> getSourceClass() {
		return se.tink.grpc.v1.models.InsightsDailySpend.class;
	}

	@Override
	public Class<InsightsDailySpend> getDestinationClass() {
		return InsightsDailySpend.class;
	}
}
