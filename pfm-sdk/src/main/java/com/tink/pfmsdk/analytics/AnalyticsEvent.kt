package com.tink.pfmsdk.analytics

data class AnalyticsEvent private constructor(val category: String, val action: String, val label: String) {

    companion object {

        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("LeftToSpend", "Show page", "Twelve months")
        @JvmField
        val EXPENSES_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("Expenses", "Show page", "Twelve months")
        @JvmField
        val INCOME_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("Income", "Show page", "One month")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("LeftToSpend", "Show page", "Six months")
        @JvmField
        val INCOME_SHOW_PAGE_TWELVE_MONTHS = AnalyticsEvent("Income", "Show page", "Twelve months")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("LeftToSpend", "Show page", "One month")
        @JvmField
        val EXPENSES_SHOW_PAGE_ONE_MONTH = AnalyticsEvent("Expenses", "Show page", "One month")
        @JvmField
        val INCOME_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("Income", "Show page", "Six months")
        @JvmField
        val EXPENSES_SHOW_PAGE_SIX_MONTHS = AnalyticsEvent("Expenses", "Show page", "Six months")
    }
}
