package se.tink.converter.identity;


import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityAnswerKey;
import se.tink.modelConverter.AbstractConverter;

public class IdentityAnswerKeyDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityAnswerKey, IdentityAnswerKey> {

	private final ModelConverterImpl converter;

	public IdentityAnswerKeyDTOConverter(ModelConverterImpl modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public IdentityAnswerKey convert(se.tink.grpc.v1.models.IdentityAnswerKey source) {
		switch (source) {
			case IDENTITY_ANSWER_KEY_OK:
				return se.tink.core.models.identity.IdentityAnswerKey.IDENTITY_ANSWER_KEY_OK;
			case IDENTITY_ANSWER_KEY_FRAUDULENT:
				return se.tink.core.models.identity.IdentityAnswerKey.IDENTITY_ANSWER_KEY_FRAUDULENT;
		}
		return se.tink.core.models.identity.IdentityAnswerKey.IDENTITY_ANSWER_KEY_UNKNOWN;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityAnswerKey> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityAnswerKey.class;
	}

	@Override
	public Class<IdentityAnswerKey> getDestinationClass() {
		return IdentityAnswerKey.class;
	}

}
