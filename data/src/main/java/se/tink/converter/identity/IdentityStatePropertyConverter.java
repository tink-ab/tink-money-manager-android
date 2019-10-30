package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateProperty;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStatePropertyConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateProperty, IdentityStateProperty> {

	private final ModelConverterImpl modelConverter;

	public IdentityStatePropertyConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateProperty convert(se.tink.grpc.v1.models.IdentityStateProperty source) {
		IdentityStateProperty identityStateProperty = new IdentityStateProperty();
		identityStateProperty
			.setAcquisitionDate(modelConverter.map(source.getAcquisitionDate(), DateTime.class));
		identityStateProperty.setMunicipality(source.getMunicipality());
		identityStateProperty.setName(source.getName());
		identityStateProperty.setNumber(source.getNumber());

		return identityStateProperty;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateProperty> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateProperty.class;
	}

	@Override
	public Class<IdentityStateProperty> getDestinationClass() {
		return IdentityStateProperty.class;
	}
}
