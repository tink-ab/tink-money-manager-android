package se.tink.core.models.follow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import se.tink.core.models.misc.Period;

public class FollowData implements Parcelable {

	private List<PeriodExactNumberPair> historicalAmounts;
	private Period period;

	public FollowData() {
	}

	protected FollowData(Parcel in) {
		period = in.readParcelable(Period.class.getClassLoader());
		historicalAmounts = new ArrayList<>();
		in.readList(historicalAmounts, null);
	}

	public static final Creator<FollowData> CREATOR = new Creator<FollowData>() {
		@Override
		public FollowData createFromParcel(Parcel in) {
			return new FollowData(in);
		}

		@Override
		public FollowData[] newArray(int size) {
			return new FollowData[size];
		}
	};

	public List<PeriodExactNumberPair> getHistoricalAmounts() {
		return historicalAmounts;
	}

	public void setHistoricalAmounts(
		List<PeriodExactNumberPair> historicalAmounts) {
		this.historicalAmounts = historicalAmounts;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(period, i);
		parcel.writeList(historicalAmounts);
	}
}
