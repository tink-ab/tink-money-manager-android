package se.tink.core.models.device;

public enum AuthenticationMethod {

	AUTHENTICATION_METHOD_UNKNOWN(0),
	AUTHENTICATION_METHOD_BANKID(1),
	AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD(2),
	AUTHENTICATION_METHOD_SMS_OTP_AND_PIN6(3),
	AUTHENTICATION_METHOD_PHONE_NUMBER_AND_PIN6(4),
	AUTHENTICATION_METHOD_ABN_AMRO_PIN5(5);

	private int key;

	AuthenticationMethod(int key) {
		this.key = key;
	}

	public static AuthenticationMethod getAuthMethodByKey(int key) {
		for (AuthenticationMethod result : AuthenticationMethod.values()) {
			if (result.getKey() == key) {
				return result;
			}
		}
		return null;
	}

	public int getKey() {
		return key;
	}

}
