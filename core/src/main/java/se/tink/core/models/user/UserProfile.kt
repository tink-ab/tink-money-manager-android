package se.tink.core.models.user

import se.tink.core.models.device.AuthenticationMethod
import java.util.EnumSet

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

    class Builder(private val username: String, private val nationalId: String) {
        private var authorizedLoginMethods = EnumSet
            .noneOf(AuthenticationMethod::class.java)
        private var availableLoginMethods = EnumSet
            .noneOf(AuthenticationMethod::class.java)

        fun withAuthorizedLoginMethods(
            authorizedLoginMethods: List<AuthenticationMethod>
        ): Builder {
            if (authorizedLoginMethods.isEmpty()) {
                this.authorizedLoginMethods = EnumSet.noneOf(AuthenticationMethod::class.java)
            } else {
                this.authorizedLoginMethods = EnumSet.copyOf(authorizedLoginMethods)
            }
            return this
        }

        fun withAvailableLoginMethods(availableLoginMethods: List<AuthenticationMethod>): Builder {
            if (availableLoginMethods.isEmpty()) {
                this.authorizedLoginMethods = EnumSet.noneOf(AuthenticationMethod::class.java)
            } else {
                this.availableLoginMethods = EnumSet.copyOf(availableLoginMethods)
            }
            return this
        }

        fun build(): UserProfile {
            return UserProfile(
                username,
                nationalId,
                authorizedLoginMethods,
                availableLoginMethods
            )
        }
    }
}
