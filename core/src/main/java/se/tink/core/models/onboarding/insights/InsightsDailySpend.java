package se.tink.core.models.onboarding.insights;

import java.util.List;

public class InsightsDailySpend extends InsightsItem {

	private List<AmountByWeekday> amountByWeekdayList;

	public List<AmountByWeekday> getAmountByWeekdayList() {
		return amountByWeekdayList;
	}

	public void setAmountByWeekdayList(List<AmountByWeekday> amountByWeekdayList) {
		this.amountByWeekdayList = amountByWeekdayList;
	}
}
