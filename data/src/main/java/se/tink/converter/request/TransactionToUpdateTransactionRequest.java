package se.tink.converter.request;

import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Transaction;
import se.tink.grpc.v1.rpc.UpdateTransactionRequest;
import se.tink.modelConverter.AbstractConverter;


public class TransactionToUpdateTransactionRequest extends
	AbstractConverter<Transaction, UpdateTransactionRequest> {

	private final ModelConverter converter;

	public TransactionToUpdateTransactionRequest(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public UpdateTransactionRequest convert(Transaction source) {
		UpdateTransactionRequest.Builder transactionRequest = UpdateTransactionRequest.newBuilder();
		transactionRequest.setTransactionId(source.getId());
		transactionRequest
			.setDescription(converter.map(source.getDescription(), StringValue.class));
		transactionRequest.setNotes(converter.map(source.getNotes(), StringValue.class));

		if (source.getTags() != null) {
			List<se.tink.grpc.v1.models.Tag> swaggerTags = converter
				.map(source.getTags(), se.tink.grpc.v1.models.Tag.class);
			transactionRequest.addAllTags(swaggerTags);
		}
		transactionRequest.setDate(converter.map(source.getTimestamp(), Timestamp.class));
		return transactionRequest.build();
	}

	@Override
	public Class<Transaction> getSourceClass() {
		return Transaction.class;
	}

	@Override
	public Class<UpdateTransactionRequest> getDestinationClass() {
		return UpdateTransactionRequest.class;
	}
}
