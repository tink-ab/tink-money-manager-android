package se.tink.converter.provider

import se.tink.converter.EnumMappers
import se.tink.converter.ModelConverter
import se.tink.core.models.misc.Field
import se.tink.core.models.provider.Provider
import se.tink.grpc.v1.models.Images
import se.tink.grpc.v1.models.ProviderFieldSpecification
import se.tink.modelConverter.AbstractConverter

class ProviderToProviderDTOConverter(
    private val modelConverter: ModelConverter
) : AbstractConverter<Provider, ProviderDTO>() {

    override fun convert(source: Provider): ProviderDTO {
        return with(source) {
            val builder = ProviderDTO.newBuilder()
                .setName(name)
                .setDisplayName(displayName)
                .setType(type.toDTO())
                .setStatus(status.toDTO())
//        TODO        .setCredentialType(EnumMappers.GRPC_TO_MODEL_CREDENTIAL_TYPE_MAP.inverse()[credentialType])
                .setHelpText(helpText)
                .setPopular(isPopular)
                .setGroupDisplayName(groupDisplayName)
                .setDisplayDescription(name)
                .addAllFields(
                    modelConverter.map<Field, ProviderFieldSpecification>(
                        fields,
                        ProviderFieldSpecification::class.java
                    )
                )
                .setFinancialInstitutionId(financialInstitutionId)
                .setFinancialInstitutionName(financialInstitutionName)
                .setAccessType(accessType.toDTO())
            images?.let { images ->
                builder.setImages(
                    modelConverter.map<se.tink.core.models.Images, Images>(
                        images,
                        Images::class.java
                    )
                )
            }
            builder.build()
        }
    }

    //Enums might be re-added to the RPC later. That's why these remain.
    private fun Provider.Status.toDTO(): ProviderStatusDTO =
        when (this) {
            Provider.Status.STATUS_UNKNOWN -> ProviderStatusDTO.STATUS_UNKNOWN
            Provider.Status.STATUS_ENABLED -> ProviderStatusDTO.STATUS_ENABLED
            Provider.Status.STATUS_DISABLED -> ProviderStatusDTO.STATUS_DISABLED
            Provider.Status.STATUS_TEMPORARY_DISABLED -> ProviderStatusDTO.STATUS_TEMPORARY_DISABLED
            Provider.Status.STATUS_OBSOLETE -> ProviderStatusDTO.STATUS_OBSOLETE
        }

    private fun Provider.Type.toDTO(): ProviderTypeDTO =
        when (this) {
            Provider.Type.TYPE_UNKNOWN -> ProviderTypeDTO.TYPE_UNKNOWN
            Provider.Type.TYPE_BANK -> ProviderTypeDTO.TYPE_BANK
            Provider.Type.TYPE_CREDIT_CARD -> ProviderTypeDTO.TYPE_CREDIT_CARD
            Provider.Type.TYPE_BROKER -> ProviderTypeDTO.TYPE_BROKER
            Provider.Type.TYPE_OTHER -> ProviderTypeDTO.TYPE_OTHER
            Provider.Type.TYPE_TEST -> ProviderTypeDTO.TYPE_TEST
            Provider.Type.TYPE_FRAUD -> ProviderTypeDTO.TYPE_FRAUD
        }

    private fun Provider.AccessType.toDTO(): ProviderAccessTypeDTO =
        when (this) {
            Provider.AccessType.TYPE_UNKNOWN -> ProviderAccessTypeDTO.ACCESS_TYPE_UNKNOWN
            Provider.AccessType.TYPE_OPEN_BANKING -> ProviderAccessTypeDTO.ACCESS_TYPE_OPEN_BANKING
            Provider.AccessType.TYPE_OTHER -> ProviderAccessTypeDTO.ACCESS_TYPE_OTHER
        }
}