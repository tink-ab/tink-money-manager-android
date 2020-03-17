package se.tink.core.models.account;


import com.tink.model.misc.ExactNumber;
import android.os.Parcel;
import android.os.Parcelable;

public class AccountDetails implements Parcelable {

	public AccountDetails() {

	}

	protected AccountDetails(Parcel in) {
		if (in.readByte() == 0) {
			numMonthsBound = null;
		} else {
			numMonthsBound = in.readInt();
		}
		type = (Type) in.readSerializable();
		interest = in.readParcelable(ExactNumber.class.getClassLoader());
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ExactNumber getInterest() {
		return interest;
	}

	public void setInterest(ExactNumber interest) {
		this.interest = interest;
	}

	public Integer getNumMonthsBound() {
		return numMonthsBound;
	}

	public void setNumMonthsBound(Integer numMonthsBound) {
		this.numMonthsBound = numMonthsBound;
	}

	public enum Type {
		TYPE_UNKNOWN,
		TYPE_MORTGAGE,
		TYPE_BLANCO,
		TYPE_MEMBERSHIP,
		TYPE_VEHICLE,
		TYPE_LAND,
		TYPE_STUDENT,
		TYPE_OTHER,
	}

	private Integer numMonthsBound;
	private Type type;
	private ExactNumber interest;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (numMonthsBound == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(numMonthsBound);
		}
		dest.writeSerializable(type);
		dest.writeParcelable(interest, flags);
	}

	public static final Creator<AccountDetails> CREATOR = new Creator<AccountDetails>() {
		@Override
		public AccountDetails createFromParcel(Parcel in) {
			return new AccountDetails(in);
		}

		@Override
		public AccountDetails[] newArray(int size) {
			return new AccountDetails[size];
		}
	};
}
