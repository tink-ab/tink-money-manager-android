package se.tink.converter.transaction;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Transaction;
import se.tink.grpc.v1.models.CurrencyDenominatedAmount;
import se.tink.modelConverter.AbstractConverter;

public class TransactionToTransactionDTOConverter extends
	AbstractConverter<Transaction, se.tink.grpc.v1.models.Transaction> {

	private final ModelConverter converter;

	public TransactionToTransactionDTOConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.Transaction convert(Transaction source) {
		se.tink.grpc.v1.models.Transaction.Builder destination = se.tink.grpc.v1.models.Transaction
			.newBuilder();
		destination.setAmount(converter.map(source.getAmount(), CurrencyDenominatedAmount.class));
		destination.setId(source.getId());
		destination.setAccountId(source.getAccountId());
		destination.setCategoryCode(source.getCategoryCode());
		destination.setDate(converter.map(source.getTimestamp(), Timestamp.class));
		destination.setDescription(source.getDescription());
		destination.setSecondaryDescription(source.getSecondaryDescription());
		destination.setNotes(source.getNotes());
		destination.setOriginalDate(converter.map(source.getOriginalTimestamp(), Timestamp.class));
		destination.setOriginalDescription(source.getOriginalDescription());
		destination.setPending(source.isPending());
		destination.setUpcoming(source.isUpcoming());

		return destination.build();
	}

	@Override
	public Class<Transaction> getSourceClass() {
		return Transaction.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Transaction> getDestinationClass() {
		return se.tink.grpc.v1.models.Transaction.class;
	}
}
