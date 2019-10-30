package se.tink.converter.authentication;

import se.tink.converter.ModelConverter;
import se.tink.core.models.authentication.AuthenticationStatus;
import se.tink.core.models.authentication.PhoneNumberAndPin6AuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class PhoneNumberAndPin6AuthenticationResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.PhoneNumberAndPin6AuthenticationResponse, PhoneNumberAndPin6AuthenticationResponse> {

	private ModelConverter modelConverter;

	public PhoneNumberAndPin6AuthenticationResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public PhoneNumberAndPin6AuthenticationResponse convert(
		se.tink.grpc.v1.rpc.PhoneNumberAndPin6AuthenticationResponse source) {
		PhoneNumberAndPin6AuthenticationResponse destination = new PhoneNumberAndPin6AuthenticationResponse();
		source.getAuthenticationToken();
		if (source.getAuthenticationToken() != null) {
			destination.setAuthenticationToken(source.getAuthenticationToken());
		}
		if (source.getStatus() != null) {
			destination
				.setStatus(modelConverter.map(source.getStatus(), AuthenticationStatus.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.PhoneNumberAndPin6AuthenticationResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.PhoneNumberAndPin6AuthenticationResponse.class;
	}

	@Override
	public Class<PhoneNumberAndPin6AuthenticationResponse> getDestinationClass() {
		return PhoneNumberAndPin6AuthenticationResponse.class;
	}
}
