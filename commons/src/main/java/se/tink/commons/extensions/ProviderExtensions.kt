package se.tink.commons.extensions

import android.content.Context
import se.tink.commons.R
import se.tink.core.models.provider.Provider

fun Provider.AccessType?.isOpenBanking() = this == Provider.AccessType.TYPE_OPEN_BANKING
fun Provider.AccessType?.isOther() = this == Provider.AccessType.TYPE_OTHER

fun List<Provider>.openBankingProviders() =
    this.filter { it.accessType.isOpenBanking() }

fun List<Provider>.openBankingInstitutionIds() =
    this.openBankingProviders().map { it.financialInstitutionId }

fun List<Provider>.otherProviders() =
    this.filter { it.accessType.isOther() }

fun List<Provider>.otherInstitutionIds() = this.otherProviders().map { it.financialInstitutionId }

fun Provider.getCapabilitiesText(context: Context): String {
    val filteredCapabilities = filterCapabilities().takeIf { it.isNotEmpty() }
    return filteredCapabilities
        ?.capabilitiesToText(context)
        ?: accessType.defaultCapabilityText(context).toLowerCase()
}

fun List<Provider.Capability>.capabilitiesToText(
    context: Context
): String = distinct()
    .map { it.convertToString(context).toLowerCase() }
    .let {
        if (it.size > 1) {
            it.subList(0, it.size - 1).joinToString()
                .plus(
                    " ${
                    context.getString(
                        R.string.provider_capability_last_separator,
                        it.last()
                    )
                    }"
                )
        } else {
            it.firstOrNull() ?: ""
        }
    }

fun Provider.AccessType.defaultCapabilityText(context: Context): String =
    if (this == Provider.AccessType.TYPE_OPEN_BANKING) {
        context.getString(R.string.provider_capability_open_banking_default)
    } else {
        context.getString(R.string.provider_capability_other_default)
    }

fun Provider.filterCapabilities() =
    capabilities.filterNot {
        it == Provider.Capability.CAPABILITY_TRANSFERS
                || it == Provider.Capability.CAPABILITY_PAYMENTS
                || it == Provider.Capability.CAPABILITY_MORTGAGE_AGGREGATION
                || it == Provider.Capability.CAPABILITY_UNKNOWN
    }

fun Provider.Capability.convertToString(context: Context): String =
    when (this) {
        Provider.Capability.CAPABILITY_CHECKING_ACCOUNTS -> context.getString(R.string.provider_capability_checking_accounts)
        Provider.Capability.CAPABILITY_SAVINGS_ACCOUNTS -> context.getString(R.string.provider_capability_savings_accounts)
        Provider.Capability.CAPABILITY_CREDIT_CARDS -> context.getString(R.string.provider_capability_credit_cards)
        Provider.Capability.CAPABILITY_INVESTMENTS -> context.getString(R.string.provider_capability_investments)
        Provider.Capability.CAPABILITY_LOANS -> context.getString(R.string.provider_capability_loans)
        Provider.Capability.CAPABILITY_MORTGAGE_LOAN -> context.getString(R.string.provider_capability_mortgage_loan)
        Provider.Capability.CAPABILITY_MORTGAGE_AGGREGATION -> context.getString(R.string.provider_capability_mortgage_aggregation)
        Provider.Capability.CAPABILITY_TRANSFERS -> context.getString(R.string.provider_capability_transfers)
        Provider.Capability.CAPABILITY_IDENTITY_DATA -> context.getString(R.string.provider_capability_identity_data)
        Provider.Capability.CAPABILITY_PAYMENTS -> context.getString(R.string.provider_capability_payments)
        Provider.Capability.CAPABILITY_UNKNOWN -> context.getString(R.string.provider_capability_other_default)
    }

