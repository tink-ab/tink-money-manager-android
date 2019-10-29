package se.tink.core.models.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {

	private String name;

	public Tag() {
	}

	protected Tag(Parcel in) {
		name = in.readString();
	}

	public static final Creator<Tag> CREATOR = new Creator<Tag>() {
		@Override
		public Tag createFromParcel(Parcel in) {
			return new Tag(in);
		}

		@Override
		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return name.equals(((Tag) obj).getName());
		} else if (obj instanceof String) {
			return name.equals(obj);
		}
		return super.equals(obj);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(name);
	}

}
