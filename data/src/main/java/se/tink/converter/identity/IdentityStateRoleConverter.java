package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateRole;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateRoleConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateRole, IdentityStateRole> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateRoleConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateRole convert(se.tink.grpc.v1.models.IdentityStateRole source) {
		IdentityStateRole identityStateRole = new IdentityStateRole();
		identityStateRole.setName(source.getName());

		return identityStateRole;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateRole> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateRole.class;
	}

	@Override
	public Class<IdentityStateRole> getDestinationClass() {
		return IdentityStateRole.class;
	}
}
