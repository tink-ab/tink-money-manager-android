package se.tink.converter.provider

import se.tink.converter.EnumMappers
import se.tink.converter.ModelConverter
import se.tink.core.models.Images
import se.tink.core.models.credential.Credential
import se.tink.core.models.misc.Field
import se.tink.core.models.provider.Provider
import se.tink.modelConverter.AbstractConverter

class ProviderDTOToProviderConverter(
    private val modelConverter: ModelConverter
) : AbstractConverter<ProviderDTO, Provider>() {

    override fun convert(source: ProviderDTO): Provider {
        return with(source) {
            Provider(
                name = name,
                displayName = displayName,
                type = type.toCoreModel(),
                status = status.toCoreModel(),
                credentialType = EnumMappers.GRPC_TO_MODEL_CREDENTIAL_TYPE_MAP[credentialType]
                    ?: Credential.Type.TYPE_UNKNOWN,
                helpText = helpText,
                isPopular = popular,
                fields = fieldsOrEmpty(),
                groupDisplayName = groupDisplayName,
                displayDescription = displayDescription,
                images = images?.let {
                    modelConverter.map(images, Images::class.java)
                },
                financialInstitutionId = financialInstitutionId,
                financialInstitutionName = financialInstitutionName,
                accessType = accessType.toCoreModel(),
                marketCode = marketCode,
                capabilities = capabilitiesList
                    .map { it.toCoreModel() }
                    .distinct(),
                authenticationFlow = authenticationFlow.toCoreModel()
            )
        }
    }

    private fun ProviderStatusDTO.toCoreModel(): Provider.Status =
        when (this) {
            ProviderStatusDTO.UNRECOGNIZED,
            ProviderStatusDTO.STATUS_UNKNOWN -> Provider.Status.STATUS_UNKNOWN
            ProviderStatusDTO.STATUS_DISABLED -> Provider.Status.STATUS_DISABLED
            ProviderStatusDTO.STATUS_ENABLED -> Provider.Status.STATUS_ENABLED
            ProviderStatusDTO.STATUS_OBSOLETE -> Provider.Status.STATUS_OBSOLETE
            ProviderStatusDTO.STATUS_TEMPORARY_DISABLED -> Provider.Status.STATUS_TEMPORARY_DISABLED
        }


    private fun ProviderTypeDTO.toCoreModel(): Provider.Type =
        when (this) {
            ProviderTypeDTO.UNRECOGNIZED,
            ProviderTypeDTO.TYPE_UNKNOWN -> Provider.Type.TYPE_UNKNOWN
            ProviderTypeDTO.TYPE_BANK -> Provider.Type.TYPE_BANK
            ProviderTypeDTO.TYPE_BROKER -> Provider.Type.TYPE_BROKER
            ProviderTypeDTO.TYPE_CREDIT_CARD -> Provider.Type.TYPE_CREDIT_CARD
            ProviderTypeDTO.TYPE_FRAUD -> Provider.Type.TYPE_FRAUD
            ProviderTypeDTO.TYPE_OTHER -> Provider.Type.TYPE_OTHER
            ProviderTypeDTO.TYPE_TEST -> Provider.Type.TYPE_TEST
        }

    private fun ProviderDTO.fieldsOrEmpty(): List<Field> =
        fieldsList?.map { modelConverter.map(it, Field::class.java) }.orEmpty()

    private fun ProviderAccessTypeDTO.toCoreModel(): Provider.AccessType =
        when (this) {
            ProviderAccessTypeDTO.ACCESS_TYPE_OPEN_BANKING -> Provider.AccessType.TYPE_OPEN_BANKING
            else -> Provider.AccessType.TYPE_OTHER
        }

    private fun ProviderCapabilityDTO.toCoreModel(): Provider.Capability =
        when (this) {
            ProviderCapabilityDTO.UNRECOGNIZED,
            ProviderCapabilityDTO.CAPABILITY_EINVOICES,
            ProviderCapabilityDTO.CAPABILITY_UNKNOWN -> Provider.Capability.CAPABILITY_UNKNOWN
            ProviderCapabilityDTO.CAPABILITY_TRANSFERS -> Provider.Capability.CAPABILITY_TRANSFERS
            ProviderCapabilityDTO.CAPABILITY_MORTGAGE_AGGREGATION -> Provider.Capability.CAPABILITY_MORTGAGE_AGGREGATION
            ProviderCapabilityDTO.CAPABILITY_CHECKING_ACCOUNTS -> Provider.Capability.CAPABILITY_CHECKING_ACCOUNTS
            ProviderCapabilityDTO.CAPABILITY_SAVINGS_ACCOUNTS -> Provider.Capability.CAPABILITY_SAVINGS_ACCOUNTS
            ProviderCapabilityDTO.CAPABILITY_CREDIT_CARDS -> Provider.Capability.CAPABILITY_CREDIT_CARDS
            ProviderCapabilityDTO.CAPABILITY_INVESTMENTS -> Provider.Capability.CAPABILITY_INVESTMENTS
            ProviderCapabilityDTO.CAPABILITY_LOANS -> Provider.Capability.CAPABILITY_LOANS
            ProviderCapabilityDTO.CAPABILITY_PAYMENTS -> Provider.Capability.CAPABILITY_PAYMENTS
            ProviderCapabilityDTO.CAPABILITY_MORTGAGE_LOAN -> Provider.Capability.CAPABILITY_MORTGAGE_LOAN
            ProviderCapabilityDTO.CAPABILITY_IDENTITY_DATA -> Provider.Capability.CAPABILITY_IDENTITY_DATA
        }

    private fun ProviderAuthenticationFlowDTO.toCoreModel(): Provider.AuthenticationFlow =
        when (this) {
            ProviderAuthenticationFlowDTO.UNRECOGNIZED,
            ProviderAuthenticationFlowDTO.AUTHENTICATION_FLOW_UNKNOWN -> Provider.AuthenticationFlow.FLOW_UKNOWN
            ProviderAuthenticationFlowDTO.AUTHENTICATION_FLOW_EMBEDDED -> Provider.AuthenticationFlow.FLOW_EMBEDDED
            ProviderAuthenticationFlowDTO.AUTHENTICATION_FLOW_REDIRECT -> Provider.AuthenticationFlow.FLOW_REDIRECT
            ProviderAuthenticationFlowDTO.AUTHENTICATION_FLOW_DECOUPLED -> Provider.AuthenticationFlow.FLOW_DECOUPLED
        }
}
