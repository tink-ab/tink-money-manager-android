package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.ExpensesFollowCriteria;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class ExpensesFollowCriteriaToExpensesFollowCriteriaDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.ExpensesFollowCriteria, ExpensesFollowCriteria> {

	private final ModelConverter converter;

	public ExpensesFollowCriteriaToExpensesFollowCriteriaDTOConverter(
		ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public ExpensesFollowCriteria convert(se.tink.grpc.v1.models.ExpensesFollowCriteria source) {
		ExpensesFollowCriteria destination = new ExpensesFollowCriteria();

		if (source.hasTargetAmount()) {
			destination.setTargetAmount(converter.map(source.getTargetAmount(), ExactNumber.class));
		} else {
			destination.setTargetAmount(null);
		}

		if (source.getCategoryCodesList() != null) {
			//noinspection unchecked
			destination.setCategoryCodes(converter
				.map(source.getCategoryCodesList(), String.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ExpensesFollowCriteria> getSourceClass() {
		return se.tink.grpc.v1.models.ExpensesFollowCriteria.class;
	}

	@Override
	public Class<ExpensesFollowCriteria> getDestinationClass() {
		return ExpensesFollowCriteria.class;
	}

}
