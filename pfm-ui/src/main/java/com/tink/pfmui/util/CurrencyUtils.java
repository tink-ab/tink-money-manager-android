package com.tink.pfmui.util;

import androidx.annotation.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.tink.pfmui.collections.Currencies;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.user.UserConfiguration;
import se.tink.core.models.user.UserConfigurationI18NConfiguration;

public class CurrencyUtils {

	private static class StringExactNumberPair {

		public final ExactNumber e;
		public final String s;


		public StringExactNumberPair(String s, ExactNumber e) {
			this.s = s;
			this.e = e;
		}
	}

	public class CurrencyFormat {

		// Value dependent format (exact if very small, rounded otherwise).
		public static final int NONE = 0x0;
		public static final int SYMBOL = 0x1; // Include currency symbol.
		public static final int SHORT = 0x2;
		public static final int EXACT = 0x4;
		public static final int ROUND = 0x8;
		public static final int DYNAMIC = 0x10;
		public static final int VERY_SHORT = 0x16;
		public static final int AMOUNT_SIGN = 0x20; // Include amount sign
	}

	// When using dynamic currency format:
	// if amount >= DYNAMIC_ROUNDING_THRESHOLD then rounded format is applied
	// else exact format is applied.
	private static final DynamicRoundingThresholds DYNAMIC_ROUNDING_THRESHOLDS = DynamicRoundingThresholds.INSTANCE;

	private static final char NON_BREAKING_WHITESPACE = (char) 160;

	private static final int EXACT_DECIMALS = 2;


	private static final ExactNumber ZERO = ExactNumber.ZERO;
	private static final ExactNumber THOUSAND = new ExactNumber(1000, 0);
	private static final ExactNumber MILLION = new ExactNumber(1000000, 0);
	private static final ExactNumber BILLION = new ExactNumber(1000000000, 0);

	private static final Map<String, ImmutableList<StringExactNumberPair>> UNITS = new HashMap<>();

	static {

		ImmutableList<StringExactNumberPair> enUsMap = ImmutableList
			.of(new StringExactNumberPair("", ZERO),
				new StringExactNumberPair("k", THOUSAND),
				new StringExactNumberPair("mm", MILLION),
				new StringExactNumberPair("bn", BILLION));

		ImmutableList<StringExactNumberPair> svSeMap = ImmutableList
			.of(new StringExactNumberPair("", ZERO),
				new StringExactNumberPair("t", THOUSAND),
				new StringExactNumberPair("mn", MILLION),
				new StringExactNumberPair("md", BILLION));

		ImmutableList<StringExactNumberPair> frFRMap = ImmutableList
			.of(new StringExactNumberPair("", ZERO),
				new StringExactNumberPair("m", THOUSAND),
				new StringExactNumberPair("mn", MILLION),
				new StringExactNumberPair("md", BILLION));

		ImmutableList<StringExactNumberPair> defaultMap = ImmutableList
			.of(new StringExactNumberPair("", ZERO),
				new StringExactNumberPair("k", THOUSAND),
				new StringExactNumberPair("M", MILLION),
				new StringExactNumberPair("G", BILLION));

		ImmutableList<StringExactNumberPair> nlNlMap = ImmutableList
			.of(new StringExactNumberPair("", ZERO),
				new StringExactNumberPair("dzd", THOUSAND),
				new StringExactNumberPair("mln", MILLION),
				new StringExactNumberPair("mjd", BILLION));

		UNITS.put("en", enUsMap);
		UNITS.put("sv", svSeMap);
		UNITS.put("fr", frFRMap);
		UNITS.put("", defaultMap);
		UNITS.put("nl", nlNlMap);
	}

	public static String getAmountLabel(Amount amount) {
		return formatCurrency(amount);
	}

	public static String formatCurrency(Amount amount) {
		return formatCurrencyWithAmountSign(amount);
	}

	public static String formatCurrencyRound(Amount amount) {
		return formatCurrency(amount,
			CurrencyFormat.ROUND | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN);
	}

	public static String formatCurrencyRoundWithoutSignAndSymbol(Amount amount) {
		double amountWithoutSign = amount.getValue().absValue().doubleValue();
		return formatAmountRoundWithoutCurrencySymbol(amountWithoutSign);
	}

	public static String formatCurrencyExactWithoutSignAndSymbol(Amount amount) {
		return formatAmount(amount.getValue().absValue().doubleValue(), EXACT_DECIMALS, false);
	}

