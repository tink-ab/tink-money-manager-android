package se.tink.converter.follow;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.ExpensesFollowCriteria;
import se.tink.core.models.follow.FollowData;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.SavingsFollowCriteria;
import se.tink.core.models.follow.SearchFollowCriteria;
import se.tink.modelConverter.AbstractConverter;

public class FollowItemToFollowItemDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.FollowItem, FollowItem> {

	private final ModelConverter converter;

	public FollowItemToFollowItemDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public FollowItem convert(se.tink.grpc.v1.models.FollowItem source) {
		FollowItem destination = new FollowItem();
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.setCreatedDate(converter.map(source.getCreatedDate(), DateTime.class));
		if (source.hasData()) {
			destination.setData(converter.map(source.getData(), FollowData.class));
		}
		if (source.getSavingCriteria().getAccountIdsCount() > 0) {
			destination.setSavingCriteria(
				converter.map(source.getSavingCriteria(), SavingsFollowCriteria.class));
		}
		if (source.getExpensesCriteria().getCategoryCodesCount() > 0) {
			destination.setExpensesCriteria(
				converter.map(source.getExpensesCriteria(), ExpensesFollowCriteria.class));
		}
		if (Strings.isNullOrEmpty(source.getSearchCriteria().getQuery())) {
			destination.setSearchCriteria(
				converter.map(source.getSearchCriteria(), SearchFollowCriteria.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.FollowItem> getSourceClass() {
		return se.tink.grpc.v1.models.FollowItem.class;
	}

	@Override
	public Class<FollowItem> getDestinationClass() {
		return FollowItem.class;
	}

}
