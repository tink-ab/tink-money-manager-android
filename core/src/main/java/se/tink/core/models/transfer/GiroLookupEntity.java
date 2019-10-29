package se.tink.core.models.transfer;

import se.tink.core.models.Images;
import android.os.Parcel;
import android.os.Parcelable;

public class GiroLookupEntity implements Parcelable {

	private String displayName;
	private String identifier;
	private Images images;
	private String displayNumber;

	public GiroLookupEntity() {

	}

	protected GiroLookupEntity(Parcel in) {
		displayName = in.readString();
		identifier = in.readString();
		images = in.readParcelable(Images.class.getClassLoader());
		displayNumber = in.readString();
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String getDisplayNumber() {
		return displayNumber;
	}

	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}

	public String getDisplayNameOrNumber() {
		String displayName = getDisplayName();
		String displayNumber = getDisplayNumber();
		if(displayName != null && !displayName.isEmpty()){
			return displayName;
		} else if(displayNumber != null && !displayNumber.isEmpty()) {
			return displayNumber;
		} else {
			return null;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(displayName);
		dest.writeString(identifier);
		dest.writeParcelable(images, flags);
		dest.writeString(displayNumber);
	}

	public static final Creator<GiroLookupEntity> CREATOR = new Creator<GiroLookupEntity>() {
		@Override
		public GiroLookupEntity createFromParcel(Parcel in) {
			return new GiroLookupEntity(in);
		}

		@Override
		public GiroLookupEntity[] newArray(int size) {
			return new GiroLookupEntity[size];
		}
	};
}
