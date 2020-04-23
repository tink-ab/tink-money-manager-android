package se.tink.core.models.account;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import se.tink.core.models.Images;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.ExactNumber;


public class Account implements Comparable<Account>, Parcelable {

	private List<String> identifiers;

	public Account() {

	}

	protected Account(Parcel in) {
		accountNumber = in.readString();
		balance = in.readParcelable(Amount.class.getClassLoader());
		credentialId = in.readString();
		excluded = in.readByte() != 0;
		favored = in.readByte() != 0;
		transactional = in.readByte() != 0;
		closed = in.readByte() != 0;
		id = in.readString();
		name = in.readString();
		ownership = in.readParcelable(ExactNumber.class.getClassLoader());
		type = (Type) in.readSerializable();
		details = in.readParcelable(AccountDetails.class.getClassLoader());
		images = in.readParcelable(Images.class.getClassLoader());
	}


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public boolean isFavored() {
		return favored;
	}

	public void setFavored(boolean favored) {
		this.favored = favored;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExactNumber getOwnership() {
		return ownership;
	}

	public void setOwnership(ExactNumber ownership) {
		this.ownership = ownership;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	private String accountNumber;
	private Amount balance;
	private String credentialId;
	private boolean excluded;
	private boolean favored;
	private boolean transactional;
	private boolean closed;
	private String id;
	private String name;
	private ExactNumber ownership;
	private Type type;
	private AccountDetails details;

	public boolean isTransactional() {
		return transactional;
	}

	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}

	@Override
	public int compareTo(@SuppressWarnings("NullableProblems") Account o) {
		int nameCompare = name.compareTo(o.name);
		if (nameCompare != 0) {
			return nameCompare;
		}

		int accountNumberCompare = accountNumber.compareTo(o.accountNumber);
		if (accountNumberCompare != 0) {
			return accountNumberCompare;
		}

		return id.compareTo(o.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account otherAccount = (Account) obj;
			return otherAccount.getId().equals(id);
		}
		return false;
	}

	public boolean isEveryday() {
		return type == Type.TYPE_CHECKING || type == Type.TYPE_CREDIT_CARD
			|| type == Type.TYPE_OTHER || type == Type.TYPE_UNKNOWN;
	}

	public boolean isSavings() {
		return type == Type.TYPE_SAVINGS || type == Type.TYPE_INVESTMENT
			|| type == Type.TYPE_PENSION;
	}

	public boolean isLoan() {
		return type == Type.TYPE_LOAN || type == Type.TYPE_MORTGAGE;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public List<String> getIdentifiers() {
		return identifiers;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
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
		UNRECOGNIZED
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public AccountDetails getDetails() {
		return details;
	}

	public void setDetails(AccountDetails details) {
		this.details = details;
	}

	public String getUri() {
		return "tink://" + id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(accountNumber);
		dest.writeParcelable(balance, flags);
		dest.writeString(credentialId);
		dest.writeByte((byte) (excluded ? 1 : 0));
		dest.writeByte((byte) (favored ? 1 : 0));
		dest.writeByte((byte) (transactional ? 1 : 0));
		dest.writeByte((byte) (closed ? 1 : 0));
		dest.writeString(id);
		dest.writeString(name);
		dest.writeParcelable(ownership, flags);
		dest.writeSerializable(type);
		dest.writeParcelable(details, flags);
		dest.writeParcelable(images, flags);
	}

	public static final Creator<Account> CREATOR = new Creator<Account>() {
		@Override
		public Account createFromParcel(Parcel in) {
			return new Account(in);
		}

		@Override
		public Account[] newArray(int size) {
			return new Account[size];
		}
	};

	private Images images;
}