package se.tink.core.models.transaction;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import com.tink.model.misc.Amount;

public class Transaction implements Comparable<Transaction>, Parcelable {

	private String accountId;
	private Amount amount;
	private Amount dispensableAmount;
	private String categoryCode;
	private String description;
	private String secondaryDescription;
	private String id;
	private String notes;
	private DateTime originalTimestamp;
	private String originalDescription;
	private boolean pending;
	private DateTime timestamp;
	private DateTime insertionTime;
	private String type;
	private TransactionDetails details;
	private List<Tag> tags;

	public Transaction() {
	}

	protected Transaction(Parcel in) {
		accountId = in.readString();
		categoryCode = in.readString();
		description = in.readString();
		secondaryDescription = in.readString();
		id = in.readString();
		notes = in.readString();
		originalDescription = in.readString();
		pending = in.readByte() != 0;
		type = in.readString();
		upcoming = in.readByte() != 0;
		originalTimestamp = new DateTime(in.readLong());
		timestamp = new DateTime(in.readLong());
		insertionTime = new DateTime(in.readLong());
		amount = in.readParcelable(Amount.class.getClassLoader());
		dispensableAmount = in.readParcelable(Amount.class.getClassLoader());
		details = in.readParcelable(TransactionDetails.class.getClassLoader());
		tags = new ArrayList<>();
		in.readList(tags, null);
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(accountId);
		parcel.writeString(categoryCode);
		parcel.writeString(description);
		parcel.writeString(secondaryDescription);
		parcel.writeString(id);
		parcel.writeString(notes);
		parcel.writeString(originalDescription);
		parcel.writeByte((byte) (pending ? 1 : 0));
		parcel.writeString(type);
		parcel.writeByte((byte) (upcoming ? 1 : 0));
		parcel.writeLong(originalTimestamp.getMillis());
		parcel.writeLong(timestamp.getMillis());
		parcel.writeLong(insertionTime != null ? insertionTime.getMillis() : 0L);
		parcel.writeParcelable(amount, i);
		parcel.writeParcelable(dispensableAmount, i);
		parcel.writeParcelable(details, i);
		parcel.writeList(tags);
	}

	public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
		@Override
		public Transaction createFromParcel(Parcel in) {
			return new Transaction(in);
		}

		@Override
		public Transaction[] newArray(int size) {
			return new Transaction[size];
		}
	};

	public boolean isUpcoming() {
		return upcoming;
	}

	public void setUpcoming(boolean upcoming) {
		this.upcoming = upcoming;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public String getOriginalDescription() {
		return originalDescription;
	}

	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}

	public DateTime getOriginalTimestamp() {
		return originalTimestamp;
	}

	public void setOriginalTimestamp(DateTime originalTimestamp) {
		this.originalTimestamp = originalTimestamp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	private boolean upcoming;

	public TransactionDetails getDetails() {
		return details;
	}

	public void setDetails(TransactionDetails details) {
		this.details = details;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int compareTo(@SuppressWarnings("NullableProblems") Transaction o) {
		int timestampCompare = o.timestamp.compareTo(timestamp);
		if (timestampCompare != 0) {
			return timestampCompare;
		}

		if (o.insertionTime != null && insertionTime != null) {
			int insertedTimestamp = o.insertionTime.compareTo(insertionTime);
			if (insertedTimestamp != 0) {
				return insertedTimestamp;
			}
		}

		int descriptionCompare = o.description.compareTo(description);
		if (descriptionCompare != 0) {
			return descriptionCompare;
		}

		return o.id.compareTo(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Transaction that = (Transaction) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getSecondaryDescription() {
		return secondaryDescription;
	}

	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription = secondaryDescription;
	}

	public DateTime getInsertionTime() {
		return insertionTime;
	}

	public void setInsertionTime(DateTime insertionTime) {
		this.insertionTime = insertionTime;
	}

	public Amount getDispensableAmount() {
		return dispensableAmount;
	}

	public void setDispensableAmount(Amount dispensableAmount) {
		this.dispensableAmount = dispensableAmount;
	}

	public boolean isEditable() {
		return upcoming && pending && details != null && !details.getTransferId().isEmpty();
	}
}
