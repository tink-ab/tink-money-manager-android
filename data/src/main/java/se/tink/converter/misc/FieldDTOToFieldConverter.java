package se.tink.converter.misc;

import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Field;
import se.tink.grpc.v1.models.ProviderFieldSpecification;
import se.tink.modelConverter.AbstractConverter;

public class FieldDTOToFieldConverter extends AbstractConverter<ProviderFieldSpecification, Field> {

	private ModelConverter modelConverter;

	public FieldDTOToFieldConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Field convert(ProviderFieldSpecification source) {
		Field field = new Field();
		field.setDescription((source.getDescription()));
		field.setHelpText((source.getHelpText()));
		field.setHint((source.getHint()));
		field.setName((source.getName()));
		field.setValue((source.getValue()));
		field.setPattern((source.getPattern()));
		field.setPatternError((source.getPatternError()));
		field.setImmutable(source.getImmutable());
		field.setMasked(source.getMasked());
		field.setOptional(source.getOptional());
		field.setNumeric(source.getNumeric());
		field.setMaxLength(source.getMaxLength());
		field.setMinLength(source.getMinLength());

		return field;
	}

	@Override
	public Class<ProviderFieldSpecification> getSourceClass() {
		return ProviderFieldSpecification.class;
	}

	@Override
	public Class<Field> getDestinationClass() {
		return Field.class;
	}
}
