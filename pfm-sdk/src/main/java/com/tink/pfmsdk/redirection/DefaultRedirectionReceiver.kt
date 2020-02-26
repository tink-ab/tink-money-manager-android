package com.tink.pfmsdk.redirection

import android.content.Context
import com.tink.pfmsdk.FragmentCoordinator
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver
import se.tink.core.models.misc.Amount
import javax.inject.Inject

/**
 * TODO: Implement redirection logic when possible
 */
internal class DefaultRedirectionReceiver @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val fragmentCoordinator: FragmentCoordinator
) : RedirectionReceiver {

    override fun showBudget(id: String, periodStart: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAccountDetails(accountId: String, topOnly: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTransactionListForIds(transactionIds: List<String>) {
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