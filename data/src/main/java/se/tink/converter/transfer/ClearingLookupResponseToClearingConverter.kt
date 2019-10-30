package se.tink.converter.transfer

import se.tink.converter.ModelConverter
import se.tink.core.models.Images
import se.tink.core.models.transfer.Clearing
import se.tink.grpc.v1.rpc.ClearingLookupResponse
import se.tink.modelConverter.AbstractConverter


class ClearingLookupResponseToClearingConverter(val modelConverter: ModelConverter) :
    AbstractConverter<ClearingLookupResponse, Clearing>() {
    override fun convert(source: ClearingLookupResponse): Clearing {
        return Clearing().apply {
            bankDisplayName = source.bankDisplayName
            images = source.takeIf { it.hasImages() }?.images?.let {
                modelConverter.map(it, Images::class.java)
            }
        }
    }
}
