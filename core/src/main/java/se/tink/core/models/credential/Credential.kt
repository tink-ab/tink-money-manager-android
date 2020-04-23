package se.tink.core.models.credential

import android.os.Parcel
import android.os.Parcelable
import se.tink.core.extensions.CredentialSessionExpiryDate
import se.tink.core.models.authentication.ThirdPartyAppAuthentication
import se.tink.core.models.misc.Field

data class Credential(
    val providerName: String,
    val type: Type,
    val fields: List<Field>,
    val id: String? = null,
    val status: Status? = null,
    val statusPayload: String? = null,
    val sessionExpiryDate: CredentialSessionExpiryDate? = null
) : Comparable<Credential>, Parcelable {

    var statusUpdated: Long = 0L
    var updated: Long = 0L
    var supplementalInformation: List<Field> = emptyList()
    val supplementalInformationMap: Map<String, String>
        get() = supplementalInformation
            .takeIf { it.isNotEmpty() }
            ?.map { it.name to it.value }
            ?.toMap()
            ?: emptyMap()
    var thirdPartyAppAuthentication: ThirdPartyAppAuthentication? = null


    enum class Type {
        TYPE_UNKNOWN,
        TYPE_PASSWORD,
        // TODO: This will be updated in PFMF-1015
        TYPE_MOBILE_BANKID,
        TYPE_THIRD_PARTY_AUTHENTICATION,
        TYPE_KEYFOB,
        TYPE_FRAUD,
        UNRECOGNIZED
    }

    enum class Status {
        STATUS_UNKNOWN,
        STATUS_CREATED,
        STATUS_AUTHENTICATING,
        STATUS_UPDATING,
        STATUS_UPDATED,
        STATUS_TEMPORARY_ERROR,
        STATUS_AUTHENTICATION_ERROR,
        STATUS_PERMANENT_ERROR,
        // TODO: This will be updated in PFMF-1015
        STATUS_AWAITING_MOBILE_BANKID_AUTHENTICATION,
        STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION,
        STATUS_AWAITING_SUPPLEMENTAL_INFORMATION,
        STATUS_SESSION_EXPIRED,
        STATUS_DISABLED
    }

    fun fieldsMap(): Map<String, String> = fields.map { it.name to it.value }.toMap()

    fun isNew(): Boolean = id == null

    override fun compareTo(other: Credential): Int = providerName.compareTo(other.providerName)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(providerName)
            writeSerializable(type)
            writeList(fields)
            writeString(id)
            writeSerializable(status)
            writeString(statusPayload)
            writeLong(statusUpdated)
            writeLong(updated)
            sessionExpiryDate?.let { writeLong(it) } ?: writeLong(0L)
            writeList(supplementalInformation)
            thirdPartyAppAuthentication?.let { writeParcelable(it, flags) }
        }
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Credential> {
        override fun createFromParcel(parcel: Parcel): Credential {
            return readFromParcel(parcel)
        }

        private fun readFromParcel(parcel: Parcel): Credential {
            return with(parcel) {
                Credential(
                    providerName = readString() ?: "",
                    type = readSerializable() as Type,
                    fields = mutableListOf<Field>().also {
                        parcel.readList(it, Field::class.java.classLoader)
                    },
                    id = readString(),
                    status = readSerializable() as? Status,
                    statusPayload = readString(),
                    sessionExpiryDate = readLong().takeIf { it != 0L }
                ).apply {
                    statusUpdated = readLong()
                    updated = readLong()
                    supplementalInformation = mutableListOf<Field>().also {
                        parcel.readList(it, Field::class.java.classLoader)
                    }
                    thirdPartyAppAuthentication =
                        readParcelable(ThirdPartyAppAuthentication::class.java.classLoader)
                }
            }
        }

        override fun newArray(size: Int): Array<Credential?> {
            return arrayOfNulls(size)
        }
    }
}
