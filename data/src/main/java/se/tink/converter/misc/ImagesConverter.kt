package se.tink.converter.misc

import se.tink.core.models.Images
import se.tink.modelConverter.AbstractConverter

class ImagesConverter : AbstractConverter<se.tink.grpc.v1.models.Images, Images>() {

    override fun convert(source: se.tink.grpc.v1.models.Images): Images {
        val destination = Images()
        destination.banner = source.bannerUrl
        destination.icon = source.iconUrl
        return destination
    }

    override fun getSourceClass(): Class<se.tink.grpc.v1.models.Images> {
        return se.tink.grpc.v1.models.Images::class.java
    }

    override fun getDestinationClass(): Class<Images> {
        return Images::class.java
    }
}
