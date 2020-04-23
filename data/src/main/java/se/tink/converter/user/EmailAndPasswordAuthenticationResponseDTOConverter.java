package se.tink.converter.user;

import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.EmailAndPasswordAuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class EmailAndPasswordAuthenticationResponseDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse, EmailAndPasswordAuthenticationResponse> {

	private ModelConverter modelConverter;

	public EmailAndPasswordAuthenticationResponseDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public EmailAndPasswordAuthenticationResponse convert(
		se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse source) {
		return new EmailAndPasswordAuthenticationResponse(
			source.getAuthenticationToken(),
			EnumMappers.GRPC_TO_MODEL_AUTHENTICATION_STATUS_MAP.get(source.getStatus())
		);
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.EmailAndPasswordAuthenticationResponse.class;
	}

	@Override
	public Class<EmailAndPasswordAuthenticationResponse> getDestinationClass() {
		return EmailAndPasswordAuthenticationResponse.class;
	}
}
