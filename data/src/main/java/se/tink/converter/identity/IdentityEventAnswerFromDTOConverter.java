package se.tink.converter.identity;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.grpc.v1.models.IdentityEventAnswer;
import se.tink.modelConverter.AbstractConverter;

public class IdentityEventAnswerFromDTOConverter extends
	AbstractConverter<IdentityEventAnswer, se.tink.core.models.identity.IdentityEventAnswer> {

	private final ModelConverter modelConverter;

	public IdentityEventAnswerFromDTOConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.core.models.identity.IdentityEventAnswer convert(IdentityEventAnswer source) {
		se.tink.core.models.identity.IdentityEventAnswer builder = new se.tink.core.models.identity.IdentityEventAnswer();
		builder.setKey(modelConverter.map(source.getKey(), se.tink.core.models.identity.IdentityAnswerKey.class));
		builder.setLabel(source.getLabel());

		return builder;
	}

	@Override
	public Class<IdentityEventAnswer> getSourceClass() {
		return IdentityEventAnswer.class;
	}

	@Override
	public Class<se.tink.core.models.identity.IdentityEventAnswer> getDestinationClass() {
		return se.tink.core.models.identity.IdentityEventAnswer.class;
	}
}
