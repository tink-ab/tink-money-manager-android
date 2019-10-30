package se.tink.converter.follow;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.modelConverter.AbstractConverter;

public class FollowItemDTOToFollowItemConverter extends
	AbstractConverter<FollowItem, se.tink.grpc.v1.models.FollowItem> {

	private final ModelConverter converter;

	public FollowItemDTOToFollowItemConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.FollowItem convert(FollowItem source) {
		se.tink.grpc.v1.models.FollowItem.Builder destination = se.tink.grpc.v1.models.FollowItem
			.newBuilder();
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.setCreatedDate(converter.map(source.getCreatedDate(), Timestamp.class));
		if (source.getData() != null) {
			destination
				.setData(converter.map(source.getData(), se.tink.grpc.v1.models.FollowData.class));
		}
		if (source.getSavingCriteria() != null) {
			destination.setSavingCriteria(
				converter.map(source.getSavingCriteria(),
					se.tink.grpc.v1.models.SavingsFollowCriteria.class));
		}
		if (source.getExpensesCriteria() != null) {
			destination.setExpensesCriteria(converter.map(source.getExpensesCriteria(),
				se.tink.grpc.v1.models.ExpensesFollowCriteria.class));
		}
		if (source.getSearchCriteria() != null) {
			destination.setSearchCriteria(converter.map(source.getSearchCriteria(),
				se.tink.grpc.v1.models.SearchFollowCriteria.class));
		}
		return destination.build();
	}

	@Override
	public Class<FollowItem> getSourceClass() {
		return FollowItem.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.FollowItem> getDestinationClass() {
		return se.tink.grpc.v1.models.FollowItem.class;
	}
}