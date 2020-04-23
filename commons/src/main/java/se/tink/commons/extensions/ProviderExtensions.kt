package se.tink.commons.extensions

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


fun Provider.filterCapabilities() =
    capabilities.filterNot {
        it == Provider.Capability.CAPABILITY_TRANSFERS
                || it == Provider.Capability.CAPABILITY_PAYMENTS
                || it == Provider.Capability.CAPABILITY_MORTGAGE_AGGREGATION
                || it == Provider.Capability.CAPABILITY_UNKNOWN
    }

