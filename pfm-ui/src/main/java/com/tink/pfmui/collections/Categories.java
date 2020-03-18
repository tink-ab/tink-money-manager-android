package com.tink.pfmui.collections;

import com.tink.model.category.Category;
import com.tink.model.category.CategoryTree;
import com.tink.service.category.CategoryService;
import com.tink.service.observer.ChangeObserver;
import javax.annotation.Nullable;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

@Deprecated
public class Categories implements ChangeObserver<CategoryTree>, Clearable {

	private static Categories instance;

	@Nullable
	private CategoryTree tree;

	public static Categories getSharedInstance() {
		if (instance == null) {
			instance = new Categories();
		}
		return instance;
	}

	private Categories() {
		DataWipeManager.sharedInstance().register(this);
	}

	public Category getParentIncomeCategory() {
		if (tree == null) {
			return null;
		}

		return tree.getIncome();
	}

	public Category getParentExpenseCategory() {
		if (tree == null) {
			return null;
		}

		return tree.getExpenses();
	}

	/**
	 * @deprecated Fetch a CategoryTree object instead and call findByCategoryCode on it
	 */
	@Deprecated
	public Category getCategory(String categoryCode) {
		Category category = findRecursive(tree.getExpenses(), categoryCode);
		if (category == null) {
			category = findRecursive(tree.getIncome(), categoryCode);
		}
		if (category == null) {
			category = findRecursive(tree.getTransfers(), categoryCode);
		}
		return category;
	}

	private Category findRecursive(Category category, String categoryCode) {
		if (category.getCode().equals(categoryCode)) {
			return category;
		}
		category.getChildren();
		if (category.getChildren().isEmpty()) {
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

	public void removeListener(CategoryService categoryService) {
		categoryService.unsubscribe(this);
	}

	@Override
	public void onCreate(CategoryTree item) {
		tree = item;
	}

	@Override
	public void onRead(CategoryTree item) {
		tree = item;
	}

	@Override
	public void onUpdate(CategoryTree item) {
		tree = item;
	}

	@Override
	public void onDelete(CategoryTree item) {

	}

	public void attatchListener(CategoryService categoryService) {
		categoryService.subscribe(this);
	}

	@Override
	public void clear() {
		tree = null;
	}
}
