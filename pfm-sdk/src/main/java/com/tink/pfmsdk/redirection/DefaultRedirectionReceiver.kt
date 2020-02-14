package com.tink.pfmsdk.redirection

import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.misc.Amount

/**
 * TODO: Implement redirection logic when possible
 */
internal class DefaultRedirectionReceiver : RedirectionReceiver {
    override fun showCredentialsDetail(id: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showBudget(id: String, periodStart: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showOverview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleThirdPartyCallbackResult(
        state: String,
        parameters: MutableMap<String, String>?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFeed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProviders(topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAddProvider(name: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRateThisApp(agreedToRate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAccountDetails(accountId: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLeftToSpend(periodKey: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProfile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showIdControlList(topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearch(query: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleIceCreamHackVisibility() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuggestions() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTransactionListForIds(transactionIds: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showIdControlDetail(id: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAccountsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTransactionDetails(transactionId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTransfer(transferId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTransfer(
        sourceAccountId: String,
        destinationAccountId: String,
        amount: Amount
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCategory(topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}