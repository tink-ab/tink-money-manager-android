package se.tink.converter.identity;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.grpc.v1.models.IdentityAnswerKey;
import se.tink.grpc.v1.models.IdentityEventAnswer;
import se.tink.modelConverter.AbstractConverter;

public class IdentityEventAnswerConverter extends
	AbstractConverter<se.tink.core.models.identity.IdentityEventAnswer, IdentityEventAnswer> {

	private final ModelConverter modelConverter;

	public IdentityEventAnswerConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityEventAnswer convert(se.tink.core.models.identity.IdentityEventAnswer source) {
		IdentityEventAnswer.Builder builder = IdentityEventAnswer.newBuilder();
		builder.setKey(modelConverter.map(source.getKey(), IdentityAnswerKey.class));
		builder.setLabel(source.getLabel());

		return builder.build();
	}

	@Override
	public Class<se.tink.core.models.identity.IdentityEventAnswer> getSourceClass() {
		return se.tink.core.models.identity.IdentityEventAnswer.class;
	}

	@Override
	public Class<IdentityEventAnswer> getDestinationClass() {
		return IdentityEventAnswer.class;
	}
}
