package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.ExpensesFollowCriteria;
import se.tink.modelConverter.AbstractConverter;

public class ExpensesFollowCriteriaDTOToExpensesFollowCriteriaConverter extends
	AbstractConverter<ExpensesFollowCriteria, se.tink.grpc.v1.models.ExpensesFollowCriteria> {

	private final ModelConverter converter;

	public ExpensesFollowCriteriaDTOToExpensesFollowCriteriaConverter(
		ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.ExpensesFollowCriteria convert(ExpensesFollowCriteria source) {
		se.tink.grpc.v1.models.ExpensesFollowCriteria.Builder destination = se.tink.grpc.v1.models.ExpensesFollowCriteria
			.newBuilder();
		if (source.getTargetAmount() != null) {
			destination.setTargetAmount(converter.map(source.getTargetAmount(),
				se.tink.grpc.v1.models.ExactNumber.class));
		}
		if (source.getCategoryCodes() != null) {
			destination.addAllCategoryCodes(converter
				.map(source.getCategoryCodes(), String.class));
		}
		return destination.build();
	}

	@Override
	public Class<ExpensesFollowCriteria> getSourceClass() {
		return ExpensesFollowCriteria.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ExpensesFollowCriteria> getDestinationClass() {
		return se.tink.grpc.v1.models.ExpensesFollowCriteria.class;
	}
}