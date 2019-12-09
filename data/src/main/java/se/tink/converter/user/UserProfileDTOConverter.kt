package se.tink.converter.user

import se.tink.core.models.device.AuthenticationMethod
import se.tink.core.models.user.UserProfile
import se.tink.modelConverter.AbstractConverter

typealias AuthenticationMethodDTO = se.tink.grpc.v1.models.AuthenticationMethod

class UserProfileDTOConverter :
    AbstractConverter<se.tink.grpc.v1.models.UserProfile, UserProfile>() {

    override fun convert(source: se.tink.grpc.v1.models.UserProfile) =
        UserProfile(
            username = source.username,
            nationalId = source.nationalId,
            locale = source.locale,
            market = source.market,
            timeZone = source.timeZone,
            currency = source.currency,
            authorizedLoginMethods = source.authorizedLoginMethodsList.map { it.mapAuthenticationMethod() }.toSet(),
            availableAuthenticationMethods = source.availableLoginMethodsList.map { it.mapAuthenticationMethod() }.toSet()
        )

    private fun AuthenticationMethodDTO.mapAuthenticationMethod() =
        when (this) {
            AuthenticationMethodDTO.AUTHENTICATION_METHOD_BANKID -> AuthenticationMethod.AUTHENTICATION_METHOD_BANKID
            AuthenticationMethodDTO.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD -> AuthenticationMethod.AUTHENTICATION_METHOD_EMAIL_AND_PASSWORD
            else -> AuthenticationMethod.AUTHENTICATION_METHOD_UNKNOWN
        }
}
