package se.tink.utils;

import android.util.SparseArray;
import com.google.common.base.Strings;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CurrencyUtils {

	private Locale locale;

	private static final int BILLION = 1000000000;
	private static final int MILLION = 1000000;
	private static final int THOUSAND = 1000;
	private static final int ZERO = 0;


	private static final Map<String, SparseArray<String>> UNITS = new HashMap<String, SparseArray<String>>();

	static {
		SparseArray<String> enUsMap = new SparseArray<String>();
		enUsMap.put(BILLION, "bn");
		enUsMap.put(MILLION, "mm");
		enUsMap.put(THOUSAND, "k");
		enUsMap.put(ZERO, "");

		SparseArray<String> svSeMap = new SparseArray<>();
		svSeMap.put(BILLION, "md");
		svSeMap.put(MILLION, "mn");
		svSeMap.put(THOUSAND, "t");
		svSeMap.put(ZERO, "");

		SparseArray<String> frFRMap = new SparseArray<>();
		frFRMap.put(BILLION, "md");
		frFRMap.put(MILLION, "mn");
		frFRMap.put(THOUSAND, "m");
		frFRMap.put(ZERO, "");

		SparseArray<String> defaultMap = new SparseArray<>();
		defaultMap.put(BILLION, "G");
		defaultMap.put(MILLION, "M");
		defaultMap.put(THOUSAND, "k");
		defaultMap.put(ZERO, "");

		SparseArray<String> nlNlMap = new SparseArray<>();
		nlNlMap.put(MILLION, "mln");
		nlNlMap.put(THOUSAND, "dzd");
		nlNlMap.put(ZERO, "");

		UNITS.put("en", enUsMap);
		UNITS.put("sv", svSeMap);
		UNITS.put("fr", frFRMap);
		UNITS.put("nl", nlNlMap);
		UNITS.put("", defaultMap);
	}

	private static CurrencyUtils instance;

	public static CurrencyUtils getInstance(Locale locale) {
		if (instance == null) {
			instance = new CurrencyUtils();
		}
		instance.setupLocale(locale);

		return instance;
	}

	private void setupLocale(Locale locale) {
		this.locale = locale;
	}

	public String formatCurrencyWithAmountSignAndWithoutSymbol(double amount, String currencyCode) {
		return formatCurrencyRound(amount, currencyCode, true, false);
	}

	public String formatCurrencyWithAmountSignAndWithSymbol(double amount, String currencyCode) {
		return formatCurrencyRound(amount, currencyCode, true, true);
	}

	public String formatCurrencyWithoutAmountSignAndWithoutSymbol(double amount,
		String currencyCode) {
		return formatCurrencyRound(amount, currencyCode, false, false);
	}

	public String formatCurrencyWithoutAmountSignAndWithSymbol(double amount, String currencyCode) {
		return formatCurrencyRound(amount, currencyCode, false, true);
	}

	private String formatCurrencyRound(double amount, String currencyCode, boolean withAmountSign,
		boolean useCurrencySymbol) {
		NumberFormat format = getDecimalFormat(currencyCode, 0);
		String formatted = format.format(Math.round(Math.abs(amount)));

		if (!useCurrencySymbol) {
			String symbol = ((DecimalFormat) format).getDecimalFormatSymbols().getCurrencySymbol();
			if (formatted.contains(symbol)) {
				formatted = formatted.replace(symbol, "");
			}
		}

		if (withAmountSign) {
			if (amount < 0) {
				formatted = "-" + formatted;
			}
		}

		return formatted;
	}

	public String formatShort(double value, String currencyCode) {
		Integer unit = null;
		String unitSign = "";
		String language = locale.getLanguage();

		SparseArray<String> localeUnits = UNITS.get(language);
		if (localeUnits == null) {
			localeUnits = UNITS.get("");
		}

		// Iterating in descending order since SparseArray does key ordering in ascending order
		for (int i = localeUnits.size() - 1; i >= 0; i--) {
			if (Double.compare(Math.abs(value), localeUnits.keyAt(i)) >= 0) {
				unit = localeUnits.keyAt(i);
				unitSign = localeUnits.get(unit);
				break;
			}
		}

		Currency currency = Currency.getInstance(currencyCode);

		String symbol = currency.getSymbol();
		if (symbol.equals("SEK")) {
			symbol = "kr";
		}

		if (Strings.isNullOrEmpty(unitSign)) {
			return String.format(locale, "%d %s", (int) value, symbol);
		}

		double valueInUnit = value / unit;

//		format.setMaximumIntegerDigits(3);

		boolean currencyLeading =
			currency.getSymbol().equals("$") || currency.getSymbol().equals("€") || currency
				.getSymbol().equals("£");

		int decimals = 0;
		// Check if value in unit is an integer.
		if (valueInUnit % 1 != 0) {
			decimals = 1;
		}

		if (currencyLeading) {
			return String.format(locale, "%s%s %s", symbol,
				formatAmountNoCurrency(valueInUnit, 0), unitSign);
		} else {

			return String
				.format(locale, "%s %s%s", formatAmountNoCurrency(valueInUnit, 0), unitSign,
					symbol);
		}
	}

	private String formatAmountNoCurrency(double amount, int decimals) {
		return getDecimalFormatNoCurrency(decimals).format(amount);
	}

	private NumberFormat getDecimalFormatNoCurrency(int decimals) {
		NumberFormat format = DecimalFormat.getInstance(locale);
		format.setMinimumFractionDigits(decimals);

		return format;
	}


	private String formatAmount(double amount, int decimals, String currencyCode) {
		return getDecimalFormat(currencyCode, decimals).format(amount);
	}

	private NumberFormat getDecimalFormat(String currencyCode, int decimals) {
		NumberFormat format;// = DecimalFormat.getCurrencyInstance(locale);
		if (currencyCode.equals("SEK")) {
			format = DecimalFormat.getCurrencyInstance(Locale.forLanguageTag("sv"));
		} else {
			format = DecimalFormat.getCurrencyInstance();
		}
		format.setCurrency(Currency.getInstance(currencyCode));
		format.setMinimumFractionDigits(decimals);

		return format;
	}


}
