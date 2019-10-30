package se.tink.converter.credential

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import se.tink.converter.ModelConverterImpl
import se.tink.core.models.credential.Credential

internal class CredentialConverterTest {
    private val converter = ModelConverterImpl()

    private val credentialDTO = CredentialDTO.newBuilder()
        .setId("123456abcdef")
        .setProviderName("Test Provider")
        .setType(CredentialTypeDTO.TYPE_THIRD_PARTY_AUTHENTICATION)
        .build()

    private val credentialDTOWithUnknownType = CredentialDTO.newBuilder()
        .setId("123456abcdef")
        .setProviderName("Test Provider")
        .setType(CredentialTypeDTO.TYPE_UNKNOWN)
        .build()

    private val credentialDTOWithValidStatus = CredentialDTO.newBuilder()
        .setId("123456abcdef")
        .setProviderName("Test Provider")
        .setType(CredentialTypeDTO.TYPE_THIRD_PARTY_AUTHENTICATION)
        .setStatus(CredentialStatusDTO.STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION)
        .build()

    private val credentialDTOWithUnknownStatus = CredentialDTO.newBuilder()
        .setId("123456abcdef")
        .setProviderName("Test Provider")
        .setType(CredentialTypeDTO.TYPE_THIRD_PARTY_AUTHENTICATION)
        .setStatus(CredentialStatusDTO.STATUS_UNKNOWN)
        .build()

    @Test
    fun `DTO default instance conversion does not crash`() {
        converter.map(CredentialDTO.getDefaultInstance(), Credential::class.java)
    }

    @Test
    fun `DTO instance with nullable properties conversion does not crash`() {
        converter.map(credentialDTO, Credential::class.java)
    }

    @Test
    fun `CredentialTypeDTO to core model conversion is valid`() {
        val credential = converter.map(credentialDTO, Credential::class.java)
        val credentialWithUnknownType =
            converter.map(credentialDTOWithUnknownType, Credential::class.java)
        Assertions.assertThat(credential.type)
            .isEqualTo(Credential.Type.TYPE_THIRD_PARTY_AUTHENTICATION)
        Assertions.assertThat(credentialWithUnknownType.type)
            .isEqualTo(Credential.Type.TYPE_UNKNOWN)
    }

    @Test
    fun `CredentialStatusDTO to core model conversion is valid`() {
        val credentialWithValidStatus =
            converter.map(credentialDTOWithValidStatus, Credential::class.java)
        val credentialWithUnknownStatus =
            converter.map(credentialDTOWithUnknownStatus, Credential::class.java)
        Assertions.assertThat(credentialWithValidStatus.status)
            .isEqualTo(Credential.Status.STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION)
        Assertions.assertThat(credentialWithUnknownStatus.status)
            .isEqualTo(Credential.Status.STATUS_UNKNOWN)
    }
}