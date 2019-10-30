package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateAddress;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateAddressConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateAddress, IdentityStateAddress> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateAddressConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateAddress convert(se.tink.grpc.v1.models.IdentityStateAddress source) {
		IdentityStateAddress identityStateAddress = new IdentityStateAddress();

		identityStateAddress.setCity(source.getCity());
		identityStateAddress.setCommunity(source.getCommunity());
		identityStateAddress.setName(source.getName());
		identityStateAddress.setPostalCode(source.getPostalCode());

		return identityStateAddress;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateAddress> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateAddress.class;
	}

	@Override
	public Class<IdentityStateAddress> getDestinationClass() {
		return IdentityStateAddress.class;
	}
}
