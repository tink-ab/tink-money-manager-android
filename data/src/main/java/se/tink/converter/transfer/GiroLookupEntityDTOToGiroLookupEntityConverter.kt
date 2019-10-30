package se.tink.converter.transfer

import se.tink.converter.ModelConverter
import se.tink.core.models.Images
import se.tink.core.models.transfer.GiroLookupEntity
import se.tink.modelConverter.AbstractConverter
import se.tink.grpc.v1.models.GiroLookupEntity as GiroLookupEntityDTO


class GiroLookupEntityDTOToGiroLookupEntityConverter(
        val converter: ModelConverter
) : AbstractConverter<GiroLookupEntityDTO, GiroLookupEntity>() {

    override fun convert(source: GiroLookupEntityDTO): GiroLookupEntity {
        return GiroLookupEntity().apply {
            identifier = source.identifier
            displayName = source.displayName
            displayNumber = source.displayNumber
            images = converter.map(source.images, Images::class.java)
        }
    }

    override fun getSourceClass(): Class<se.tink.grpc.v1.models.GiroLookupEntity> {
        return se.tink.grpc.v1.models.GiroLookupEntity::class.java
    }

    override fun getDestinationClass(): Class<GiroLookupEntity> {
        return GiroLookupEntity::class.java
    }
}
