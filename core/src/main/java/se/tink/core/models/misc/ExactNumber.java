package se.tink.core.models.misc;


import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class ExactNumber implements Parcelable, Comparable<ExactNumber>, Serializable {

	public static final ExactNumber ZERO = new ExactNumber(0, 0);
	public static final ExactNumber ONE = new ExactNumber(1, 0);
	public static final ExactNumber TWO = new ExactNumber(2, 0);
	public static final ExactNumber TEN = new ExactNumber(10, 0);

	private BigDecimal bigDecimal;

	public ExactNumber() {
		bigDecimal = BigDecimal.ZERO;
	}

	protected ExactNumber(Parcel in) {
		bigDecimal = new BigDecimal(in.readString());
	}

	public static final Creator<ExactNumber> CREATOR = new Creator<ExactNumber>() {
		@Override
		public ExactNumber createFromParcel(Parcel in) {
			return new ExactNumber(in);
		}

		@Override
		public ExactNumber[] newArray(int size) {
			return new ExactNumber[size];
		}
	};

	public int intValue() {
		return bigDecimal.intValue();
	}

	public ExactNumber(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public ExactNumber(long unscaledValue, long scale) {
		bigDecimal = new BigDecimal(BigInteger.valueOf(unscaledValue), (int) scale);
	}

	public long getUnscaledValue() {
		return bigDecimal.unscaledValue().longValue();
	}

	public long getScale() {
		return bigDecimal.scale();
	}

	public long longValue() {
		return bigDecimal.longValue();
	}

	public float floatValue() {
		return bigDecimal.floatValue();
	}

	public double doubleValue() {
		return bigDecimal.doubleValue();
	}

	public long getRoundedLongValue() {
		return bigDecimal.round(MathContext.DECIMAL32).longValue();
	}

	public long decimalValue() {
		return bigDecimal.subtract(bigDecimal.setScale(0, RoundingMode.FLOOR))
			.movePointRight(bigDecimal.scale()).longValue();
	}

	public ExactNumber absValue() {
		return new ExactNumber(bigDecimal.abs());
	}

	public boolean isSmallerThan(int other) {
		return bigDecimal.compareTo(new BigDecimal(BigInteger.valueOf(other), 0)) < 0;
	}

	public boolean isBiggerThan(int other) {
		return bigDecimal.compareTo(new BigDecimal(BigInteger.valueOf(other), 0)) > 0;
	}

	public boolean isBiggerThan(ExactNumber other) {
		return compareTo(other) > 0;
	}

	public boolean isBiggerThanOrEqual(ExactNumber other) {
		return compareTo(other) >= 0;
	}


	public boolean isSmallerThan(ExactNumber other) {
		return compareTo(other) < 0;
	}

	BigDecimal getBigDecimal() {
		return this.bigDecimal;
	}

	@Override
	public int compareTo(ExactNumber o) {
		return bigDecimal.compareTo(o.getBigDecimal());
	}

	public boolean isInteger() {
		return decimalValue() == 0;
	}

	public ExactNumber multiply(ExactNumber nr) {
		return new ExactNumber(bigDecimal.multiply(nr.bigDecimal));
	}

	public ExactNumber divide(ExactNumber nr) {
		return new ExactNumber(bigDecimal.divide(nr.getBigDecimal(), MathContext.DECIMAL64));
	}

	public BigDecimal asBigDecimal() {
		return bigDecimal;
	}

	public ExactNumber round() {
		return round(0);
	}

	public ExactNumber round(int decimals) {
		return new ExactNumber(bigDecimal.setScale(decimals, RoundingMode.HALF_UP));
	}

	private static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
		return base.multiply(pct).divide(new BigDecimal(100));
	}

	/**
	 *
   * @deprecated Wrong result when not working on positive numbers. Use subtract() instead
	 */
	@Deprecated
	public ExactNumber subtractIgnoreSigns(ExactNumber other) {
		return new ExactNumber(bigDecimal.abs().subtract(other.bigDecimal.abs()));
	}

	public ExactNumber subtract(ExactNumber other) {
		return new ExactNumber(bigDecimal.subtract(other.bigDecimal));
	}

	public int length() {
		return bigDecimal.precision();
	}

	public String percentageFormat(Locale locale) {
		NumberFormat format = NumberFormat.getPercentInstance(locale);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		return format.format(bigDecimal);
	}

	public ExactNumber add(ExactNumber value) {
		return new ExactNumber(
			this.getBigDecimal().add(value.getBigDecimal()));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExactNumber)) {
			return false;
		}

		return bigDecimal.compareTo(((ExactNumber) obj).bigDecimal) == 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(bigDecimal.toString());
	}

	public boolean isZero() {
		return asBigDecimal().signum() == 0;
	}

	public ExactNumber negate() {
		return new ExactNumber(bigDecimal.negate());
	}

	@Override
	public String toString() {
		return String.format("ExactNumber(%s, %s)", getUnscaledValue(), getScale());
	}
}
