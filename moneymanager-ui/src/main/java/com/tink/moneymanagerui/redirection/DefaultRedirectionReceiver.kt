package com.tink.moneymanagerui.redirection

import android.content.Context
import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.details.AccountDetailsFragment
import com.tink.moneymanagerui.budgets.creation.BudgetCreationFragment
import com.tink.moneymanagerui.budgets.details.BudgetDetailsFragment
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import com.tink.moneymanagerui.transaction.StatusSubtitleMode
import com.tink.moneymanagerui.transaction.TransactionsListFragment
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.redirection.RedirectionReceiver
import javax.inject.Inject

internal class DefaultRedirectionReceiver @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val fragmentCoordinator: FragmentCoordinator
) : RedirectionReceiver {

    override fun showBudget(id: String, periodStart: String) {
        fragmentCoordinator.replace(BudgetDetailsFragment.newInstance(id, periodStart))
    }

    override fun createBudget(
        name: String?,
        amount: Amount?,
        filter: BudgetFilter?,
        periodicity: BudgetPeriodicity?
    ) {
        fragmentCoordinator.replace(BudgetCreationFragment.newInstance(name, amount, filter, periodicity))
    }

    override fun showAccountDetails(accountId: String) {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(accountId))
    }

    override fun showTransactionListForIds(transactionIds: List<String>) {
        fragmentCoordinator.replace(
            TransactionsListFragment.newInstance(
                data = TransactionsListMetaData(
                    title = context.getString(R.string.tink_transactions_list_toolbar_title),
                    period = null,
                    categoryId = null,
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
}
