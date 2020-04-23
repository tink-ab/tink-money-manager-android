package se.tink.core.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class Images implements Parcelable, Serializable {

	private String icon;
	private String banner;

	public Images() {

	}

	protected Images(Parcel in) {
		icon = in.readString();
		banner = in.readString();
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(icon);
		dest.writeString(banner);
	}

	public static final Creator<Images> CREATOR = new Creator<Images>() {
		@Override
		public Images createFromParcel(Parcel in) {
			return new Images(in);
		}

		@Override
		public Images[] newArray(int size) {
			return new Images[size];
		}
	};
}