	public static String formatCurrencyRoundWithoutSign(Amount amount) {
		return formatCurrency(amount, CurrencyFormat.ROUND | CurrencyFormat.SYMBOL);
	}

	public static String formatCurrencyExactWithoutSign(Amount amount) {
		return formatCurrency(amount, CurrencyFormat.EXACT | CurrencyFormat.SYMBOL);
	}

	public static String formatCurrencyRoundWithoutSymbol(Amount amount) {
		return formatAmountRoundWithoutCurrencySymbol(amount.getValue().doubleValue());
	}

	public static String formatCurrencyExactWithoutSymbol(Amount amount) {
		return formatCurrency(amount,
			CurrencyFormat.EXACT | CurrencyFormat.AMOUNT_SIGN);
	}

	public static String formatCurrencyExact(Amount amount) {
		return formatCurrency(amount,
			CurrencyFormat.EXACT | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN);
	}

	public static String formatCurrencyRoundWithoutAmountSign(Amount amount) {
		return formatCurrency(amount, CurrencyFormat.ROUND | CurrencyFormat.SYMBOL);
	}

	public static String formatCurrencyWithAmountSign(Amount amount) {
		return formatCurrency(amount,
			CurrencyFormat.DYNAMIC | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN);
	}

	public static String formatCurrencyWithAmountSignExact(Amount amount) {
		return formatCurrency(amount,
			CurrencyFormat.DYNAMIC | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN
				| CurrencyFormat.EXACT);
	}

	public static String formatCurrencyWithoutAmountSign(Amount amount) {
		return formatCurrency(amount, CurrencyFormat.DYNAMIC | CurrencyFormat.SYMBOL);
	}

	public static String formatCurrencyWithExplicitPositive(Amount amount) {
		return formatCurrency(
			amount,
			CurrencyFormat.DYNAMIC | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN,
			true);
	}

	public static String formatCurrencyExactWithExplicitPositive(Amount amount) {
		return formatCurrency(
			amount,
			CurrencyFormat.EXACT | CurrencyFormat.SYMBOL | CurrencyFormat.AMOUNT_SIGN,
			true);
	}

	@VisibleForTesting
	static String formatCurrency(
		Amount amount,
		int currencyFormat,
		boolean explicitPositive) {
		ExactNumber absValue = amount.getValue().absValue();

		String currencyCode = amount.getCurrencyCode();
		if (currencyCode == null) {
			currencyCode = Currencies.getSharedInstance().getDefaultCurrencyCode();
		}

		String formatted;

		if ((currencyFormat & CurrencyFormat.ROUND) == CurrencyFormat.ROUND) {
			formatted = formatAmountRound(absValue, currencyCode);
		} else if ((currencyFormat & CurrencyFormat.EXACT) == CurrencyFormat.EXACT) {
			formatted = formatAmountExact(absValue, currencyCode);
		} else if ((currencyFormat & CurrencyFormat.SHORT) == CurrencyFormat.SHORT) {
			formatted = formatShort(absValue, currencyCode);
		} else { // CurrencyFormat.DYNAMIC
			if (absValue.isSmallerThan(DYNAMIC_ROUNDING_THRESHOLDS.get(currencyCode)) && absValue
				.isBiggerThan(ExactNumber.ZERO)) {
				formatted = formatAmount(absValue, EXACT_DECIMALS, currencyCode);
			} else {
				formatted = formatAmountRound(absValue, currencyCode);
			}
		}

		if ((currencyFormat & CurrencyFormat.AMOUNT_SIGN) == CurrencyFormat.AMOUNT_SIGN) {
			if (amount.getValue().isSmallerThan(ExactNumber.ZERO)) {
				formatted = "-" + formatted;
			} else if (explicitPositive) {
				formatted = "+" + formatted;
			}
		}

		return formatted;
	}

	public static String formatCurrency(Amount amount, int currencyFormat) {
		return formatCurrency(amount, currencyFormat, false);
	}

	public static String formatAmountRound(ExactNumber amount, String currencyCode) {
		return formatAmount(amount, 0, currencyCode);
	}

	public static String formatAmountRoundWithoutCurrencySymbol(double amount) {
		return formatAmount(amount, 0, false);
	}

	public static String formatAmountRoundWithCurrencySymbol(double amount) {
		return formatAmount(amount, 0, true);
	}

	public static String formatAmountExactWithoutCurrencySymbol(double amount) {
		return formatAmount(amount, EXACT_DECIMALS, false);
	}

	public static String formatAmountExactWithCurrencySymbol(double amount) {
		return formatAmount(amount, EXACT_DECIMALS, true);
	}

