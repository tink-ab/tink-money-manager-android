package se.tink.core.models.identity;

import java.util.List;
import org.joda.time.DateTime;

public class IdentityStateCompanyEngagement {

	private List<IdentityStateRole> roles;
	private IdentityStateCompany company;
	private DateTime dateIn;

	public void setRoles(List<IdentityStateRole> roles) {
		this.roles = roles;
	}

	public void setCompany(IdentityStateCompany company) {
		this.company = company;
	}

	public void setDateIn(DateTime dateIn) {
		this.dateIn = dateIn;
	}

	public DateTime getDateIn() {
		return dateIn;
	}

	public IdentityStateCompany getCompany() {
		return company;
	}

	public List<IdentityStateRole> getRoles() {
		return roles;
	}

	public String getStringRepresentation() {
		return company.getName();
	}
}
