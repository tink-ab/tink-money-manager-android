package se.tink.piechart;

import se.tink.commons.categories.enums.CategoryExpenseType;
import se.tink.commons.categories.enums.CategoryIncomeType;

public class Category {

	private String code;
	private boolean onlyChild;
	private boolean defaultChild;
	private String name;
	private String typeName;
	private CategoryExpenseType categoryExpenseType;
	private CategoryIncomeType categoryIncomeType;
	private String icon;

	public boolean isOnlyChild() {
		return onlyChild;
	}

	public void setOnlyChild(boolean onlyChild) {
		this.onlyChild = onlyChild;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDefaultChild() {
		return defaultChild;
	}

	public void setDefaultChild(boolean defaultChild) {
		this.defaultChild = defaultChild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public CategoryExpenseType getCategoryExpenseType() {
		return categoryExpenseType;
	}

	public void setCategoryExpenseType(CategoryExpenseType categoryExpenseType) {
		this.categoryExpenseType = categoryExpenseType;
	}

	public CategoryIncomeType getCategoryIncomeType() {
		return categoryIncomeType;
	}

	public void setCategoryIncomeType(CategoryIncomeType categoryIncomeType) {
		this.categoryIncomeType = categoryIncomeType;
	}

	public String getCurrentName(String defaultChildFormat) {
		if (name != null) {
			return getNameWithDefaultChildFormat(defaultChildFormat);
		} else {
			return typeName;
		}
	}

	private String getNameWithDefaultChildFormat(String defaultChildFormat) {
		if (defaultChild && !onlyChild) {
			return String.format(
				defaultChildFormat,
				name);
		} else {
			return name;
		}
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
