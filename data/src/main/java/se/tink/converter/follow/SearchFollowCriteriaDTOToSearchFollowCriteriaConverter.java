package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.SearchFollowCriteria;
import se.tink.modelConverter.AbstractConverter;

public class SearchFollowCriteriaDTOToSearchFollowCriteriaConverter extends
	AbstractConverter<SearchFollowCriteria, se.tink.grpc.v1.models.SearchFollowCriteria> {

	private final ModelConverter converter;

	public SearchFollowCriteriaDTOToSearchFollowCriteriaConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.SearchFollowCriteria convert(SearchFollowCriteria source) {
		se.tink.grpc.v1.models.SearchFollowCriteria.Builder destination = se.tink.grpc.v1.models.SearchFollowCriteria
			.newBuilder();
		destination.setQuery(source.getQuery());
		if (source.getTargetAmount() != null) {
			destination.setTargetAmount(
				converter.map(source.getTargetAmount(), se.tink.grpc.v1.models.ExactNumber.class));
		}
		return destination.build();
	}

	@Override
	public Class<SearchFollowCriteria> getSourceClass() {
		return SearchFollowCriteria.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.SearchFollowCriteria> getDestinationClass() {
		return se.tink.grpc.v1.models.SearchFollowCriteria.class;
	}
}