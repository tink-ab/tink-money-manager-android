package se.tink.core.models.identity;


import java.util.List;

public class IdentityState {

	private IdentityStateAddress address;
	private List<IdentityStateCompanyEngagement> companyEngagements;
	private String firstName;
	private String lastName;
	private List<IdentityStateRecordOfNonPayment> recordsOfNonPayment;
	private IdentityStateTaxDeclaration mostRecentTaxDeclaration;
	private IdentityStateOutstandingDebt outstandingDebt;
	private List<IdentityStateProperty> propertiesList;
	private IdentityStateCreditScore creditScore;
	private String nationalId;

	public void setAddress(IdentityStateAddress address) {
		this.address = address;
	}

	public void setCompanyEngagements(List<IdentityStateCompanyEngagement> companyEngagements) {
		this.companyEngagements = companyEngagements;
	}

	public IdentityStateAddress getAddress() {
		return address;
	}

	public List<IdentityStateCompanyEngagement> getCompanyEngagements() {
		return companyEngagements;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public List<IdentityStateRecordOfNonPayment> getRecordsOfNonPayment() {
		return recordsOfNonPayment;
	}

	public IdentityStateTaxDeclaration getMostRecentTaxDeclaration() {
		return mostRecentTaxDeclaration;
	}

	public IdentityStateOutstandingDebt getOutstandingDebt() {
		return outstandingDebt;
	}

	public List<IdentityStateProperty> getPropertiesList() {
		return propertiesList;
	}

	public IdentityStateCreditScore getCreditScore() {
		return creditScore;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRecordsOfNonPayment(List<IdentityStateRecordOfNonPayment> recordsOfNonPayment) {
		this.recordsOfNonPayment = recordsOfNonPayment;
	}

	public void setMostRecentTaxDeclaration(IdentityStateTaxDeclaration mostRecentTaxDeclaration) {
		this.mostRecentTaxDeclaration = mostRecentTaxDeclaration;
	}

	public void setOutstandingDebt(IdentityStateOutstandingDebt outstandingDebt) {
		this.outstandingDebt = outstandingDebt;
	}

	public void setPropertiesList(List<IdentityStateProperty> propertiesList) {
		this.propertiesList = propertiesList;
	}

	public void setCreditScore(IdentityStateCreditScore creditScore) {
		this.creditScore = creditScore;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getNationalId() {
		return nationalId;
	}
}
