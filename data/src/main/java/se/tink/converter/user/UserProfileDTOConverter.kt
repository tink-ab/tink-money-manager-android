package se.tink.converter.user

import se.tink.converter.EnumMappers
import se.tink.core.models.device.AuthenticationMethod
import se.tink.core.models.user.UserProfile
import se.tink.modelConverter.AbstractConverter

class UserProfileDTOConverter :
    AbstractConverter<se.tink.grpc.v1.models.UserProfile, UserProfile>() {

    override fun convert(source: se.tink.grpc.v1.models.UserProfile) =
        UserProfile(
            username = source.username,
            nationalId = source.nationalId,
            authorizedLoginMethods = mapAuthorizedAuthenticationMethods(source),
            availableAuthenticationMethods = mapAvailableAuthenticationMethods(source)
        )


    private fun mapAuthorizedAuthenticationMethods(
        source: se.tink.grpc.v1.models.UserProfile
    ): Set<AuthenticationMethod> {

        val authenticationMethods = mutableSetOf<AuthenticationMethod>()

        for (authenticationMethod in source
            .authorizedLoginMethodsList) {
            authenticationMethods
                .add(EnumMappers.AUTHENTICATION_METHOD_MAP[authenticationMethod]!!)
        }

        return authenticationMethods
    }

    private fun mapAvailableAuthenticationMethods(
        source: se.tink.grpc.v1.models.UserProfile
    ): Set<AuthenticationMethod> {

        val authenticationMethods = mutableSetOf<AuthenticationMethod>()

        for (authenticationMethod in source
            .availableLoginMethodsList) {
            authenticationMethods
                .add(EnumMappers.AUTHENTICATION_METHOD_MAP[authenticationMethod]!!)
        }

        return authenticationMethods
    }
}
