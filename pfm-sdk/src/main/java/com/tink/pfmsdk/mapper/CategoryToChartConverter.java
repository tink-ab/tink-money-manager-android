package com.tink.pfmsdk.mapper;

import com.google.common.base.Objects;
import com.tink.pfmsdk.charts.models.CategoryChartData;
import com.tink.pfmsdk.view.TinkIcon;
import se.tink.commons.categories.enums.CategoryExpenseType;
import se.tink.commons.categories.enums.CategoryIncomeType;
import se.tink.core.models.Category;
import se.tink.modelConverter.AbstractConverter;

public class CategoryToChartConverter extends
	AbstractConverter<Category, CategoryChartData> {

	@Override
	public CategoryChartData convert(Category source) {
		String code = source.getCode();
		String name = source.getName();

		CategoryChartData category;

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
	public Class<CategoryChartData> getDestinationClass() {
		return CategoryChartData.class;
	}

	private CategoryChartData getExpensesShoppingCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_SHOPPING);
		return category;
	}

	private CategoryChartData getExpensesHomeCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_HOME);
		return category;
	}

	private CategoryChartData getExpensesMiscCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_MISC);
		return category;
	}

	private CategoryChartData getExpensesFoodCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_FOOD);
		return category;
	}

	private CategoryChartData getExpensesHouseCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_HOUSE);
		return category;
	}

	private CategoryChartData getExpensesTransportCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_TRANSPORT);
		return category;
	}

	private CategoryChartData getExpensesWellnessCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_WELLNESS);
		return category;
	}

	private CategoryChartData getExpensesEntertainmentCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryExpenseType(CategoryExpenseType.EXPENSES_ENTERTAINMENT);
		return category;
	}

	private CategoryChartData getIncomeSalaryCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_SALARY);
		return category;
	}

	private CategoryChartData getIncomePensionCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_PENSION);
		return category;
	}

	private CategoryChartData getIncomeRefundCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_REFUND);
		return category;
	}

	private CategoryChartData getIncomeBenefitsCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_BENEFITS);
		return category;
	}

	private CategoryChartData getIncomeFinancialCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_FINANCIAL);
		return category;
	}

	private CategoryChartData getIncomeOtherCategory(String code, String name) {
		CategoryChartData category = new CategoryChartData();
		category.setCode(code);
		category.setIcon(TinkIcon.fromCategoryCode(code));
		category.setName(name);
		category.setCategoryIncomeType(CategoryIncomeType.INCOME_OTHER);
		return category;
	}

	private CategoryChartData mapCategory(se.tink.core.models.Category c) {
		CategoryChartData cat = new CategoryChartData();
		cat.setCode(c.getCode());
		cat.setIcon(TinkIcon.fromCategoryCode(c.getCode()));
		cat.setName(c.getName());
		return cat;
	}

}
