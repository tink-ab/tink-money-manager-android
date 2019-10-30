package se.tink.converter.misc;

import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Field;
import se.tink.grpc.v1.models.ProviderFieldSpecification;
import se.tink.modelConverter.AbstractConverter;

public class FieldToFieldDTOConverter extends AbstractConverter<Field, ProviderFieldSpecification> {

	private ModelConverter modelConverter;

	public FieldToFieldDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public ProviderFieldSpecification convert(Field source) {
		ProviderFieldSpecification.Builder field = ProviderFieldSpecification.newBuilder();
		field.setDescription((source.getDescription()));
		field.setHelpText((source.getHelpText()));
		field.setHint((source.getHint()));
		field.setName((source.getName()));
		field.setValue((source.getValue()));
		field.setPattern((source.getPattern()));
		field.setPatternError((source.getPatternError()));

		field.setImmutable(source.isImmutable());
		field.setMasked(source.isMasked());
		field.setOptional(source.isOptional());
		field.setNumeric(source.isNumeric());

		if (source.getMaxLength() != null) {
			field.setMaxLength(modelConverter.map(source.getMaxLength(), Integer.class));
		}
		if (source.getMinLength() != null) {
			field.setMinLength(modelConverter.map(source.getMinLength(), Integer.class));
		}

		return field.build();
	}

	@Override
	public Class<Field> getSourceClass() {
		return Field.class;
	}

	@Override
	public Class<ProviderFieldSpecification> getDestinationClass() {
		return ProviderFieldSpecification.class;
	}

}
