package se.tink.converter.authentication


import se.tink.core.models.authentication.ThirdPartyAppAuthentication
import se.tink.modelConverter.AbstractConverter

typealias ThirdPartyAppAuthenticationDTO = se.tink.grpc.v1.models.ThirdPartyAppAuthentication
typealias ThirdPartyAppAuthenticationAndroidDTO = se.tink.grpc.v1.models.ThirdPartyAppAuthentication.Android

class ThirdPartyAppAuthenticationDTOToThirdPartyAppAuthenticationConverter :
    AbstractConverter<ThirdPartyAppAuthenticationDTO, ThirdPartyAppAuthentication>() {

    override fun convert(source: ThirdPartyAppAuthenticationDTO): ThirdPartyAppAuthentication {
        return with(source) {
            ThirdPartyAppAuthentication(
                downloadMessage,
                downloadTitle,
                upgradeMessage,
                upgradeTitle,
                androidOrNull()
            )
        }
    }

    private fun ThirdPartyAppAuthenticationDTO.androidOrNull() =
        android.takeIf { hasAndroid() }?.toCoreModel()

    private fun ThirdPartyAppAuthenticationAndroidDTO.toCoreModel() =
        ThirdPartyAppAuthentication.Android(
            intent,
            packageName,
            requiredMinimumVersion
        )
}
