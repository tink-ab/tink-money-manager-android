package se.tink.core.models.statistic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StatisticTree {

	private Map<String, Statistic> balancesByAccountId;
	private Map<String, Statistic> balancesByAccountGroupType;
	private Map<String, Statistic> leftToSpend;
	private Map<String, Statistic> leftToSpendAverage;
	private Map<String, Statistic> expensesByCategoryCode;
	private Map<String, Statistic> incomeByCategoryCode;

	public Map<String, Statistic> getBalancesByAccountId() {
		return balancesByAccountId;
	}

	public void setBalancesByAccountId(
		Map<String, Statistic> balancesByAccountId) {
		this.balancesByAccountId = balancesByAccountId;
	}

	public Map<String, Statistic> getBalancesByAccountGroupType() {
		return balancesByAccountGroupType;
	}

	public void setBalancesByAccountGroupType(
		Map<String, Statistic> balancesByAccountGroupType) {
		this.balancesByAccountGroupType = balancesByAccountGroupType;
	}

	public Map<String, Statistic> getLeftToSpend() {
		return leftToSpend;
	}

	public void setLeftToSpend(
		Map<String, Statistic> leftToSpend) {
		this.leftToSpend = leftToSpend;
	}

	public Map<String, Statistic> getLeftToSpendAverage() {
		return leftToSpendAverage;
	}

	public void setLeftToSpendAverage(
		Map<String, Statistic> leftToSpendAverage) {
		this.leftToSpendAverage = leftToSpendAverage;
	}

	public Map<String, Statistic> getExpensesByCategoryCode() {
		return expensesByCategoryCode;
	}

	public void setExpensesByCategoryCode(
		Map<String, Statistic> expensesByCategoryCode) {
		this.expensesByCategoryCode = expensesByCategoryCode;
	}

	public Map<String, Statistic> getIncomeByCategoryCode() {
		return incomeByCategoryCode;
	}

	public void setIncomeByCategoryCode(
		Map<String, Statistic> incomeByCategoryCode) {
		this.incomeByCategoryCode = incomeByCategoryCode;
	}

	public boolean isEmpty() {
		return isEmpty(getBalancesByAccountGroupType()) &&
			isEmpty(getBalancesByAccountId()) &&
			isEmpty(getExpensesByCategoryCode()) &&
			isEmpty(getIncomeByCategoryCode()) &&
			isEmpty(getLeftToSpendAverage()) &&
			isEmpty(getLeftToSpend());
	}

	private boolean isEmpty(Map m) {
		return m == null || m.isEmpty();
	}

	public static StatisticTree addOrUpdate(StatisticTree currentStatistics, StatisticTree newStatistic) {
		StatisticTree result = new StatisticTree();
		result.balancesByAccountId = addOrUpdate(currentStatistics.balancesByAccountId, newStatistic.balancesByAccountId);
		result.balancesByAccountGroupType = addOrUpdate(currentStatistics.balancesByAccountGroupType, newStatistic.balancesByAccountGroupType);
		result.leftToSpend = addOrUpdate(currentStatistics.leftToSpend, newStatistic.leftToSpend);
		result.leftToSpendAverage = addOrUpdate(currentStatistics.leftToSpendAverage, newStatistic.leftToSpendAverage);
		result.expensesByCategoryCode = addOrUpdate(currentStatistics.expensesByCategoryCode, newStatistic.expensesByCategoryCode);
		result.incomeByCategoryCode = addOrUpdate(currentStatistics.incomeByCategoryCode, newStatistic.incomeByCategoryCode);
		return result;
	}

	public static StatisticTree delete(StatisticTree currentStatistics, StatisticTree toDelete) {
		StatisticTree result = new StatisticTree();
		result.balancesByAccountId = delete(currentStatistics.balancesByAccountId, toDelete.balancesByAccountId);
		result.balancesByAccountGroupType = delete(currentStatistics.balancesByAccountGroupType, toDelete.balancesByAccountGroupType);
		result.leftToSpend = delete(currentStatistics.leftToSpend, toDelete.leftToSpend);
		result.leftToSpendAverage = delete(currentStatistics.leftToSpendAverage, toDelete.leftToSpendAverage);
		result.expensesByCategoryCode = delete(currentStatistics.expensesByCategoryCode, toDelete.expensesByCategoryCode);
		result.incomeByCategoryCode = delete(currentStatistics.incomeByCategoryCode, toDelete.incomeByCategoryCode);
		return result;
	}

	public static Map<String, Statistic> addOrUpdate(Map<String, Statistic> currentStatistics,
		Map<String, Statistic> newStatistic) {
		Map<String, Statistic> modifiedStatistics = copyMap(currentStatistics);
		modifiedStatistics.putAll(newStatistic);
		return modifiedStatistics;
	}

	public static Map<String, Statistic> delete(Map<String, Statistic> currentPeriods,
		Map<String, Statistic> deletedPeriods) {
		Map<String, Statistic> modifiedStatistics = copyMap(currentPeriods);
		modifiedStatistics.keySet().removeAll(deletedPeriods.keySet());
		return modifiedStatistics;
	}

	private static Map<String, Statistic> copyMap(Map<String, Statistic> statisticsToCopy) {
		Map<String, Statistic> modifiedStatistics;
		if (statisticsToCopy != null) {
			modifiedStatistics = new HashMap<>(statisticsToCopy);
		} else {
			modifiedStatistics = new HashMap<>();
		}
		return modifiedStatistics;
	}

	public void updateWith(StatisticTree update) {
		if (update != null) {
			mergeMapUpdate(getBalancesByAccountGroupType(),
				update.getBalancesByAccountGroupType());
			mergeMapUpdate(getBalancesByAccountId(), update.getBalancesByAccountId());
			mergeMapUpdate(getLeftToSpend(), update.getLeftToSpend());
			mergeMapUpdate(getLeftToSpendAverage(), update.getLeftToSpendAverage());
			mergeMapUpdate(getExpensesByCategoryCode(), update.getExpensesByCategoryCode());
			mergeMapUpdate(getIncomeByCategoryCode(), update.getIncomeByCategoryCode());
		}
	}

	public static void mergeMapUpdate(Map<String, Statistic> mapToUpdate, Map<String, Statistic> update) {
		if (mapToUpdate == null || update == null) {
			return;
		}

		for (Entry<String, Statistic> updatedNode : update.entrySet()) {
			String key = updatedNode.getKey();
			Statistic updatedValue = updatedNode.getValue();
			if (mapToUpdate.containsKey(key)) {
				Statistic statisticToUpdate = mapToUpdate.get(key);
				statisticToUpdate.setPeriod(updatedValue.getPeriod());
				statisticToUpdate.setAmount(updatedValue.getAmount());
				mergeMapUpdate(statisticToUpdate.getChildren(), updatedValue.getChildren());
			} else {
				mapToUpdate.put(key, updatedValue);
			}
		}
	}
}
