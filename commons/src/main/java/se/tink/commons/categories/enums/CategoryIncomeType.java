package se.tink.commons.categories.enums;


public enum CategoryIncomeType {

	INCOME_SALARY("salary", "income:salary"),
	INCOME_PENSION("pension", "income:pension"),
	INCOME_REFUND("refund", "income:refund"),
	INCOME_BENEFITS("benefits", "income:benefits"),
	INCOME_FINANCIAL("financial", "income:financial"),
	INCOME_OTHER("other", "income:other");

	private final String type;
	private final String code;

	CategoryIncomeType(String type, String code) {
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
