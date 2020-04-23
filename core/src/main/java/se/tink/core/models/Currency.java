package se.tink.core.models;

public class Currency {

	private static final int LARGE_AMOUNT_THRESHOLD_FACTOR = 20;

	private String code;
	private double factor;
	private boolean prefixed;
	private String symbol;

	public Currency() {

	}

	public Currency(String code, String symbol, boolean prefixed, double factor) {
		this.code = code;
		this.symbol = symbol;
		this.prefixed = prefixed;
		this.factor = factor;
	}

	public String getCode() {
		return code;
	}

	public double getFactor() {
		return factor;
	}

	public double getLargeAmountThreshold() {
		return factor * LARGE_AMOUNT_THRESHOLD_FACTOR;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isPrefixed() {
		return prefixed;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setPrefixed(boolean prefixed) {
		this.prefixed = prefixed;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public static Currency getInstance(String currencyCode) {
		return null;
	}
}
