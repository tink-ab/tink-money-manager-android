package se.tink.converter.follow;


import java.util.List;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.ExpensesFollowCriteria;
import se.tink.core.models.follow.FollowData;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.core.models.follow.SavingsFollowCriteria;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.follow.FollowItemEntity;
import se.tink.repository.cache.models.follow.FollowItemWithRelations;

public class FollowItemEntityWithRelationsToFollowItemConverter extends
	AbstractConverter<FollowItemWithRelations, FollowItem> {

	private ModelConverter modelConverter;

	public FollowItemEntityWithRelationsToFollowItemConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public FollowItem convert(FollowItemWithRelations source) {

		FollowItemEntity itemEntity = source.getFollowItemEntity();
		FollowItem destination = new FollowItem();
		destination.setId(itemEntity.getId());
		destination.setName(itemEntity.getName());
		destination.setCreatedDate(new DateTime(source.getFollowItemEntity().getCreatedDate()));

		destination.setData(new FollowData());

		FollowData followData = new FollowData();

		List<PeriodExactNumberPair> historicalAmounts = modelConverter
			.map(source.getHistoricalAmounts(), PeriodExactNumberPair.class);
		followData.setHistoricalAmounts(historicalAmounts);

		if (itemEntity.getPeriod() != null) {
			Period period = modelConverter.map(itemEntity.getPeriod(), Period.class);
			followData.setPeriod(period);
		}
		destination.setData(followData);

		ExactNumber targetAmount = null;
		if (itemEntity.getTargetAmount() != null) {
			targetAmount = modelConverter.map(itemEntity.getTargetAmount(), ExactNumber.class);
		}

		if (source.getExpenseCriteriaCodes() != null
			&& source.getExpenseCriteriaCodes().size() > 0) {
			ExpensesFollowCriteria expensesFollowCriteria = new ExpensesFollowCriteria();
			expensesFollowCriteria.setCategoryCodes(source.getExpenseCriteriaCodes());
			expensesFollowCriteria.setTargetAmount(targetAmount);
			destination.setExpensesCriteria(expensesFollowCriteria);
		} else if (source.getSavingsAccountIds() != null
			&& source.getSavingsAccountIds().size() > 0) {
			SavingsFollowCriteria savingsFollowCriteria = new SavingsFollowCriteria();
			savingsFollowCriteria.setAccountIds(source.getSavingsAccountIds());
			savingsFollowCriteria.setTargetAmount(targetAmount);
			savingsFollowCriteria.setPeriod(itemEntity.getSavingsPeriod());
			destination.setSavingCriteria(savingsFollowCriteria);
		}

		return destination;
	}

	@Override
	public Class<FollowItemWithRelations> getSourceClass() {
		return FollowItemWithRelations.class;
	}

	@Override
	public Class<FollowItem> getDestinationClass() {
		return FollowItem.class;
	}
}
