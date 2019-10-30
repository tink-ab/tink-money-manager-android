package se.tink.core.models.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionDetails implements Parcelable {

	private String transferId;

	public TransactionDetails() {
	}

	protected TransactionDetails(Parcel in) {
		transferId = in.readString();
	}

	public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
		@Override
		public TransactionDetails createFromParcel(Parcel in) {
			return new TransactionDetails(in);
		}

		@Override
		public TransactionDetails[] newArray(int size) {
			return new TransactionDetails[size];
		}
	};

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferTransactionId) {
		this.transferId = transferTransactionId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(transferId);
	}

}
