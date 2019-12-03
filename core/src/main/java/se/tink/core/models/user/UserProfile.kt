package se.tink.core.models.user

import se.tink.core.models.device.AuthenticationMethod

class UserProfile(
    val username: String,
    private val nationalId: String,
    private val authorizedLoginMethods: Set<AuthenticationMethod>,
    private val availableAuthenticationMethods: Set<AuthenticationMethod>
) {
    fun hasPassword(): Boolean {
        return authorizedLoginMethods
            .contains(AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD)
    }
}
