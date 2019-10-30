package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateOutstandingDebt;
import se.tink.core.models.misc.Amount;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateOutstandingDebtConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateOutstandingDebt, IdentityStateOutstandingDebt> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateOutstandingDebtConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateOutstandingDebt convert(
		se.tink.grpc.v1.models.IdentityStateOutstandingDebt source) {
		IdentityStateOutstandingDebt destination = new IdentityStateOutstandingDebt();

		destination.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		destination.setNumber(source.getNumber());
		destination
			.setRegisteredDate(modelConverter.map(source.getRegisteredDate(), DateTime.class));

		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateOutstandingDebt> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateOutstandingDebt.class;
	}

	@Override
	public Class<IdentityStateOutstandingDebt> getDestinationClass() {
		return IdentityStateOutstandingDebt.class;
	}
}
