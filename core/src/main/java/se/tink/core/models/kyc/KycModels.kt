package se.tink.core.models.kyc

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class KycQuestionReference(val reference: String) : Parcelable {
    TAX_IN_OTHER_COUNTRY("TAX_IN_OTHER_COUNTRY"),
    TAX_IN_COUNTRY("TAX_IN_COUNTRY"),
    IS_PEP("IS_PEP"),
    ON_OWN_BEHALF("ON_OWN_BEHALF")
}

enum class Answer { YES, NO }

data class KycCompliance(val provided: Boolean, val compliant: Boolean)

class KycAnswers(
    val compliant: Boolean,
    val map: Map<KycQuestionReference, Any?>
) : Map<KycQuestionReference, Any?> by map
