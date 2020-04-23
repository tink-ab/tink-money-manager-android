package se.tink.converter.transaction;

import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Tag;
import se.tink.modelConverter.AbstractConverter;

public class TagToTagDTOConverter extends AbstractConverter<Tag, se.tink.grpc.v1.models.Tag> {

	private final ModelConverter converter;

	public TagToTagDTOConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.Tag convert(Tag source) {
		se.tink.grpc.v1.models.Tag.Builder destination = se.tink.grpc.v1.models.Tag.newBuilder();
		destination.setName(source.getName());
		return destination.build();
	}

	@Override
	public Class<Tag> getSourceClass() {
		return Tag.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Tag> getDestinationClass() {
		return se.tink.grpc.v1.models.Tag.class;
	}
}
