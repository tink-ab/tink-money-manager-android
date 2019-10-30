package se.tink.converter.identity;

import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityStateCreditScore;
import se.tink.modelConverter.AbstractConverter;

public class IdentityStateCreditScoreConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityStateCreditScore, IdentityStateCreditScore> {

	private final ModelConverterImpl modelConverter;

	public IdentityStateCreditScoreConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityStateCreditScore convert(
		se.tink.grpc.v1.models.IdentityStateCreditScore source) {
		IdentityStateCreditScore identityStateCreditScore = new IdentityStateCreditScore();
		identityStateCreditScore.setMaxScore(source.getMaxScore());
		identityStateCreditScore.setScore(source.getScore());
		identityStateCreditScore.setText(source.getText());

		return identityStateCreditScore;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityStateCreditScore> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityStateCreditScore.class;
	}

	@Override
	public Class<IdentityStateCreditScore> getDestinationClass() {
		return IdentityStateCreditScore.class;
	}
}
