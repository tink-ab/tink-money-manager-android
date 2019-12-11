package com.tink.pfmsdk.tracking

data class ActionEvent private constructor(val category: String, val action: String, val label: String) {

    companion object {

        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_TWELVE_MONTHS = ActionEvent("LeftToSpend", "Show page", "Twelve months")
        @JvmField
        val EXPENSES_SHOW_PAGE_TWELVE_MONTHS = ActionEvent("Expenses", "Show page", "Twelve months")
        @JvmField
        val INCOME_SHOW_PAGE_ONE_MONTH = ActionEvent("Income", "Show page", "One month")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_SIX_MONTHS = ActionEvent("LeftToSpend", "Show page", "Six months")
        @JvmField
        val INCOME_SHOW_PAGE_TWELVE_MONTHS = ActionEvent("Income", "Show page", "Twelve months")
        @JvmField
        val LEFTTOSPEND_SHOW_PAGE_ONE_MONTH = ActionEvent("LeftToSpend", "Show page", "One month")
        @JvmField
        val EXPENSES_SHOW_PAGE_ONE_MONTH = ActionEvent("Expenses", "Show page", "One month")
        @JvmField
        val INCOME_SHOW_PAGE_SIX_MONTHS = ActionEvent("Income", "Show page", "Six months")
        @JvmField
        val EXPENSES_SHOW_PAGE_SIX_MONTHS = ActionEvent("Expenses", "Show page", "Six months")
    }
}
