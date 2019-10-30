package se.tink.converter.misc;


import se.tink.core.models.Images;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ImagesEntity;

public class ImagesEntityToImagesConverter extends AbstractConverter<ImagesEntity, Images> {

	@Override
	public Images convert(ImagesEntity source) {
		Images destination = new Images();
		destination.setBanner(source.getBanner());
		destination.setIcon(source.getIcon());
		return destination;
	}

	@Override
	public Class<ImagesEntity> getSourceClass() {
		return ImagesEntity.class;
	}

	@Override
	public Class<Images> getDestinationClass() {
		return Images.class;
	}
}
