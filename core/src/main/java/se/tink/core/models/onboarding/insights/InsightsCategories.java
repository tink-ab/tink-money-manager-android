package se.tink.core.models.onboarding.insights;

import java.util.List;

public class InsightsCategories extends InsightsItem {

	private List<AmountByCategory> amountByCategoryList;

	public List<AmountByCategory> getAmountByCategoryList() {
		return amountByCategoryList;
	}

	public void setAmountByCategoryList(List<AmountByCategory> amountByCategoryList) {
		this.amountByCategoryList = amountByCategoryList;
	}

}
