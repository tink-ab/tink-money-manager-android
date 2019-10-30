package se.tink.converter.authentication;

import se.tink.converter.ModelConverter;
import se.tink.core.models.authentication.SmsAuthentiticationResponse;
import se.tink.core.models.authentication.SmsAuthentiticationResponse.Status;
import se.tink.grpc.v1.models.SmsOtpStatus;
import se.tink.grpc.v1.rpc.VerifySmsOtpResponse;
import se.tink.modelConverter.AbstractConverter;

public class VerifySmsOtpResponseConverter extends
	AbstractConverter<VerifySmsOtpResponse, SmsAuthentiticationResponse> {

	private final ModelConverter mapper;

	public VerifySmsOtpResponseConverter(ModelConverter converter) {
		mapper = converter;
	}

	@Override
	public SmsAuthentiticationResponse convert(VerifySmsOtpResponse source) {
		SmsAuthentiticationResponse smsAuthentiticationResponse = new SmsAuthentiticationResponse();
		smsAuthentiticationResponse.setSmsOtpVerificationToken(source.getSmsOtpVerificationToken());
		smsAuthentiticationResponse.setStatus(getStatus(source.getStatus()));
		smsAuthentiticationResponse.setExistingUser(source.getExistingUser());
		return smsAuthentiticationResponse;
	}

	@Override
	public Class<VerifySmsOtpResponse> getSourceClass() {
		return VerifySmsOtpResponse.class;
	}

	@Override
	public Class<SmsAuthentiticationResponse> getDestinationClass() {
		return SmsAuthentiticationResponse.class;
	}

	private SmsAuthentiticationResponse.Status getStatus(SmsOtpStatus status) {
		switch (status) {
			case SMS_OTP_STATUS_CORRECT:
				return Status.SMS_OTP_STATUS_CORRECT;
			case SMS_OTP_STATUS_EXPIRED:
				return Status.SMS_OTP_STATUS_EXPIRED;
			case SMS_OTP_STATUS_INCORRECT:
				return Status.SMS_OTP_STATUS_INCORRECT;
			case SMS_OTP_STATUS_TOO_MANY_ATTEMPTS:
				return Status.SMS_OTP_STATUS_TOO_MANY_ATTEMPTS;
			case SMS_OTP_STATUS_UNKNOWN:
			case UNRECOGNIZED:
			default:
				return Status.SMS_OTP_STATUS_UNKNOWN;
		}
	}
}
