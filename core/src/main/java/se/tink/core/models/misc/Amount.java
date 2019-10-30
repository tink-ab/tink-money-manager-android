package se.tink.core.models.misc;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class Amount implements Parcelable, Serializable {

	private ExactNumber value;
	private String currencyCode;


	public Amount() {
		value = new ExactNumber();
	}

	public Amount(ExactNumber value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}

	protected Amount(Parcel in) {
		value = in.readParcelable(ExactNumber.class.getClassLoader());
		currencyCode = in.readString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Amount && value.equals(((Amount) obj).value);
	}

	public static final Creator<Amount> CREATOR = new Creator<Amount>() {
		@Override
		public Amount createFromParcel(Parcel in) {
			return new Amount(in);
		}

		@Override
		public Amount[] newArray(int size) {
			return new Amount[size];
		}
	};

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public ExactNumber getValue() {
		return value;
	}

	public void setValue(ExactNumber value) {
		this.value = value;
	}

	public Amount abs() {
		return new Amount(value.absValue(), currencyCode);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(value, i);
		parcel.writeString(currencyCode);
	}

	public boolean isZero() {
		return value.isZero();
	}

	public boolean isValid() {
		return getCurrencyCode() != null && getCurrencyCode().length() > 0 && getValue() != null;
	}

	public Amount half() {
		return new Amount(getValue().divide(ExactNumber.TWO), currencyCode);
	}

	@Override
	public String toString() {
		return "Amount {"
				+ "\nvalue=" + value
				+ "\ncurrencyCode=" + currencyCode
				+ "\n}";
	}
}
