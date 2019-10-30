package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateCompany;
import se.tink.core.models.identity.IdentityStateCompanyEngagement;
import se.tink.core.models.identity.IdentityStateRole;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateCompanyEngagementConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateCompanyEngagement, IdentityStateCompanyEngagement> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateCompanyEngagementConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateCompanyEngagement convert(
		se.tink.grpc.v1.models.IdentityStateCompanyEngagement source) {
		IdentityStateCompanyEngagement identityStateCompanyEngagement = new IdentityStateCompanyEngagement();

		identityStateCompanyEngagement.setRoles(
			modelConverter.map(source.getRolesList(),
				IdentityStateRole.class));

		identityStateCompanyEngagement
			.setCompany(modelConverter.map(source.getCompany(), IdentityStateCompany.class));
		identityStateCompanyEngagement
			.setDateIn(modelConverter.map(source.getDateIn(), DateTime.class));

		return identityStateCompanyEngagement;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateCompanyEngagement> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateCompanyEngagement.class;
	}

	@Override
	public Class<IdentityStateCompanyEngagement> getDestinationClass() {
		return IdentityStateCompanyEngagement.class;
	}
}
