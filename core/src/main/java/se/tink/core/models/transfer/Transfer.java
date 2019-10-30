package se.tink.core.models.transfer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import org.joda.time.DateTime;
import se.tink.core.models.misc.Amount;

public class Transfer implements Parcelable {

	private Amount amount;
	private String credentialId;
	private String destinationUri;
	private String destinationMessage;
	private String id;
	private String sourceUri;
	private String sourceMessage;
	private Type type;
	private DateTime dueDate;

	public Transfer() {

	}

	protected Transfer(Parcel in) {
		amount = in.readParcelable(Amount.class.getClassLoader());
		credentialId = in.readString();
		destinationUri = in.readString();
		destinationMessage = in.readString();
		id = in.readString();
		sourceUri = in.readString();
		sourceMessage = in.readString();
		type = (Type) in.readSerializable();
		dueDate = (DateTime) in.readSerializable();
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public String getDestinationUri() {
		return destinationUri;
	}

	public void setDestinationUri(String destinationUri) {
		this.destinationUri = destinationUri;
	}

	public String getDestinationMessage() {
		return destinationMessage;
	}

	public void setDestinationMessage(String destinationMessage) {
		this.destinationMessage = destinationMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceUri() {
		return sourceUri;
	}

	public String getSourceUriWithoutPrefix() {
		Uri uri = Uri.parse(getSourceUri());
		return uri.getHost() + uri.getPath();
	}

	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}

	public String getSourceMessage() {
		return sourceMessage;
	}

	public void setSourceMessage(String sourceMessage) {
		this.sourceMessage = sourceMessage;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
		this.dueDate = dueDate;
	}

	public enum Type {
		TYPE_UNKNOWN,
		TYPE_EINVOICE,
		TYPE_PAYMENT,
		TYPE_BANK_TRANSFER,
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(amount, flags);
		dest.writeString(credentialId);
		dest.writeString(destinationUri);
		dest.writeString(destinationMessage);
		dest.writeString(id);
		dest.writeString(sourceUri);
		dest.writeString(sourceMessage);
		dest.writeSerializable(type);
		dest.writeSerializable(dueDate);
	}

	public static final Creator<Transfer> CREATOR = new Creator<Transfer>() {
		@Override
		public Transfer createFromParcel(Parcel in) {
			return new Transfer(in);
		}

		@Override
		public Transfer[] newArray(int size) {
			return new Transfer[size];
		}
	};

}
