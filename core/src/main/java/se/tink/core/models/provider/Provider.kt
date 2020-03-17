package se.tink.core.models.provider

import com.tink.model.credential.Credential
import se.tink.core.models.Images
import se.tink.core.models.misc.Field
import java.io.Serializable

data class Provider (
    val name: String,
    val displayName: String,
    val type: Type,
    val status: Status,
    val credentialType: Credential.Type,
    val helpText: String,
    val isPopular: Boolean = false,
    val fields: List<Field>,
    val groupDisplayName: String,
    val displayDescription: String,
    val images: Images? = null,
    val financialInstitutionId: String,
    val financialInstitutionName: String,
    val accessType: AccessType,
    val marketCode: String,
    val capabilities: List<Capability>,
    val authenticationFlow: AuthenticationFlow
) : Comparable<Provider>, Serializable {

    enum class Type {
        TYPE_UNKNOWN,
        TYPE_BANK,
        TYPE_CREDIT_CARD,
        TYPE_BROKER,
        TYPE_OTHER,
        TYPE_TEST,
        TYPE_FRAUD
    }

    enum class Status {
        STATUS_UNKNOWN,
        STATUS_ENABLED,
        STATUS_DISABLED,
        STATUS_TEMPORARY_DISABLED,
        STATUS_OBSOLETE
    }

    enum class AccessType {
        TYPE_UNKNOWN,
        TYPE_OPEN_BANKING,
        TYPE_OTHER
    }

    enum class Capability {
        CAPABILITY_UNKNOWN,
        CAPABILITY_TRANSFERS,
        CAPABILITY_MORTGAGE_AGGREGATION,
        CAPABILITY_CHECKING_ACCOUNTS,
        CAPABILITY_SAVINGS_ACCOUNTS,
        CAPABILITY_CREDIT_CARDS,
        CAPABILITY_INVESTMENTS,
        CAPABILITY_LOANS,
        CAPABILITY_PAYMENTS,
        CAPABILITY_MORTGAGE_LOAN,
        CAPABILITY_IDENTITY_DATA
    }

    enum class AuthenticationFlow {
        FLOW_UKNOWN,
        FLOW_EMBEDDED,
        FLOW_REDIRECT,
        FLOW_DECOUPLED
    }

    override fun compareTo(other: Provider): Int = name.compareTo(other.name)
}