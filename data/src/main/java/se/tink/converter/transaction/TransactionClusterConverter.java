package se.tink.converter.transaction;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.transaction.TransactionCluster;
import se.tink.modelConverter.AbstractConverter;

public class TransactionClusterConverter extends
	AbstractConverter<se.tink.grpc.v1.models.TransactionCluster, TransactionCluster> {

	private final ModelConverter converter;

	public TransactionClusterConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public TransactionCluster convert(se.tink.grpc.v1.models.TransactionCluster source) {
		TransactionCluster destination = new TransactionCluster();
		if (source.hasCategorizationImprovement()) {
			destination.setCategorizationImprovement(
				converter.map(source.getCategorizationImprovement(), ExactNumber.class));
		}
		if (source.getDescription() != null) {
			destination.setDescription(source.getDescription());
		}
		if (source.hasScore()) {
			destination.setScore(converter.map(source.getScore(), ExactNumber.class));
		}
		if (source.getTransactionsList() != null) {
			List<Transaction> transactions = converter
				.map(source.getTransactionsList(), Transaction.class);
			destination.setTransactions(transactions);
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.TransactionCluster> getSourceClass() {
		return se.tink.grpc.v1.models.TransactionCluster.class;
	}

	@Override
	public Class<TransactionCluster> getDestinationClass() {
		return TransactionCluster.class;
	}

}
