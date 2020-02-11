package com.tink.pfmsdk.tracking

/**
 * This class represents an event that occurs when a user navigates to a new screen within the Finance Overview UI.
 */
data class ScreenEvent private constructor(val name: String) {

    companion object {

        @JvmField
        val OVERVIEW = ScreenEvent("Overview")
        @JvmField
        val CATEGORY_SELECTION = ScreenEvent("Category Selection")
        @JvmField
        val SIMILAR_TRANSACTIONS = ScreenEvent("Similar Transactions")
        @JvmField
        val TRACKING_ERROR = ScreenEvent("TrackingError")
        @JvmField
        val TRANSACTIONS = ScreenEvent("Transactions")
        @JvmField
        val EXPENSES = ScreenEvent("Expenses")
        @JvmField
        val INCOME = ScreenEvent("Income")
        @JvmField
        val ACCOUNT_DETAILS = ScreenEvent("Account Details")
    }
}
