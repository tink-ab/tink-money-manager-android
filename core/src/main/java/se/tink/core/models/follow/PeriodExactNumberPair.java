package se.tink.core.models.follow;

import android.os.Parcel;
import android.os.Parcelable;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;

public class PeriodExactNumberPair implements Parcelable {

	private Period period;
	private ExactNumber value;

	public PeriodExactNumberPair() {
	}

	protected PeriodExactNumberPair(Parcel in) {
		period = in.readParcelable(Period.class.getClassLoader());
		value = in.readParcelable(ExactNumber.class.getClassLoader());
	}

	public static final Creator<PeriodExactNumberPair> CREATOR = new Creator<PeriodExactNumberPair>() {
		@Override
		public PeriodExactNumberPair createFromParcel(Parcel in) {
			return new PeriodExactNumberPair(in);
		}

		@Override
		public PeriodExactNumberPair[] newArray(int size) {
			return new PeriodExactNumberPair[size];
		}
	};

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public ExactNumber getValue() {
		return value;
	}

	public void setValue(ExactNumber value) {
		this.value = value;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(period, i);
		parcel.writeParcelable(value, i);
	}

}
