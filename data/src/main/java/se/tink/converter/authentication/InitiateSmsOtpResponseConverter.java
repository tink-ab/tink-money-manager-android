package se.tink.converter.authentication;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.authentication.InitiateSmsVerificationResponse;
import se.tink.grpc.v1.rpc.InitiateSmsOtpResponse;
import se.tink.modelConverter.AbstractConverter;

public class InitiateSmsOtpResponseConverter extends
	AbstractConverter<InitiateSmsOtpResponse, InitiateSmsVerificationResponse> {

	private final ModelConverter mapper;

	public InitiateSmsOtpResponseConverter(ModelConverter converter) {
		mapper = converter;
	}

	@Override
	public InitiateSmsVerificationResponse convert(InitiateSmsOtpResponse source) {
		InitiateSmsVerificationResponse destination = new InitiateSmsVerificationResponse();
		destination.setExpireAt(mapper.map(source.getExpireAt(), DateTime.class));
		destination.setOtpLength(source.getOtpLength());
		destination.setSmsOtpVerificationToken(source.getSmsOtpVerificationToken());
		return destination;
	}

	@Override
	public Class<InitiateSmsOtpResponse> getSourceClass() {
		return InitiateSmsOtpResponse.class;
	}

	@Override
	public Class<InitiateSmsVerificationResponse> getDestinationClass() {
		return InitiateSmsVerificationResponse.class;
	}
}
