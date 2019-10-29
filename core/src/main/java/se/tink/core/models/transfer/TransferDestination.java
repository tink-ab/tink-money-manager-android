package se.tink.core.models.transfer;

import android.os.Parcel;
import android.os.Parcelable;
import se.tink.core.models.Images;
import se.tink.core.models.misc.Amount;

public class TransferDestination implements Parcelable {

	private String uri;
	private String name;
	private Amount balance;
	private String displayBankName;
	private Type type;
	private Images images;
	private String displayAccountNumber;
	private boolean matchesMultiple;

	public TransferDestination() {

	}

	protected TransferDestination(Parcel in) {
		uri = in.readString();
		name = in.readString();
		balance = in.readParcelable(Amount.class.getClassLoader());
		displayBankName = in.readString();
		type = (Type) in.readSerializable();
		images = in.readParcelable(Images.class.getClassLoader());
		displayAccountNumber = in.readString();
		matchesMultiple = in.readByte() != 0;
	}

	public static final Creator<TransferDestination> CREATOR = new Creator<TransferDestination>() {
		@Override
		public TransferDestination createFromParcel(Parcel in) {
			return new TransferDestination(in);
		}

		@Override
		public TransferDestination[] newArray(int size) {
			return new TransferDestination[size];
		}
	};

	public boolean isMatchesMultiple() {
		return matchesMultiple;
	}

	public void setMatchesMultiple(boolean matchesMultiple) {
		this.matchesMultiple = matchesMultiple;
	}

	public String getDisplayAccountNumber() {
		return displayAccountNumber;
	}

	public void setDisplayAccountNumber(String displayAccountNumber) {
		this.displayAccountNumber = displayAccountNumber;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDisplayBankName() {
		return displayBankName;
	}

	public void setDisplayBankName(String displayBankName) {
		this.displayBankName = displayBankName;
	}

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isPayable() {
		return getUri().startsWith("se-pg://") || getUri().startsWith("se-bg://");
	}

	public boolean isInternal() {
		return getType() != Type.TYPE_EXTERNAL;
	}

	public boolean isExternal() {
		return getType() == Type.TYPE_EXTERNAL;
	}

	public boolean isTransferable() {
		return getUri().startsWith("se://") || getUri().startsWith("tink://") || getUri().startsWith("iban://");
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(uri);
		dest.writeString(name);
		dest.writeParcelable(balance, flags);
		dest.writeString(displayBankName);
		dest.writeSerializable(type);
		dest.writeParcelable(images, flags);
		dest.writeString(displayAccountNumber);
		dest.writeByte((byte) (matchesMultiple ? 1 : 0));
	}

	public enum Type {
		TYPE_UNKNOWN,
		TYPE_CHECKING,
		TYPE_SAVINGS,
		TYPE_INVESTMENT,
		TYPE_MORTGAGE,
		TYPE_CREDIT_CARD,
		TYPE_LOAN,
		TYPE_DUMMY,
		TYPE_PENSION,
		TYPE_OTHER,
		TYPE_EXTERNAL,
	}
}
