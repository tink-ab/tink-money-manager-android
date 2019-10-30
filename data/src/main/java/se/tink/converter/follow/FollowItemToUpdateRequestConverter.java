package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.grpc.v1.models.ExpensesFollowCriteria;
import se.tink.grpc.v1.models.SavingsFollowCriteria;
import se.tink.grpc.v1.models.SearchFollowCriteria;
import se.tink.grpc.v1.rpc.UpdateFollowItemRequest;
import se.tink.modelConverter.AbstractConverter;

public class FollowItemToUpdateRequestConverter extends
	AbstractConverter<FollowItem, UpdateFollowItemRequest> {

	private final ModelConverter converter;

	public FollowItemToUpdateRequestConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public UpdateFollowItemRequest convert(FollowItem source) {
		UpdateFollowItemRequest.Builder destination = UpdateFollowItemRequest.newBuilder();
		destination.setName(source.getName());
		if (source.getSavingCriteria() != null) {
			destination.setSavingCriteria(
				converter.map(source.getSavingCriteria(), SavingsFollowCriteria.class));
		}
		if (source.getExpensesCriteria() != null) {
			destination.setExpensesCriteria(
				converter.map(source.getExpensesCriteria(), ExpensesFollowCriteria.class));
		}
		if (source.getSearchCriteria() != null) {
			destination.setSearchCriteria(
				converter.map(source.getSearchCriteria(), SearchFollowCriteria.class));
		}
		destination.setFollowItemId(source.getId());
		return destination.build();
	}

	@Override
	public Class<FollowItem> getSourceClass() {
		return FollowItem.class;
	}

	@Override
	public Class<UpdateFollowItemRequest> getDestinationClass() {
		return UpdateFollowItemRequest.class;
	}
}