	private static String formatAmount(double amount, int decimals, boolean useCurrencySymbol) {
		NumberFormat format = getDecimalFormat(null, decimals);
		String formatted = format.format(amount);

		if (!useCurrencySymbol) {
			String symbol = ((DecimalFormat) format).getDecimalFormatSymbols().getCurrencySymbol();
			if (formatted.contains(symbol)) {
				//Replace the currency symbol and the surrounding space, if it's before or after the symbol, or no space.
				formatted = formatted.replaceAll(symbol + "\\s", "");
				formatted = formatted.replaceAll("\\s" + symbol, "");
				formatted = formatted.replaceAll(symbol, "");

			}
		}
		return formatted;
	}

	public static char getMinusSign() {
		NumberFormat format = getDecimalFormat(null, 0);
		return ((DecimalFormat) format).getDecimalFormatSymbols().getMinusSign();
	}

	private static String formatAmount(ExactNumber amount, int decimals, String currencyCode) {
		return getDecimalFormat(currencyCode, decimals)
			.format(amount.round(decimals).asBigDecimal());
	}

	private static NumberFormat getDecimalFormat(String currencyCode, int decimals) {

		Locale locale = Locale.getDefault();

		UserConfiguration userConfiguration = Currencies.getSharedInstance().getUserConfiguration();

		if (userConfiguration != null) {
			UserConfigurationI18NConfiguration config = userConfiguration.getI18nConfiguration();

			if (config != null) {
				String localeCode = config.getLocaleCode();
				String[] splits = localeCode.split("_");

				locale = new Locale(splits[0], config.getMarketCode());
			}
		}

		if (currencyCode == null) {
			currencyCode = Currencies.getSharedInstance().getDefaultCurrencyCode();
		}

		NumberFormat format = DecimalFormat.getCurrencyInstance(locale);

		if (!currencyCode.isEmpty()) {
			format.setCurrency(Currency.getInstance(currencyCode));
		}
		format.setMinimumFractionDigits(decimals);
		format.setMaximumFractionDigits(decimals);

		return format;
	}

	public static String formatAmountExact(ExactNumber amount) {
		return formatAmount(amount, EXACT_DECIMALS, null);
	}

	private static String formatAmountExact(ExactNumber amount, String currencyCode) {
		return formatAmount(amount, EXACT_DECIMALS, currencyCode);
	}

	public static String formatShort(ExactNumber value, String currencyCode) {
		StringExactNumberPair unit = null;

		Locale locale = Locale.getDefault();

		UserConfiguration userConfiguration = Currencies.getSharedInstance()
			.getUserConfiguration();

		if (userConfiguration != null) {
			UserConfigurationI18NConfiguration i18nConfiguration = userConfiguration
				.getI18nConfiguration();
			if (i18nConfiguration != null) {
				locale = new Locale(i18nConfiguration.getLocaleCode());
			}
		}

		String language = locale.getLanguage();

		ImmutableList<StringExactNumberPair> localeUnits = UNITS.get(language);

		for (StringExactNumberPair unitCandidate : localeUnits) {
			if (value.compareTo(unitCandidate.e) >= 0) {
				unit = unitCandidate;
			} else {
				break;
			}
		}

		if (unit == null) {
			return String.format(locale, "%d", value.longValue());
		}

		ExactNumber valueInUnit = value.divide(unit.e);

		String amount = formatAmountRoundWithoutCurrencySymbol(valueInUnit.doubleValue());
		amount = amount.replaceAll("\\s", "");

		NumberFormat decimalFormat;
		// Check if value in unit is an integer.
		if (valueInUnit.isInteger()) {
			decimalFormat = getDecimalFormat(currencyCode, 0);
		} else {
			decimalFormat = getDecimalFormat(currencyCode, EXACT_DECIMALS);
		}

		String symbol = ((DecimalFormat) decimalFormat).getDecimalFormatSymbols()
			.getCurrencySymbol();
		return String.format("%s%s %s", amount, unit.s, symbol);
	}

	public static String formatGetAmountSign(Amount amount) {
		if (amount.getValue().isSmallerThan(ExactNumber.ZERO)) {
			return "-";
		} else if (amount.getValue().isBiggerThan(ExactNumber.ZERO)) {
			return "+";
		}
		return "";
	}

	public static boolean isAmountLessThanZero(Amount amount) {
		if (amount.getValue().isSmallerThan(ExactNumber.ZERO)) {
			return true;
		}
		return false;
	}

}
