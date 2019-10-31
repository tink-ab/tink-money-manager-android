package com.tink.pfmsdk.collections;

import java.util.List;
import java.util.Map;
import se.tink.core.models.Category;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;

public class StatisticsToMap {

	private Map<String, Statistic> statistics;
	private List<Category> categories;
	private Period period;
	private Map<String, Period> periods;
	private boolean isLeftToSpendData;
	private boolean isCurrentMonth;

	public Map<String, Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(Map<String, Statistic> statistics) {
		this.statistics = statistics;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Map<String, Period> getPeriods() {
		return periods;
	}

	public void setPeriods(Map<String, Period> periods) {
		this.periods = periods;
	}

	public boolean isLeftToSpendData() {
		return isLeftToSpendData;
	}

	public void setLeftToSpendData(boolean leftToSpendData) {
		isLeftToSpendData = leftToSpendData;
	}

	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}

	public void setCurrentMonth(boolean currentMonth) {
		isCurrentMonth = currentMonth;
	}

}
