package com.tink.pfmui.collections;

import com.google.common.collect.Lists;
import java.util.List;
import se.tink.core.models.Category;
import se.tink.core.models.Category.Type;
import se.tink.core.models.category.CategoryTree;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.CategoryService;

@Deprecated
public class Categories implements ObjectChangeObserver<CategoryTree>, Clearable {

	private static Categories instance;

	private CategoryTree tree = new CategoryTree();

	public static Categories getSharedInstance() {
		if (instance == null) {
			instance = new Categories();
		}
		return instance;
	}

	private Categories() {
		DataWipeManager.sharedInstance().register(this);
	}

	public Category getParentCategoryFromCategoryCode(String code) {
		Category childCategory = getCategory(code);
		Category parentNode = childCategory.getParent();
		if (parentNode != null) {
			return getCategory(parentNode.getCode());
		}
		return childCategory;
	}

	public Category getParentIncomeCategory() {
		if (tree == null) {
			return null;
		}

		return tree.getIncome();
	}

	private Category createAllCategory(Category parent, String allCategoryTitle, Type type) {

		Category newParent = new Category();
		newParent.setName(parent.getName());
		newParent.setCode(parent.getCode());
		newParent.setType(parent.getType());
		newParent.setSortOrder(0);
		newParent.setParent(parent.getParent());

		List<Category> categories = Lists.newArrayList(parent.getChildren());

		Category allCategories = createCategory(allCategoryTitle, newParent.getCode(), null);
		allCategories.setType(type);
		categories.add(0, allCategories);

		newParent.setChildren(categories);

		return newParent;
	}

	private Category createCategory(String name, String code, Category parent) {
		Category category = new Category();
		category.setParent(parent);
		category.setName(name);
		category.setCode(code);
		return category;
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

	public Category getCategoryTreeRoot(Type categoryType) {
		switch (categoryType) {
			case TYPE_EXPENSES:
				return tree.getExpenses();
			case TYPE_INCOME:
				return tree.getIncome();
			case TYPE_TRANSFER:
				return tree.getTransfers();
			case TYPE_UNKKNOWN:
			default:
				return null;
		}
	}

	private Category findRecursive(Category category, String categoryCode) {
		if (category.getCode().equals(categoryCode)) {
			return category;
		}
		if (category.getChildren() == null || category.getChildren().isEmpty()) {
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
		tree = new CategoryTree();
	}
}
