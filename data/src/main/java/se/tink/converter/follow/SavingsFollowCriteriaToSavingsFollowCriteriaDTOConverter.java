package se.tink.converter.follow;

import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.SavingsFollowCriteria;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class SavingsFollowCriteriaToSavingsFollowCriteriaDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.SavingsFollowCriteria, SavingsFollowCriteria> {

	private final ModelConverter converter;

	public SavingsFollowCriteriaToSavingsFollowCriteriaDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public SavingsFollowCriteria convert(se.tink.grpc.v1.models.SavingsFollowCriteria source) {
		SavingsFollowCriteria destination = new SavingsFollowCriteria();
		destination.setPeriod(source.getPeriod());
		if (source.hasTargetAmount()) {
			destination.setTargetAmount(converter.map(source.getTargetAmount(), ExactNumber.class));
		}
		if (source.getAccountIdsList() != null) {
			destination.setAccountIds(source.getAccountIdsList());
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.SavingsFollowCriteria> getSourceClass() {
		return se.tink.grpc.v1.models.SavingsFollowCriteria.class;
	}

	@Override
	public Class<SavingsFollowCriteria> getDestinationClass() {
		return SavingsFollowCriteria.class;
	}

}
