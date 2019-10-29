package se.tink.core.models.follow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import se.tink.core.models.misc.ExactNumber;

public class SavingsFollowCriteria implements Parcelable {

	private ExactNumber targetAmount;
	private List<String> accountIds;
	private String period;

	public SavingsFollowCriteria() {
	}

	protected SavingsFollowCriteria(Parcel in) {
		targetAmount = in.readParcelable(ExactNumber.class.getClassLoader());
		accountIds = in.createStringArrayList();
		period = in.readString();
	}

	public static final Creator<SavingsFollowCriteria> CREATOR = new Creator<SavingsFollowCriteria>() {
		@Override
		public SavingsFollowCriteria createFromParcel(Parcel in) {
			return new SavingsFollowCriteria(in);
		}

		@Override
		public SavingsFollowCriteria[] newArray(int size) {
			return new SavingsFollowCriteria[size];
		}
	};

	public ExactNumber getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(ExactNumber targetAmount) {
		this.targetAmount = targetAmount;
	}

	public List<String> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(List<String> accountIds) {
		this.accountIds = accountIds;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(targetAmount, i);
		parcel.writeStringList(accountIds);
		parcel.writeString(period);
	}

}
