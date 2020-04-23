package se.tink.repository.cache.models;

import androidx.room.Entity;

@Entity
public class ImagesEntity {

	private String icon;
	private String banner;

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
}
