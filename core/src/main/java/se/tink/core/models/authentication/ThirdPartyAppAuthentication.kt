package se.tink.core.models.authentication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ThirdPartyAppAuthentication(
    val downloadMessage: String,
    val downloadTitle: String,
    val upgradeMessage: String,
    val upgradeTitle: String,
    val android: Android? = null
) : Parcelable {

    @Parcelize
    data class Android(
        val intent: String,
        val packageName: String,
        val requiredMinimumVersion: Int
    ) : Parcelable
}
