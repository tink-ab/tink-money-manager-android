package com.tink.pfmsdk.tracking

data class ScreenEvent private constructor(val name: String) {

    companion object {

        @JvmField
        val OVERVIEW = ScreenEvent("Overview")
        @JvmField
        val CATEGORY_LIST = ScreenEvent("Category.List")
        @JvmField
        val TRANSACTIONS_SIMILAR = ScreenEvent("Transactions.Similar")
        @JvmField
        val TRACKING_ERROR = ScreenEvent("TrackingError")
        @JvmField
        val TRANSACTIONS_LIST = ScreenEvent("Transactions.List")
        @JvmField
        val EXPENSES = ScreenEvent("Expenses")
    }
}
