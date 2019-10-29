package se.tink.core.models.identity;

import org.joda.time.DateTime;

public class IdentityStateProperty {

	private DateTime acquisitionDate;
	private String municipality;
	private String name;
	private String number;

	public void setAcquisitionDate(DateTime acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public DateTime getAcquisitionDate() {
		return acquisitionDate;
	}

	public String getMunicipality() {
		return municipality;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getStringRepresentation() {
		return getName();
	}
}
