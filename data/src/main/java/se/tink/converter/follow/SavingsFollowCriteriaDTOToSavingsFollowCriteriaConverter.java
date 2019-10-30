package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.SavingsFollowCriteria;
import se.tink.modelConverter.AbstractConverter;

public class SavingsFollowCriteriaDTOToSavingsFollowCriteriaConverter extends
	AbstractConverter<SavingsFollowCriteria, se.tink.grpc.v1.models.SavingsFollowCriteria> {

	private final ModelConverter converter;

	public SavingsFollowCriteriaDTOToSavingsFollowCriteriaConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.SavingsFollowCriteria convert(SavingsFollowCriteria source) {
		se.tink.grpc.v1.models.SavingsFollowCriteria.Builder destination = se.tink.grpc.v1.models.SavingsFollowCriteria
			.newBuilder();
		if (source.getPeriod() != null) {
			destination.setPeriod(source.getPeriod());
		}
		if (source.getTargetAmount() != null) {
			destination.setTargetAmount(
				converter.map(source.getTargetAmount(), se.tink.grpc.v1.models.ExactNumber.class));
		}
		if (source.getAccountIds() != null) {
			destination.addAllAccountIds(converter.map(source.getAccountIds(), String.class));
		}
		return destination.build();
	}

	@Override
	public Class<SavingsFollowCriteria> getSourceClass() {
		return SavingsFollowCriteria.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.SavingsFollowCriteria> getDestinationClass() {
		return se.tink.grpc.v1.models.SavingsFollowCriteria.class;
	}
}