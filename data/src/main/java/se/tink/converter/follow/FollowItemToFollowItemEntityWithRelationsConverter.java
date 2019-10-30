package se.tink.converter.follow;


import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ExactNumberEntity;
import se.tink.repository.cache.models.PeriodEntity;
import se.tink.repository.cache.models.follow.FollowHistoricalAmountEntity;
import se.tink.repository.cache.models.follow.FollowItemEntity;
import se.tink.repository.cache.models.follow.FollowItemWithRelations;

public class FollowItemToFollowItemEntityWithRelationsConverter extends
	AbstractConverter<FollowItem, FollowItemWithRelations> {

	private ModelConverter modelConverter;

	public FollowItemToFollowItemEntityWithRelationsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public FollowItemWithRelations convert(FollowItem source) {
		FollowItemWithRelations destination = new FollowItemWithRelations();

		FollowItemEntity followItemEntity = new FollowItemEntity();
		followItemEntity.setId(source.getId());
		followItemEntity.setName(source.getName());
		followItemEntity.setCreatedDate(source.getCreatedDate().getMillis());

		if (source.getData() != null && source.getData().getPeriod() != null) {
			PeriodEntity period = modelConverter
				.map(source.getData().getPeriod(), PeriodEntity.class);
			followItemEntity.setPeriod(period);
		}

		ExactNumber targetAmount = null;
		if (source.getExpensesCriteria() != null) {
			targetAmount = source.getExpensesCriteria().getTargetAmount();
			destination.setExpenseCriteriaCodes(source.getExpensesCriteria().getCategoryCodes());
		} else if (source.getSavingCriteria() != null) {
			targetAmount = source.getSavingCriteria().getTargetAmount();
			destination.setSavingsAccountIds(source.getSavingCriteria().getAccountIds());
			followItemEntity.setSavingsPeriod(source.getSavingCriteria().getPeriod());
		}
		if (targetAmount != null) {
			ExactNumberEntity targetAmountEntity = modelConverter
				.map(targetAmount, ExactNumberEntity.class);
			followItemEntity.setTargetAmount(targetAmountEntity);
		}

		if (source.getData() != null) {
			if (source.getData().getHistoricalAmounts() != null) {
				List<FollowHistoricalAmountEntity> historicalAmounts = modelConverter
					.map(source.getData().getHistoricalAmounts(), FollowHistoricalAmountEntity.class);
				for (FollowHistoricalAmountEntity followHistoricalAmountEntity : historicalAmounts) {
					followHistoricalAmountEntity.setFollowItemId(source.getId());
				}
				destination.setHistoricalAmounts(historicalAmounts);
			}
		}

		destination.setFollowItemEntity(followItemEntity);

		return destination;
	}

	@Override
	public Class<FollowItem> getSourceClass() {
		return FollowItem.class;
	}

	@Override
	public Class<FollowItemWithRelations> getDestinationClass() {
		return FollowItemWithRelations.class;
	}
}
