package se.tink.repository.cache.models;

import androidx.room.Entity;

@Entity
public class AmountEntity {

	private long unscaledValue;
	private long scale;
	private String currencyCode;

	public long getUnscaledValue() {
		return unscaledValue;
	}

	public void setUnscaledValue(long unscaledValue) {
		this.unscaledValue = unscaledValue;
	}

	public long getScale() {
		return scale;
	}

	public void setScale(long scale) {
		this.scale = scale;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
