package se.tink.core.models.identity;

public class IdentityStateAddress {

	private String city;
	private String community;
	private String name;
	private String postalCode;

	public void setCity(String city) {
		this.city = city;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public String getCommunity() {
		return community;
	}

	public String getName() {
		return name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return name + ", " + postalCode + " " + city;
	}
}
