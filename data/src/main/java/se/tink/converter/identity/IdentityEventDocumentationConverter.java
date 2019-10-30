package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityEventDocumentation;
import se.tink.modelConverter.AbstractConverter;

public class IdentityEventDocumentationConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityEventDocumentation, IdentityEventDocumentation> {

	private final ModelConverterImpl converter;

	public IdentityEventDocumentationConverter(ModelConverterImpl modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public IdentityEventDocumentation convert(
		se.tink.grpc.v1.models.IdentityEventDocumentation source) {
		IdentityEventDocumentation identityEventDocumentation = new IdentityEventDocumentation();
		identityEventDocumentation.setHelpBody(source.getHelpBody());
		identityEventDocumentation.setHelpTitle(source.getHelpTitle());
		identityEventDocumentation.setInfoBody(source.getInfoBody());
		identityEventDocumentation.setInfoTitle(source.getInfoTitle());

		return identityEventDocumentation;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityEventDocumentation> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityEventDocumentation.class;
	}

	@Override
	public Class<IdentityEventDocumentation> getDestinationClass() {
		return IdentityEventDocumentation.class;
	}
}
