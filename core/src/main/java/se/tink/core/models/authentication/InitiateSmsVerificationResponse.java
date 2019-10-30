package se.tink.core.models.authentication;

import org.joda.time.DateTime;

public class InitiateSmsVerificationResponse {

	private DateTime expireAt;
	private int otpLength;
	private String smsOtpVerificationToken;

	public void setExpireAt(DateTime expireAt) {
		this.expireAt = expireAt;
	}

	public void setOtpLength(int otpLength) {
		this.otpLength = otpLength;
	}

	public void setSmsOtpVerificationToken(String smsOtpVerificationToken) {
		this.smsOtpVerificationToken = smsOtpVerificationToken;
	}

	public DateTime getExpireAt() {
		return expireAt;
	}

	public int getOtpLength() {
		return otpLength;
	}

	public String getSmsOtpVerificationToken() {
		return smsOtpVerificationToken;
	}
}
