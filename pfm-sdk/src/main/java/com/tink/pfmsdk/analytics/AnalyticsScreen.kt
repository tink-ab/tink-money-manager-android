package com.tink.pfmsdk.analytics

data class AnalyticsScreen private constructor(val name: String) {

    companion object {

        @JvmField
        val OVERVIEW = AnalyticsScreen("Overview")
        @JvmField
        val CATEGORY_LIST = AnalyticsScreen("Category.List")
        @JvmField
        val TRANSACTIONS_SIMILAR = AnalyticsScreen("Transactions.Similar")
        @JvmField
        val TRACKING_ERROR = AnalyticsScreen("TrackingError")
        @JvmField
        val TRANSACTIONS_LIST = AnalyticsScreen("Transactions.List")
        @JvmField
        val EXPENSES = AnalyticsScreen("Expenses")
    }
}
