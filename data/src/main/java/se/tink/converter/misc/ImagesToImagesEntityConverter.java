package se.tink.converter.misc;


import se.tink.core.models.Images;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.ImagesEntity;

public class ImagesToImagesEntityConverter extends AbstractConverter<Images, ImagesEntity> {

	@Override
	public ImagesEntity convert(Images source) {
		ImagesEntity destination = new ImagesEntity();
		destination.setBanner(source.getBanner());
		destination.setIcon(source.getIcon());
		return destination;
	}

	@Override
	public Class<Images> getSourceClass() {
		return Images.class;
	}

	@Override
	public Class<ImagesEntity> getDestinationClass() {
		return ImagesEntity.class;
	}
}
