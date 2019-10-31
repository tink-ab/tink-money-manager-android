package se.tink.enums;

/**
 * @deprecated - will be removed when barcharts are reimplemented
 */
@Deprecated
public enum ChartType {

	INCOME("INCOME", 2, "income"),
	EXPENSES("EXPENSES", 0, "expenses"),
	LEFT_TO_SPEND("LEFT_TO_SPEND", 1, "left-to-spend");

	private String name;
	private int code;
	private String stringCode;

	ChartType(String name, int code, String stringCode) {
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
