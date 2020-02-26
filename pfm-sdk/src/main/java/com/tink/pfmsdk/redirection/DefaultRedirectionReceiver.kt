package com.tink.pfmsdk.redirection

import android.content.Context
import com.tink.pfmsdk.FragmentAnimationFlags
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.overview.accounts.AccountDetailsFragment
import com.tink.pfmsdk.transaction.CategorizationFlowFragment
import com.tink.pfmsdk.transaction.StatusSubtitleMode
import com.tink.pfmsdk.transaction.TransactionsListFragment
import com.tink.pfmsdk.transaction.TransactionsListMetaData
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver
import se.tink.commons.extensions.getColorFromAttr
import se.tink.core.models.misc.Amount
import javax.inject.Inject

internal class DefaultRedirectionReceiver @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val fragmentCoordinator: FragmentCoordinator
) : RedirectionReceiver {

    override fun showBudget(id: String, periodStart: String) {
        // TODO: PFMSDK: Implement this when budget feature is available
    }

    override fun showAccountDetails(accountId: String) {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(accountId))
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
        // TODO: PFMSDK: Implement this when we have a transaction details screen
    }

    override fun categorizeTransaction(transactionId: String) {
        fragmentCoordinator.replace(
            CategorizationFlowFragment.newInstance(transactionId),
            animation = FragmentAnimationFlags.FADE_IN_ONLY
        )
    }

    override fun showTransfer(transferId: String) {
        // TODO: PFMSDK: Implement this when we can support transfer flows
    }

    override fun showTransfer(
        sourceAccountId: String,
        destinationAccountId: String,
        amount: Amount
    ) {
        // TODO: PFMSDK: Implement this when we can support transfer flows
    }

    override fun showCategory(categoryCode: String) {
        // TODO: PFMSDK: Show statistics screen for the category
    }
}