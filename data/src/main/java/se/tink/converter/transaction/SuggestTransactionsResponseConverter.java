package se.tink.converter.transaction;

import java.util.List;
import se.tink.converter.ModelConverter;
import com.tink.model.misc.ExactNumber;
import se.tink.core.models.transaction.SuggestTransactionsResponse;
import se.tink.core.models.transaction.TransactionCluster;
import se.tink.modelConverter.AbstractConverter;

public class SuggestTransactionsResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.SuggestTransactionsResponse, SuggestTransactionsResponse> {

	private final ModelConverter converter;

	public SuggestTransactionsResponseConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public SuggestTransactionsResponse convert(
		se.tink.grpc.v1.rpc.SuggestTransactionsResponse source) {
		SuggestTransactionsResponse destination = new SuggestTransactionsResponse();
		if (source.hasCategorizationImprovement()) {
			destination.setCategorizationImprovement(
				converter.map(source.getCategorizationImprovement(), ExactNumber.class));
		}
		if (source.hasCategorizationLevel()) {
			destination.setCategorizationLevel(
				converter.map(source.getCategorizationLevel(), ExactNumber.class));
		}
		if (source.getClustersList() != null) {
			List<TransactionCluster> clusters = converter
				.map(source.getClustersList(), TransactionCluster.class);
			destination.setClusters(clusters);
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.SuggestTransactionsResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.SuggestTransactionsResponse.class;
	}

	@Override
	public Class<SuggestTransactionsResponse> getDestinationClass() {
		return SuggestTransactionsResponse.class;
	}

}
