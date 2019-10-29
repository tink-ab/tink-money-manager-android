package se.tink.core.models.onboarding.insights;

import java.util.List;

public class InsightsLeftToSpend extends InsightsItem {

	private List<LeftToSpendByPeriod> leftToSpendByPeriodList;

	public List<LeftToSpendByPeriod> getLeftToSpendByPeriodList() {
		return leftToSpendByPeriodList;
	}

	public void setLeftToSpendByPeriodList(List<LeftToSpendByPeriod> leftToSpendByPeriodList) {
		this.leftToSpendByPeriodList = leftToSpendByPeriodList;
	}
}
