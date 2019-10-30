package se.tink.repository.cache.models;

import androidx.room.Entity;

@Entity
public class AccountDetailsEntity {

	private int numMonthsBound;
	private String detailsType;
	private long interestValue;
	private long interestScale;

	public int getNumMonthsBound() {
		return numMonthsBound;
	}

	public void setNumMonthsBound(int numMonthsBound) {
		this.numMonthsBound = numMonthsBound;
	}

	public String getDetailsType() {
		return detailsType;
	}

	public void setDetailsType(String detailsType) {
		this.detailsType = detailsType;
	}

	public long getInterestValue() {
		return interestValue;
	}

	public void setInterestValue(long interestValue) {
		this.interestValue = interestValue;
	}

	public long getInterestScale() {
		return interestScale;
	}

	public void setInterestScale(long interestScale) {
		this.interestScale = interestScale;
	}
}
