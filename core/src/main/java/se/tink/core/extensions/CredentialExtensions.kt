package se.tink.core.extensions

import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Minutes
import se.tink.core.models.credential.Credential
import se.tink.core.models.misc.Field

typealias CredentialSessionExpiryDate = Long
typealias RefreshableSessionExpiryDate = DateTime

fun Credential.isManuallyRefreshable(): Boolean =
    when (type) {
        Credential.Type.TYPE_PASSWORD -> isPasswordRefreshable()
        Credential.Type.TYPE_MOBILE_BANKID -> isMobileBankIdRefreshable()
        Credential.Type.TYPE_THIRD_PARTY_AUTHENTICATION -> isThirdPartyRefreshable()
        else -> false
    }

private fun Credential.isPasswordRefreshable() =
    when (status) {
        Credential.Status.STATUS_SESSION_EXPIRED -> true

        // Only credentials of Open Banking connections has sessionExpiryDate.
        // Therefore, do the 7 day range check to see if it needs refreshing.
        else -> isSessionRefreshable()
    }

private fun Credential.isMobileBankIdRefreshable() =
    when (status) {
        Credential.Status.STATUS_AUTHENTICATING,
        Credential.Status.STATUS_TEMPORARY_ERROR,
        Credential.Status.STATUS_CREATED -> true
        Credential.Status.STATUS_UPDATED -> updated.olderThan(3)
        else -> false
    }

private fun Credential.isThirdPartyRefreshable() =
    when (status) {
        Credential.Status.STATUS_AUTHENTICATION_ERROR,
        Credential.Status.STATUS_SESSION_EXPIRED -> true

        // Only credentials of Open Banking connections has sessionExpiryDate.
        // Therefore, do the 7 day range check to see if it needs refreshing.
        else -> isSessionRefreshable()
    }

fun List<Field>.toMap() = map { it.name to it.value }.toMap()

private fun Long.olderThan(minutes: Int) =
    Minutes
        .minutesBetween(DateTime(this), DateTime.now())
        .isGreaterThan(Minutes.minutes(minutes))

fun Credential.sessionExpiryIfRefreshable(): RefreshableSessionExpiryDate? =
    sessionExpiryDate?.let { expiryDateMillis ->
        val now = DateTime.now()
        DateTime(expiryDateMillis).takeIf { it.isAfter(now) && it.isBefore(now.plusDays(7)) }
    }

fun Credential.isSessionRefreshable(): Boolean = sessionExpiryIfRefreshable() != null

