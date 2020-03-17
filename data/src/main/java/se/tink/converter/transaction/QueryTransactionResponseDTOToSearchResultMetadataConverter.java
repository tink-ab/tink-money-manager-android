package se.tink.converter.transaction;


import se.tink.converter.ModelConverter;
import com.tink.model.misc.Amount;
import se.tink.core.models.transaction.SearchResultMetadata;
import se.tink.grpc.v1.rpc.QueryTransactionsResponse;
import se.tink.modelConverter.AbstractConverter;

public class QueryTransactionResponseDTOToSearchResultMetadataConverter extends
	AbstractConverter<QueryTransactionsResponse, SearchResultMetadata> {

	private ModelConverter converter;

	public QueryTransactionResponseDTOToSearchResultMetadataConverter(
		ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public SearchResultMetadata convert(QueryTransactionsResponse source) {
		SearchResultMetadata destination = new SearchResultMetadata();
		destination.setTotalCount(source.getTotalCount());

		if (source.hasTotalNetAmount()) {
			Amount totalNetAmount = converter.map(source.getTotalNetAmount(), Amount.class);
			destination.setTotalNetAmount(totalNetAmount);
		}

		return destination;
	}

	@Override
	public Class<QueryTransactionsResponse> getSourceClass() {
		return QueryTransactionsResponse.class;
	}

	@Override
	public Class<SearchResultMetadata> getDestinationClass() {
		return SearchResultMetadata.class;
	}
}
