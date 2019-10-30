package se.tink.core.models.category;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.tink.core.models.Category;
import se.tink.core.models.Category.Type;

public class CategoryTree {

	private Category expenses;
	private Category income;
	private Category transfers;

	public Category getExpenses() {
		return expenses;
	}

	public void setExpenses(Category expenses) {
		this.expenses = expenses;
	}

	public Category getIncome() {
		return income;
	}

	public void setIncome(Category income) {
		this.income = income;
	}

	public Category getTransfers() {
		return transfers;
	}

	public void setTransfers(Category transfers) {
		this.transfers = transfers;
	}

	@Nullable
	public Category getCategoryByType(@NotNull Type type) {
		switch (type) {
			case TYPE_INCOME:
				return income;
			case TYPE_EXPENSES:
				return expenses;
			case TYPE_TRANSFER:
				return transfers;
		}
		return null;
	}

	@Nullable
	public Category findCategoryByCode(String categoryCode) {
		Category category = findRecursive(getExpenses(), categoryCode);
		if (category == null) {
			category = findRecursive(getIncome(), categoryCode);
		}
		if (category == null) {
			category = findRecursive(getTransfers(), categoryCode);
		}
		return category;
	}

	private Category findRecursive(Category category, String categoryCode) {
		if (category.getCode().equals(categoryCode)) {
			return category;
		}
		if (category.getChildren() == null) {
			return null;
		}
		for (Category childCategory : category.getChildren()) {
			Category returnCategory = findRecursive(childCategory, categoryCode);
			if (returnCategory != null) {
				return returnCategory;
			}
		}
		return null;
	}
}
