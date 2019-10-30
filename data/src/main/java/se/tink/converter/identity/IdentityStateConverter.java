package se.tink.converter.identity;

import se.tink.converter.ModelConverter;
import se.tink.core.models.identity.IdentityState;
import se.tink.core.models.identity.IdentityStateAddress;
import se.tink.core.models.identity.IdentityStateCompanyEngagement;
import se.tink.core.models.identity.IdentityStateCreditScore;
import se.tink.core.models.identity.IdentityStateOutstandingDebt;
import se.tink.core.models.identity.IdentityStateProperty;
import se.tink.core.models.identity.IdentityStateRecordOfNonPayment;
import se.tink.core.models.identity.IdentityStateTaxDeclaration;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityState, IdentityState> {

	private final ModelConverter modelConverter;

	public IdentityStateConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityState convert(se.tink.grpc.v1.models.IdentityState source) {
		IdentityState identityState = new IdentityState();
		identityState
			.setAddress(modelConverter.map(source.getAddress(), IdentityStateAddress.class));
		identityState.setCompanyEngagements(
			modelConverter.map(
				source.getCompanyEngagementsList(), IdentityStateCompanyEngagement.class));
		identityState.setRecordsOfNonPayment(
			modelConverter.map(
				source.getRecordsOfNonPaymentList(),
				IdentityStateRecordOfNonPayment.class));
		identityState.setCreditScore(modelConverter.map(source.getCreditScore(),
			IdentityStateCreditScore.class));
		identityState.setFirstName(source.getFirstName());
		identityState.setLastName(source.getLastName());
		identityState
			.setMostRecentTaxDeclaration(modelConverter.map(source.getMostRecentTaxDeclaration(),
				IdentityStateTaxDeclaration.class));
		identityState.setOutstandingDebt(modelConverter.map(source.getOutstandingDebt(),
			IdentityStateOutstandingDebt.class));
		identityState.setPropertiesList(
			modelConverter.map(source.getPropertiesList(),
				IdentityStateProperty.class));
		identityState.setNationalId(source.getNationalId());
		return identityState;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityState> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityState.class;
	}

	@Override
	public Class<IdentityState> getDestinationClass() {
		return IdentityState.class;
	}
}
