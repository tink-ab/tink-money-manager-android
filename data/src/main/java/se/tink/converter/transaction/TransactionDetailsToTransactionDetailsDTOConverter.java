package se.tink.converter.transaction;

import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.TransactionDetails;
import se.tink.grpc.v1.models.Transaction;
import se.tink.modelConverter.AbstractConverter;

public class TransactionDetailsToTransactionDetailsDTOConverter extends
	AbstractConverter<TransactionDetails, Transaction.TransactionDetails> {

	private ModelConverter modelConverter;

	public TransactionDetailsToTransactionDetailsDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Transaction.TransactionDetails convert(TransactionDetails source) {
		Transaction.TransactionDetails.Builder destination = Transaction.TransactionDetails
			.newBuilder();
		destination.setTransferId(source.getTransferId());
		return destination.build();
	}

	@Override
	public Class<TransactionDetails> getSourceClass() {
		return TransactionDetails.class;
	}

	@Override
	public Class<Transaction.TransactionDetails> getDestinationClass() {
		return Transaction.TransactionDetails.class;
	}
}
