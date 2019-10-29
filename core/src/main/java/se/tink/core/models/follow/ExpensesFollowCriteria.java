package se.tink.core.models.follow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import se.tink.core.models.misc.ExactNumber;

public class ExpensesFollowCriteria implements Parcelable {

	private ExactNumber targetAmount;
	private List<String> categoryCodes;

	public ExpensesFollowCriteria() {
	}

	protected ExpensesFollowCriteria(Parcel in) {
		categoryCodes = in.createStringArrayList();
		targetAmount = in.readParcelable(ExactNumber.class.getClassLoader());
	}

	public static final Creator<ExpensesFollowCriteria> CREATOR = new Creator<ExpensesFollowCriteria>() {
		@Override
		public ExpensesFollowCriteria createFromParcel(Parcel in) {
			return new ExpensesFollowCriteria(in);
		}

		@Override
		public ExpensesFollowCriteria[] newArray(int size) {
			return new ExpensesFollowCriteria[size];
		}
	};

	public ExactNumber getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(ExactNumber targetAmount) {
		this.targetAmount = targetAmount;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeStringList(categoryCodes);
		parcel.writeParcelable(targetAmount, i);
	}

}
