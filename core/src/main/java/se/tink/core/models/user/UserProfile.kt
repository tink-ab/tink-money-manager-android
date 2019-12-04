package se.tink.core.models.user

import se.tink.core.models.device.AuthenticationMethod

data class UserProfile(
    val username: String,
    val nationalId: String,
    val locale: String,
    val timeZone: String,
    val currency: String,
    val authorizedLoginMethods: Set<AuthenticationMethod>,
    val availableAuthenticationMethods: Set<AuthenticationMethod>
) {
    fun hasPassword(): Boolean {
        return authorizedLoginMethods
            .contains(AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD)
    }
}
