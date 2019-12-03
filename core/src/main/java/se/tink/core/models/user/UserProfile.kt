package se.tink.core.models.user

import se.tink.core.models.device.AuthenticationMethod

data class UserProfile(
    val username: String,
    private val nationalId: String,
    private val locale: String,
    private val timeZone: String,
    private val currency: String,
    private val authorizedLoginMethods: Set<AuthenticationMethod>,
    private val availableAuthenticationMethods: Set<AuthenticationMethod>
) {
    fun hasPassword(): Boolean {
        return authorizedLoginMethods
            .contains(AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD)
    }
}
