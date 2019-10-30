package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateRecordOfNonPayment;
import se.tink.core.models.misc.Amount;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateRecordOfNonPaymentConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateRecordOfNonPayment, IdentityStateRecordOfNonPayment> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateRecordOfNonPaymentConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateRecordOfNonPayment convert(
		se.tink.grpc.v1.models.IdentityStateRecordOfNonPayment source) {

		IdentityStateRecordOfNonPayment identityStateRecordOfNonPayment = new IdentityStateRecordOfNonPayment();
		identityStateRecordOfNonPayment
			.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		identityStateRecordOfNonPayment.setName(source.getName());
		identityStateRecordOfNonPayment
			.setRegisteredDate(modelConverter.map(source.getRegisteredDate(),
				DateTime.class));

		return identityStateRecordOfNonPayment;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateRecordOfNonPayment> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateRecordOfNonPayment.class;
	}

	@Override
	public Class<IdentityStateRecordOfNonPayment> getDestinationClass() {
		return IdentityStateRecordOfNonPayment.class;
	}
}
