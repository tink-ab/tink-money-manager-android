package se.tink.core.models.follow;


import android.os.Parcel;
import android.os.Parcelable;
import se.tink.core.models.misc.ExactNumber;

public class SearchFollowCriteria implements Parcelable {

	private ExactNumber targetAmount;
	private String query;

	public SearchFollowCriteria() {
	}

	protected SearchFollowCriteria(Parcel in) {
		targetAmount = in.readParcelable(ExactNumber.class.getClassLoader());
		query = in.readString();
	}

	public static final Creator<SearchFollowCriteria> CREATOR = new Creator<SearchFollowCriteria>() {
		@Override
		public SearchFollowCriteria createFromParcel(Parcel in) {
			return new SearchFollowCriteria(in);
		}

		@Override
		public SearchFollowCriteria[] newArray(int size) {
			return new SearchFollowCriteria[size];
		}
	};

	public ExactNumber getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(ExactNumber targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(targetAmount, i);
		parcel.writeString(query);
	}

}
