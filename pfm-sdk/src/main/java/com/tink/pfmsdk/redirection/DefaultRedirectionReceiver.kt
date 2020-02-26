package com.tink.pfmsdk.redirection

import android.content.Context
import com.tink.pfmsdk.FragmentAnimationFlags
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.transaction.CategorizationFlowFragment
import com.tink.pfmsdk.transaction.StatusSubtitleMode
import com.tink.pfmsdk.transaction.TransactionsListFragment
import com.tink.pfmsdk.transaction.TransactionsListMetaData
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver
import se.tink.commons.extensions.getColorFromAttr
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
        fragmentCoordinator.replace(
            TransactionsListFragment.newInstance(
                data = TransactionsListMetaData(
                    statusBarColor = context.getColorFromAttr(R.attr.tink_colorPrimaryDark),
                    backgroundColor = context.getColorFromAttr(R.attr.tink_colorPrimary),
                    title = context.getString(R.string.transactions_list_toolbar_title),
                    period = null,
                    categoryCode = null,
                    isShowAll = false,
                    statusSubtitleMode = StatusSubtitleMode.SHOW_REDUCED_AMOUNT,
                    transactionIds = transactionIds,
                    accountId = null
                )
            )
        )
    }

    override fun showTransactionDetails(transactionId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun categorizeTransaction(transactionId: String) {
        fragmentCoordinator.replace(
            CategorizationFlowFragment.newInstance(transactionId),
            animation = FragmentAnimationFlags.FADE_IN_ONLY
        )
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