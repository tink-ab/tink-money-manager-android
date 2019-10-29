package se.tink.core.models.authentication;

public class SmsAuthentiticationResponse {

	private String smsOtpVerificationToken;
	private Status status;
	private boolean existingUser;

	public void setSmsOtpVerificationToken(String smsOtpVerificationToken) {
		this.smsOtpVerificationToken = smsOtpVerificationToken;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public String getSmsOtpVerificationToken() {
		return smsOtpVerificationToken;
	}

	public enum Status {
		SMS_OTP_STATUS_EXPIRED, SMS_OTP_STATUS_INCORRECT, SMS_OTP_STATUS_TOO_MANY_ATTEMPTS, SMS_OTP_STATUS_UNKNOWN, SMS_OTP_STATUS_CORRECT
	}

	public boolean isExistingUser() {
		return existingUser;
	}

	public void setExistingUser(boolean existingUser) {
		this.existingUser = existingUser;
	}

}
