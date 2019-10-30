package se.tink.core.models.user;

public class CollectBankIdAuthenticationResponse {

	private String nationalId;
	private BankIdAuthenticationStatus status;

	public CollectBankIdAuthenticationResponse() {

	}

	public CollectBankIdAuthenticationResponse(String nationalId,
		BankIdAuthenticationStatus status) {
		this.nationalId = nationalId;
		this.status = status;
	}

	public String getNationalId() {
		return nationalId;
	}

	public BankIdAuthenticationStatus getStatus() {
		return status;
	}
}
