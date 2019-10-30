package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateCompany;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateCompanyConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateCompany, IdentityStateCompany> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateCompanyConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateCompany convert(se.tink.grpc.v1.models.IdentityStateCompany source) {
		IdentityStateCompany identityStateCompany = new IdentityStateCompany();
		identityStateCompany.setName(source.getName());
		identityStateCompany.setNumber(source.getNumber());
		return identityStateCompany;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateCompany> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateCompany.class;
	}

	@Override
	public Class<IdentityStateCompany> getDestinationClass() {
		return IdentityStateCompany.class;
	}
}
