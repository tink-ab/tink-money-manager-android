package se.tink.converter.user

import java.util.LinkedList
import se.tink.converter.EnumMappers
import se.tink.core.models.device.AuthenticationMethod
import se.tink.core.models.user.UserProfile
import se.tink.modelConverter.AbstractConverter

class UserProfileDTOConverter :
    AbstractConverter<se.tink.grpc.v1.models.UserProfile, UserProfile>() {

    override fun convert(source: se.tink.grpc.v1.models.UserProfile): UserProfile {

        return UserProfile.Builder(source.username, source.nationalId)
            .withAuthorizedLoginMethods(mapAuthorizedAuthenticationMethods(source))
            .withAvailableLoginMethods(mapAvailableAuthenticationMethods(source))
            .build()
    }

    override fun getSourceClass(): Class<se.tink.grpc.v1.models.UserProfile> {
        return se.tink.grpc.v1.models.UserProfile::class.java
    }

    override fun getDestinationClass(): Class<UserProfile> {
        return UserProfile::class.java
    }

    private fun mapAuthorizedAuthenticationMethods(
        source: se.tink.grpc.v1.models.UserProfile
    ): List<AuthenticationMethod> {

        val authenticationMethods = LinkedList<AuthenticationMethod>()

        for (authenticationMethod in source
            .authorizedLoginMethodsList) {
            authenticationMethods
                .add(EnumMappers.AUTHENTICATION_METHOD_MAP[authenticationMethod]!!)
        }

        return authenticationMethods
    }

    private fun mapAvailableAuthenticationMethods(
        source: se.tink.grpc.v1.models.UserProfile
    ): List<AuthenticationMethod> {

        val authenticationMethods = LinkedList<AuthenticationMethod>()

        for (authenticationMethod in source
            .availableLoginMethodsList) {
            authenticationMethods
                .add(EnumMappers.AUTHENTICATION_METHOD_MAP[authenticationMethod]!!)
        }

        return authenticationMethods
    }

}
