package se.tink.converter.credential

import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.models.authentication.ThirdPartyAppAuthentication
import se.tink.core.models.credential.Credential
import se.tink.core.models.credential.Credential.Status
import se.tink.core.models.misc.Field
import se.tink.modelConverter.AbstractConverter
import java.util.ArrayList

class CredentialDTOToCredentialConverter(
    private val modelConverter: ModelConverter
) : AbstractConverter<CredentialDTO, Credential>() {

    override fun convert(source: CredentialDTO): Credential {
        return with(source) {
            Credential(
                providerName,
                type.toCoreModel(),
                mapFields(fieldsMap),
                id,
                status.toCoreModel(),
                statusPayload,
                if (hasSessionExpiryDate()) {
                    modelConverter.map(sessionExpiryDate, DateTime::class.java).millis
                } else {
                    null
                }
            ).also { credential ->
                if (supplementalInformationFieldsList != null && supplementalInformationFieldsCount > 0) {
                    credential.supplementalInformation =
                        modelConverter.map(supplementalInformationFieldsList, Field::class.java)
                }

                if (hasThirdPartyAppAuthentication()) {
                    credential.thirdPartyAppAuthentication = modelConverter.map(
                        thirdPartyAppAuthentication,
                        ThirdPartyAppAuthentication::class.java
                    )
                }
                if (hasStatusUpdated()) {
                    credential.statusUpdated = modelConverter.map(statusUpdated, DateTime::class.java).millis
                }
                if (hasUpdated()) {
                    credential.updated = modelConverter.map(updated, DateTime::class.java).millis
                }
            }
        }
    }

    private fun CredentialStatusDTO.toCoreModel(): Status =
        when (this) {
            CredentialStatusDTO.STATUS_CREATED -> Status.STATUS_CREATED
            CredentialStatusDTO.STATUS_AUTHENTICATING -> Status.STATUS_AUTHENTICATING
            CredentialStatusDTO.STATUS_UPDATING -> Status.STATUS_UPDATING
            CredentialStatusDTO.STATUS_UPDATED -> Status.STATUS_UPDATED
            CredentialStatusDTO.STATUS_TEMPORARY_ERROR -> Status.STATUS_TEMPORARY_ERROR
            CredentialStatusDTO.STATUS_AUTHENTICATION_ERROR -> Status.STATUS_AUTHENTICATION_ERROR
            CredentialStatusDTO.STATUS_PERMANENT_ERROR -> Status.STATUS_PERMANENT_ERROR
            CredentialStatusDTO.STATUS_AWAITING_SUPPLEMENTAL_INFORMATION -> Status.STATUS_AWAITING_SUPPLEMENTAL_INFORMATION
            CredentialStatusDTO.STATUS_DISABLED -> Status.STATUS_DISABLED
            CredentialStatusDTO.STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION -> Status.STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION
            // TODO: This will be updated in PFMF-1015
            CredentialStatusDTO.STATUS_AWAITING_MOBILE_BANKID_AUTHENTICATION -> Status.STATUS_AWAITING_MOBILE_BANKID_AUTHENTICATION
            CredentialStatusDTO.STATUS_SESSION_EXPIRED -> Status.STATUS_SESSION_EXPIRED
            else -> Status.STATUS_UNKNOWN
        }

    private fun CredentialTypeDTO.toCoreModel(): Credential.Type =
        when (this) {
            CredentialTypeDTO.TYPE_PASSWORD -> Credential.Type.TYPE_PASSWORD
            CredentialTypeDTO.TYPE_KEYFOB -> Credential.Type.TYPE_KEYFOB
            CredentialTypeDTO.TYPE_FRAUD -> Credential.Type.TYPE_FRAUD
            CredentialTypeDTO.TYPE_THIRD_PARTY_AUTHENTICATION -> Credential.Type.TYPE_THIRD_PARTY_AUTHENTICATION
            // TODO: This will be updated in PFMF-1015
            CredentialTypeDTO.TYPE_MOBILE_BANKID -> Credential.Type.TYPE_MOBILE_BANKID
            else -> Credential.Type.TYPE_UNKNOWN
        }

    private fun mapFields(fieldMap: Map<String, String>?): List<Field> {
        val fieldList = ArrayList<Field>()
        if (fieldMap.isNullOrEmpty()) {
            return fieldList
        }
        for ((entryKey, entryValue) in fieldMap) {
            fieldList.add(
                Field().apply {
                    name = entryKey
                    value = entryValue
                }
            )
        }
        return fieldList
    }
}
