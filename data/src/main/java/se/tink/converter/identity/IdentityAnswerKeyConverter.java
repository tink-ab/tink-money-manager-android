package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityAnswerKey;
import se.tink.modelConverter.AbstractConverter;

public class IdentityAnswerKeyConverter extends
	AbstractConverter<IdentityAnswerKey, se.tink.grpc.v1.models.IdentityAnswerKey> {

	private final ModelConverterImpl converter;

	public IdentityAnswerKeyConverter(ModelConverterImpl modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.IdentityAnswerKey convert(IdentityAnswerKey source) {
		switch (source) {
			case IDENTITY_ANSWER_KEY_OK:
				return se.tink.grpc.v1.models.IdentityAnswerKey.IDENTITY_ANSWER_KEY_OK;
			case IDENTITY_ANSWER_KEY_FRAUDULENT:
				return se.tink.grpc.v1.models.IdentityAnswerKey.IDENTITY_ANSWER_KEY_FRAUDULENT;
		}
		return se.tink.grpc.v1.models.IdentityAnswerKey.IDENTITY_ANSWER_KEY_UNKNOWN;
	}

	@Override
	public Class<IdentityAnswerKey> getSourceClass() {
		return IdentityAnswerKey.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityAnswerKey> getDestinationClass() {
		return se.tink.grpc.v1.models.IdentityAnswerKey.class;
	}

}
