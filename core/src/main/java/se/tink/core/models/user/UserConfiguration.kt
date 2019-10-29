package se.tink.core.models.user

data class UserFeatureFlags(private val list: List<String>) : List<String> by list {
    val hasTransfers = list.contains("TRANSFERS")
    val hasBillPay = list.contains("BILLPAY")
    val isEmployee = list.contains("TINK_EMPLOYEE")
    val isBetaUser = list.contains("ANDROID_BETA")
    val hasBudgets = list.contains("BUDGETS_V2")
    val hasRecurringTransactions = list.contains("RECURRING_TRANSACTIONS")
    val hasInsights = list.contains("ACTIONABLE_INSIGHTS")
}

class UserConfiguration {

    var flags: UserFeatureFlags = UserFeatureFlags(listOf())
        private set
    var i18nConfiguration: UserConfigurationI18NConfiguration? = null

    fun setFlags(flags: List<String>) {
        this.flags = UserFeatureFlags(flags)
    }
}

