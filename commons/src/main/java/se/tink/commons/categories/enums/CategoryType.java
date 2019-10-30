package se.tink.commons.categories.enums;


public enum CategoryType {

	INCOME("INCOME", 2, "income"),
	EXPENSES("EXPENSES", 0, "expenses");

	private String name;
	private int code;
	private String stringCode;

	CategoryType(String name, int code, String stringCode) {
		this.name = name;
		this.code = code;
		this.stringCode = stringCode;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public String getStringCode() {
		return stringCode;
	}

}
