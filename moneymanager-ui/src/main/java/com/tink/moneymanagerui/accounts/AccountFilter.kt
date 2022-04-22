package com.tink.moneymanagerui.accounts

/**
 * Interface definition to filter accounts
 */
fun interface AccountFilter {
    /**
     * Called to determine which accounts should be shown on the financial overview
     *
     * @param account an account that is a candidate to be shown on the financial overview
     * @return `true` if it should be included in the financial overview
     */
    fun isIncludedInOverview(account: AccountWithImage): Boolean
}
