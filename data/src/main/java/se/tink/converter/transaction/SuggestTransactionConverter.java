package se.tink.converter.transaction;

import java.util.List;
import se.tink.converter.ModelConverter;
import com.tink.model.misc.ExactNumber;
import se.tink.core.models.transaction.TransactionCluster;
import se.tink.grpc.v1.rpc.SuggestTransactionsResponse;
import se.tink.modelConverter.AbstractConverter;

public class SuggestTransactionConverter extends
	AbstractConverter<SuggestTransactionsResponse, se.tink.core.models.transaction.SuggestTransactionsResponse> {

	private ModelConverter modelConverter;

	public SuggestTransactionConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.core.models.transaction.SuggestTransactionsResponse convert(
		SuggestTransactionsResponse source) {
		se.tink.core.models.transaction.SuggestTransactionsResponse destination = new se.tink.core.models.transaction.SuggestTransactionsResponse();

		destination.setCategorizationImprovement(
			modelConverter.map(source.getCategorizationImprovement(), ExactNumber.class));
		destination.setCategorizationLevel(
			modelConverter.map(source.getCategorizationLevel(), ExactNumber.class));

		List<TransactionCluster> clusters = modelConverter
			.map(source.getClustersList(), TransactionCluster.class);
		destination.setClusters(clusters);

		return destination;
	}

	@Override
	public Class<SuggestTransactionsResponse> getSourceClass() {
		return SuggestTransactionsResponse.class;
	}

	@Override
	public Class<se.tink.core.models.transaction.SuggestTransactionsResponse> getDestinationClass() {
		return se.tink.core.models.transaction.SuggestTransactionsResponse.class;
	}
}