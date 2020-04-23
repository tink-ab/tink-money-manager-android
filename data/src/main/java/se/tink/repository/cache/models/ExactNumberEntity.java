package se.tink.repository.cache.models;


import androidx.room.Entity;

@Entity
public class ExactNumberEntity {

	private long unscaledValue;
	private long scale;

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
}
