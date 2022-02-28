package com.tink.moneymanagerui.accounts

import android.os.Parcelable
import com.tink.model.account.Account
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

sealed class OverviewAccountsMode(val overviewAccountsFilter: AccountFilter) : Parcelable

@Parcelize
object OverviewAllAccounts : OverviewAccountsMode({
    true
})

@Parcelize
object OverviewFavoriteAccounts : OverviewAccountsMode({
    it.account.favored
})

@Parcelize
class OverviewFinancialInstitutionAccounts(private val financialInstitutionId: String) : OverviewAccountsMode({
    it.account.financialInstitutionID == financialInstitutionId
})

@Parcelize
class OverviewCredentialsIdAccounts(private val credentialsId: String) : OverviewAccountsMode({
    it.account.credentialsId == credentialsId
})

@Parcelize
class OverviewAccountsTypeAccounts(private val accountType: Account.Type) : OverviewAccountsMode({
    it.account.type == accountType
})

@Parcelize
class OverviewCustomAccounts(private val customAccountFilter: @RawValue AccountFilter) : OverviewAccountsMode(customAccountFilter)
