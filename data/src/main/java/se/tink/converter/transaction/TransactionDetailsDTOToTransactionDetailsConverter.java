package se.tink.converter.transaction;

import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.TransactionDetails;
import se.tink.grpc.v1.models.Transaction;
import se.tink.modelConverter.AbstractConverter;

public class TransactionDetailsDTOToTransactionDetailsConverter extends
	AbstractConverter<Transaction.TransactionDetails, TransactionDetails> {

	private ModelConverter modelConverter;

	public TransactionDetailsDTOToTransactionDetailsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public TransactionDetails convert(Transaction.TransactionDetails source) {
		TransactionDetails details = new TransactionDetails();
		details.setTransferId(source.getTransferId());
		return details;
	}

	@Override
	public Class<Transaction.TransactionDetails> getSourceClass() {
		return Transaction.TransactionDetails.class;
	}

	@Override
	public Class<TransactionDetails> getDestinationClass() {
		return TransactionDetails.class;
	}
}
