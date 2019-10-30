package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateTaxDeclaration;
import se.tink.core.models.misc.Amount;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateTaxDeclarationConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateTaxDeclaration, IdentityStateTaxDeclaration> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateTaxDeclarationConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateTaxDeclaration convert(
		se.tink.grpc.v1.models.IdentityStateTaxDeclaration source) {
		IdentityStateTaxDeclaration identityStateTaxDeclaration = new IdentityStateTaxDeclaration();

		identityStateTaxDeclaration
			.setFinalTax(modelConverter.map(source.getFinalTax(), Amount.class));
		identityStateTaxDeclaration
			.setIncomeByCapital(modelConverter.map(source.getIncomeByCapital(), Amount.class));
		identityStateTaxDeclaration.setIncomeByService(modelConverter.map(
			source.getIncomeByService(), Amount.class));
		identityStateTaxDeclaration.setTotalIncome(modelConverter.map(
			source.getTotalIncome(), Amount.class));
		identityStateTaxDeclaration.setRegisteredDate(modelConverter.map(
			source.getRegisteredDate(), DateTime.class));
		identityStateTaxDeclaration.setYear(source.getYear());

		return identityStateTaxDeclaration;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateTaxDeclaration> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateTaxDeclaration.class;
	}

	@Override
	public Class<IdentityStateTaxDeclaration> getDestinationClass() {
		return IdentityStateTaxDeclaration.class;
	}
}
