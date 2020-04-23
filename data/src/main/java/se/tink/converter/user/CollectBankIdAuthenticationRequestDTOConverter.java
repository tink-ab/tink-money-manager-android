package se.tink.converter.user;


import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.CollectBankIdAuthenticationResponse;
import se.tink.modelConverter.AbstractConverter;

public class CollectBankIdAuthenticationRequestDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.CollectBankIdAuthenticationResponse, CollectBankIdAuthenticationResponse> {

	private ModelConverter modelConverter;

	public CollectBankIdAuthenticationRequestDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public CollectBankIdAuthenticationResponse convert(
		se.tink.grpc.v1.rpc.CollectBankIdAuthenticationResponse source) {
		return new CollectBankIdAuthenticationResponse(
			source.getNationalId(),
			EnumMappers.GRPC_TO_MODEL_BANK_ID_AUTHENTICATION_STATUS_MAP.get(source.getStatus())
			// TODO might need getOrDefault
		);
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.CollectBankIdAuthenticationResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.CollectBankIdAuthenticationResponse.class;
	}

	@Override
	public Class<CollectBankIdAuthenticationResponse> getDestinationClass() {
		return CollectBankIdAuthenticationResponse.class;
	}
}
