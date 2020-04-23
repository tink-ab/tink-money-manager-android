package se.tink.core.models.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime


@Parcelize
data class TinkApplication(
        val id: String,
        val forms: List<TinkApplicationForm>,
        val status: TinkApplicationStatus,
        val steps: Int,
        val title: String,
        val type: TinkApplicationType
) : Parcelable {
    constructor() : this("", listOf(), TinkApplicationStatus(), 0, "", TinkApplicationType.UNKNOWN)
}

@Parcelize
data class TinkApplicationForm(
        val id: String,
        val applicationId: String,
        val description: String,
        val fields: List<TinkApplicationFormField>,
        val status: TinkApplicationFormStatus,
        val type: String,
        val title: String,
        val serializedPayload: String,
        val name: String,
        //Calculated frontend side
        val index: Int,
        var isDependencyForm: Boolean
) : Parcelable {
    constructor() : this("", "", "", listOf(), TinkApplicationFormStatus(), "", "", "", "", 0, false)
}

@Parcelize
data class ApplicationFormInfo(val form: TinkApplicationForm, val stepsTotal: Int, val stepsCompleted: Int, val applicationType: String)
    : Parcelable


@Parcelize
data class TinkApplicationStatus(
        val key: TinkApplicationStatusKey,
        val message: String,
        val updated: DateTime
) : Parcelable {
    constructor() : this(TinkApplicationStatusKey.UNKNOWN, "", DateTime())
}

@Parcelize
data class TinkApplicationFormStatus(
        val key: TinkApplicationFormStatusKey,
        val message: String,
        val updated: DateTime
) : Parcelable {
    constructor() : this(TinkApplicationFormStatusKey.UNKNOWN, "", DateTime())
}

@Parcelize
data class TinkApplicationFormField(
        val defaultValue: String?,
        val description: String,
        val errors: List<String>,
        val displayError: String,
        val label: String,
        val name: String,
        val options: List<TinkApplicationFormFieldOption>,
        val pattern: String,
        val type: TinkApplicationFieldType,
        var value: String?,
        val required: Boolean,
        val readOnly: Boolean,
        val dependency: String,
        val infoTitle: String,
        val infoBody: String,
        val introduction: String
) : Parcelable {
    constructor() : this(null, "", listOf(), "", "", "", listOf(), "", TinkApplicationFieldType.UNKNOWN, null, false, false, "", "", "", "")
}

@Parcelize
data class TinkApplicationFormFieldOption(
        val value: String,
        val label: String,
        val description: String,
        val serializedPayload: String
) : Parcelable {
    constructor() : this("", "", "", "")
}

@Parcelize
data class TinkApplicationSummary(
        val description: String,
        val id: String,
        val imageUrl: String,
        val progress: Double,
        val provider: String,
        val status: TinkApplicationSummaryStatus,
        val title: String,
        val type: TinkApplicationType
) : Parcelable {
    constructor() : this("", "", "", 0.0, "", TinkApplicationSummaryStatus(), "", TinkApplicationType.UNKNOWN)
}

@Parcelize
data class TinkApplicationSummaryStatus(
        val key: TinkApplicationStatusKey,
        val body: String,
        val payload: String,
        val title: String
) : Parcelable {
    constructor() : this(TinkApplicationStatusKey.UNKNOWN, "", "", "")
}

enum class TinkApplicationFormStatusKey {
    UNKNOWN,
    CREATED,
    COMPLETED,
    IN_PROGRESS,
    ERROR,
    DISQUALIFIED,
    AUTO_SAVED,

}

enum class TinkApplicationFieldType {
    UNKNOWN,
    CHECKBOX,
    DATE,
    EMAIL,
    HIDDEN,
    MULTI_SELECT,
    NUMBER,
    NUMERIC,
    SELECT,
    SIGNATURE,
    TEXT,
}

enum class TinkApplicationType {
    UNKNOWN,
    SWITCH_MORTGAGE_PROVIDER,
    OPEN_SAVINGS_ACCOUNT,
    RESIDENCE_VALUATION,
    SOLICIT_UNSECURED_LOANS,
    ACCEPT_UNSECURED_LOANS
}

enum class TinkApplicationStatusKey {
    UNKNOWN,
    CREATED,
    IN_PROGRESS,
    DELETED,
    ERROR,
    EXPIRED,
    DISQUALIFIED,
    COMPLETED,
    SIGNED,
    SUPPLEMENTAL_INFORMATION_REQUIRED,
    REJECTED,
    APPROVED,
    ABORTED,
    EXECUTED,
    PENDING
}