package se.tink.converter.transaction;

import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Tag;
import se.tink.modelConverter.AbstractConverter;

public class TagDTOToTagConverter extends AbstractConverter<se.tink.grpc.v1.models.Tag, Tag> {

	private final ModelConverter converter;

	public TagDTOToTagConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public Tag convert(se.tink.grpc.v1.models.Tag source) {
		Tag destination = new Tag();
		destination.setName(source.getName());
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Tag> getSourceClass() {
		return se.tink.grpc.v1.models.Tag.class;
	}

	@Override
	public Class<Tag> getDestinationClass() {
		return Tag.class;
	}
}
