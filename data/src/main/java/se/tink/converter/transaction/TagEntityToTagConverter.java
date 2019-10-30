package se.tink.converter.transaction;

import se.tink.core.models.transaction.Tag;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.TagEntity;

public class TagEntityToTagConverter extends AbstractConverter<TagEntity, Tag> {

	@Override
	public Tag convert(TagEntity source) {
		Tag destination = new Tag();
		destination.setName(source.getName());
		return destination;
	}

	@Override
	public Class<TagEntity> getSourceClass() {
		return TagEntity.class;
	}

	@Override
	public Class<Tag> getDestinationClass() {
		return Tag.class;
	}
}
