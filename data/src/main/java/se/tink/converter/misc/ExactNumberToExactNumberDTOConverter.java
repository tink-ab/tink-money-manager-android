package se.tink.converter.misc;

import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class ExactNumberToExactNumberDTOConverter extends
	AbstractConverter<ExactNumber, se.tink.grpc.v1.models.ExactNumber> {

	private ModelConverter modelConverter;

	public ExactNumberToExactNumberDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.ExactNumber convert(ExactNumber source) {
		se.tink.grpc.v1.models.ExactNumber.Builder builder = se.tink.grpc.v1.models.ExactNumber
			.newBuilder();
		builder.setUnscaledValue(source.getUnscaledValue());
		builder.setScale(source.getScale());

		return builder.build();
	}

	@Override
	public Class<ExactNumber> getSourceClass() {
		return ExactNumber.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ExactNumber> getDestinationClass() {
		return se.tink.grpc.v1.models.ExactNumber.class;
	}
}
