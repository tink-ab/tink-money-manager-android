package se.tink.core.models.transfer;

import se.tink.core.models.Images;

public class Clearing {

	private String bankDisplayName;
	private Images images;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public String getBankDisplayName() {
		return bankDisplayName;
	}

	public void setBankDisplayName(String bankDisplayName) {
		this.bankDisplayName = bankDisplayName;
	}
}
