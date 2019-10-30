package se.tink.commons.categories.enums;


public enum CategoryExpenseType {

	EXPENSES_FOOD("fooddrinks", "expenses:food"),
	EXPENSES_WELLNESS("health", "expenses:wellness"),
	EXPENSES_HOME("home", "expenses:home"),
	EXPENSES_HOUSE("house", "expenses:house"),
	EXPENSES_ENTERTAINMENT("entertainment", "expenses:entertainment"),
	EXPENSES_MISC("other", "expenses:misc"),
	EXPENSES_TRANSPORT("transport", "expenses:transport"),
	EXPENSES_SHOPPING("shopping", "expenses:shopping");

	private final String type;
	private final String code;

	CategoryExpenseType(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

}
