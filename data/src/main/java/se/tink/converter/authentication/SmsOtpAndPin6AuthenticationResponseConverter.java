package se.tink.converter.authentication;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.authentication.AuthenticationResponse;
import se.tink.grpc.v1.rpc.SmsOtpAndPin6AuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class SmsOtpAndPin6AuthenticationResponseConverter extends
	AbstractConverter<SmsOtpAndPin6AuthenticationResponse, AuthenticationResponse> {

	private ModelConverter modelConverter;

	public SmsOtpAndPin6AuthenticationResponseConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AuthenticationResponse convert(SmsOtpAndPin6AuthenticationResponse source) {
		AuthenticationResponse destination = new AuthenticationResponse();
		destination.setAuthenticationToken(source.getAuthenticationToken());
		if (source.getStatus() != null) {
			destination.setStatus(modelConverter
				.map(source.getStatus(), se.tink.core.models.authentication.AuthenticationStatus.class));
		}
		return destination;
	}

	@Override
	public Class<SmsOtpAndPin6AuthenticationResponse> getSourceClass() {
		return SmsOtpAndPin6AuthenticationResponse.class;
	}

	@Override
	public Class<AuthenticationResponse> getDestinationClass() {
		return AuthenticationResponse.class;
	}
}
