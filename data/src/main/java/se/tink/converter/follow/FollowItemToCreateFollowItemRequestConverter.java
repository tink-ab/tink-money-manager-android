package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.grpc.v1.models.ExpensesFollowCriteria;
import se.tink.grpc.v1.models.SavingsFollowCriteria;
import se.tink.grpc.v1.models.SearchFollowCriteria;
import se.tink.grpc.v1.rpc.CreateFollowItemRequest;
import se.tink.modelConverter.AbstractConverter;

public class FollowItemToCreateFollowItemRequestConverter extends
	AbstractConverter<FollowItem, CreateFollowItemRequest> {

	private final ModelConverter converter;

	public FollowItemToCreateFollowItemRequestConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public CreateFollowItemRequest convert(FollowItem source) {
		CreateFollowItemRequest.Builder destination = CreateFollowItemRequest.newBuilder();
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
		return destination.build();
	}

	@Override
	public Class<FollowItem> getSourceClass() {
		return FollowItem.class;
	}

	@Override
	public Class<CreateFollowItemRequest> getDestinationClass() {
		return CreateFollowItemRequest.class;
	}
}