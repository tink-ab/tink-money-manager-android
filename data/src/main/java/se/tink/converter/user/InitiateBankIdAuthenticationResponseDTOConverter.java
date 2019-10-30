package se.tink.converter.user;

import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.InitiateBankIdAuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class InitiateBankIdAuthenticationResponseDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse, InitiateBankIdAuthenticationResponse> {

	private ModelConverter modelConverter;

	public InitiateBankIdAuthenticationResponseDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public InitiateBankIdAuthenticationResponse convert(
		se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse source) {
		return new InitiateBankIdAuthenticationResponse(source.getAutoStartToken(),
			source.getAuthenticationToken(),
			EnumMappers.GRPC_TO_MODEL_BANK_ID_AUTHENTICATION_STATUS_MAP.get(source.getStatus()));
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.InitiateBankIdAuthenticationResponse.class;
	}

	@Override
	public Class<InitiateBankIdAuthenticationResponse> getDestinationClass() {
		return InitiateBankIdAuthenticationResponse.class;
	}
}
