package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.SearchFollowCriteria;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class SearchFollowCriteriaToSearchFollowCriteriaDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.SearchFollowCriteria, SearchFollowCriteria> {

	private final ModelConverter converter;

	public SearchFollowCriteriaToSearchFollowCriteriaDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public SearchFollowCriteria convert(se.tink.grpc.v1.models.SearchFollowCriteria source) {
		SearchFollowCriteria destination = new SearchFollowCriteria();
		destination.setQuery(source.getQuery());
		if (source.getTargetAmount() != null) {
			destination.setTargetAmount(converter.map(source.getTargetAmount(), ExactNumber.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.SearchFollowCriteria> getSourceClass() {
		return se.tink.grpc.v1.models.SearchFollowCriteria.class;
	}

	@Override
	public Class<SearchFollowCriteria> getDestinationClass() {
		return SearchFollowCriteria.class;
	}

}
