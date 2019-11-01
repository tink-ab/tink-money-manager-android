package com.tink.pfmsdk.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import se.tink.core.models.follow.ExpensesFollowCriteria;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.SavingsFollowCriteria;
import se.tink.core.models.misc.ExactNumber;

public class FollowItemsUtils {

	public static List<FollowItem> filterExpenseFollowItems(final List<FollowItem> followItems) {
		Predicate<FollowItem> predicate = input -> input.getExpensesCriteria() != null;

		Iterable<FollowItem> filteredItems = Iterables.filter(followItems, predicate);
		return Lists.newArrayList(filteredItems);
	}

	public static List<FollowItem> filterSavingsFollowItems(final List<FollowItem> followItems) {
		Predicate<FollowItem> predicate = input -> input.getSavingCriteria() != null;

		Iterable<FollowItem> filteredItems = Iterables.filter(followItems, predicate);
		return Lists.newArrayList(filteredItems);
	}

	public static List<FollowItem> filterSavingsFollowItemsWithTarget(
		final List<FollowItem> followItems) {
		Predicate<FollowItem> predicate = input -> input.getSavingCriteria() != null
			&& input.getSavingCriteria().getTargetAmount() != null;

		Iterable<FollowItem> filteredItems = Iterables.filter(followItems, predicate);
		return Lists.newArrayList(filteredItems);
	}

	public static FollowItem getSavingsItemByAccountId(List<FollowItem> followItems,
		final String accountId) {
		Predicate<FollowItem> predicate = input -> {
			SavingsFollowCriteria savingCriteria = input.getSavingCriteria();
			List<String> accountIds = savingCriteria.getAccountIds();
			if (accountIds.contains(accountId)) {
				return true;
			}
			return false;
		};

		Iterable<FollowItem> filteredItems = Iterables.filter(followItems, predicate);
		List<FollowItem> items = Lists.newArrayList(filteredItems);
		if (items == null || items.isEmpty()) {
			return null;
		}
		return items.get(0);
	}

	public static List<FollowItem> filterExpensesFollowItemsWithTarget(List<FollowItem> items) {
		List<FollowItem> expensesFollowItems = new LinkedList<>();
		for(FollowItem item : items) {
			if (item.getExpensesCriteria() != null) {
				expensesFollowItems.add(item);
			}
		}

		return expensesFollowItems;
	}

	public static FollowItem getBudgetForCategoryCode(List<FollowItem> followItems,
		final String categoryCode) {
		Predicate<FollowItem> predicate = input -> {
			ExpensesFollowCriteria data = input.getExpensesCriteria();

			if (data == null) {
				return false;
			}

			List<String> categoryCodes = data.getCategoryCodes();
			if (categoryCodes.contains(categoryCode)) {
				return true;
			}
			return false;
		};

		Iterable<FollowItem> filteredItems = Iterables.filter(followItems, predicate);
		List<FollowItem> items = Lists.newArrayList(filteredItems);
		if (items == null || items.isEmpty()) {
			return null;
		}
		return items.get(0);
	}

	public static ExactNumber getProgressForBudget(FollowItem followItem) {
		ExactNumber targetAmount = followItem.getExpensesCriteria().getTargetAmount();
		ExactNumber currentAmount = followItem.getLastHistoricalAmountOrZero();
		return getProgress(targetAmount, currentAmount);
	}

	public static ExactNumber getProgressForSavingsGoal(FollowItem followItem) {
		ExactNumber targetAmount = followItem.getSavingCriteria().getTargetAmount();
		ExactNumber currentAmount = followItem.getLastHistoricalAmountOrZero();
		return getProgress(targetAmount, currentAmount);
	}

	public static ExactNumber getProgress(ExactNumber targetAmount, ExactNumber currentAmount) {
		if (currentAmount.compareTo(ExactNumber.ZERO) == 0) {
			return new ExactNumber(BigDecimal.ZERO);
		}

		if (targetAmount.compareTo(ExactNumber.ZERO) == 0) {
			return new ExactNumber(new BigDecimal(1));
		}

		return currentAmount.absValue().divide(targetAmount.absValue());
	}

	public static List<FollowItem> add(List<FollowItem> currentFollowItems,
		List<FollowItem> toAddFollowItems) {
		List<FollowItem> modifiedFollowItems = copyList(currentFollowItems);
		modifiedFollowItems.addAll(toAddFollowItems);
		return modifiedFollowItems;
	}

	public static List<FollowItem> replace(List<FollowItem> currentFollowItems,
		List<FollowItem> toAddFollowItems) {
		List<FollowItem> modifiedFollowItems = delete(currentFollowItems, toAddFollowItems);
		modifiedFollowItems.addAll(toAddFollowItems);
		return modifiedFollowItems;
	}

	public static List<FollowItem> delete(List<FollowItem> currentFollowItems,
		List<FollowItem> toDeleteFollowItems) {
		List<FollowItem> modifiedFollowItems = copyList(currentFollowItems);
		modifiedFollowItems.removeAll(toDeleteFollowItems);
		return modifiedFollowItems;
	}

	private static List<FollowItem> copyList(List<FollowItem> currentFollowItems) {
		List<FollowItem> modifiedFollowItems;
		if (currentFollowItems == null) {
			modifiedFollowItems = Lists.newArrayList();
		} else {
			modifiedFollowItems = Lists.newArrayList(currentFollowItems);
		}
		return modifiedFollowItems;
	}

}
