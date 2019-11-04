package com.tink.pfmsdk.mapper;

import com.google.common.base.Objects;
import com.tink.pfmsdk.view.TinkIcon;
import se.tink.commons.categories.enums.CategoryExpenseType;
import se.tink.commons.categories.enums.CategoryIncomeType;
import se.tink.core.models.Category;
import se.tink.modelConverter.AbstractConverter;

public class CategoryToChartConverter extends
	AbstractConverter<Category, se.tink.piechart.Category> {

	@Override
	public se.tink.piechart.Category convert(Category source) {
		String code = source.getCode();
		String name = source.getName();

		se.tink.piechart.Category category;

		if (Objects.equal(code, CategoryExpenseType.EXPENSES_SHOPPING.getCode())) {
			category = getExpensesShoppingCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_HOME.getCode())) {
			category = getExpensesHomeCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_MISC.getCode())) {
			category = getExpensesMiscCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_HOUSE.getCode())) {
			category = getExpensesHouseCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_ENTERTAINMENT.getCode())) {
			category = getExpensesEntertainmentCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_FOOD.getCode())) {
			category = getExpensesFoodCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_TRANSPORT.getCode())) {
			category = getExpensesTransportCategory(code, name);

		} else if (Objects.equal(code, CategoryExpenseType.EXPENSES_WELLNESS.getCode())) {
			category = getExpensesWellnessCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_SALARY.getCode())) {
			category = getIncomeSalaryCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_FINANCIAL.getCode())) {
			category = getIncomeFinancialCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_REFUND.getCode())) {
			category = getIncomeRefundCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_BENEFITS.getCode())) {
			category = getIncomeBenefitsCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_PENSION.getCode())) {
			category = getIncomePensionCategory(code, name);

		} else if (Objects.equal(code, CategoryIncomeType.INCOME_OTHER.getCode())) {
			category = getIncomeOtherCategory(code, name);

		} else {
			category = mapCategory(source);
		}

		category.setDefaultChild(source.isDefaultChild());
		category.setOnlyChild(source.getParent() == null || source.getParent().getChildren().size() <= 1);

		return category;
	}

	@Override
	public Class<Category> getSourceClass() {
		return Category.class;
	}

	@Override
	public Class<se.tink.piechart.Category> getDestinationClass() {
		return se.tink.piechart.Category.class;
	}

	private se.tink.piechart.Category getExpensesShoppingCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_SHOPPING);
		return category;
	}

	private se.tink.piechart.Category getExpensesHomeCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_HOME);
		return category;
	}

	private se.tink.piechart.Category getExpensesMiscCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_MISC);
		return category;
	}

	private se.tink.piechart.Category getExpensesFoodCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_FOOD);
		return category;
	}

	private se.tink.piechart.Category getExpensesHouseCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_HOUSE);
		return category;
	}

	private se.tink.piechart.Category getExpensesTransportCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_TRANSPORT);
		return category;
	}

	private se.tink.piechart.Category getExpensesWellnessCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_WELLNESS);
		return category;
	}

	private se.tink.piechart.Category getExpensesEntertainmentCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_ENTERTAINMENT);
		return category;
	}

	private se.tink.piechart.Category getIncomeSalaryCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_SALARY);
		return category;
	}

	private se.tink.piechart.Category getIncomePensionCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_PENSION);
		return category;
	}

	private se.tink.piechart.Category getIncomeRefundCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_REFUND);
		return category;
	}

	private se.tink.piechart.Category getIncomeBenefitsCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_BENEFITS);
		return category;
	}

	private se.tink.piechart.Category getIncomeFinancialCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_FINANCIAL);
		return category;
	}

	private se.tink.piechart.Category getIncomeOtherCategory(String code, String name) {
		se.tink.piechart.Category category = new se.tink.piechart.Category();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_OTHER);
		return category;
	}

	private se.tink.piechart.Category mapCategory(se.tink.core.models.Category c) {
		se.tink.piechart.Category cat = new se.tink.piechart.Category();
		cat.setCode(c.getCode());
		cat.setIcon(TinkIcon.fromCategoryCode(c.getCode()));
		cat.setName(c.getName());
		return cat;
	}

}
