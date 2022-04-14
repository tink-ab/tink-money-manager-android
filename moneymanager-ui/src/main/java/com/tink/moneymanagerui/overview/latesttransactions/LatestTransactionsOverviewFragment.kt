package com.tink.moneymanagerui.overview.latesttransactions

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import com.tink.moneymanagerui.transaction.StatusSubtitleMode
import com.tink.moneymanagerui.transaction.TransactionsListFragment
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_overview_latest_transactions.*
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class LatestTransactionsOverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.tink_fragment_overview_latest_transactions
    override fun needsLoginToBeAuthorized(): Boolean = true

    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var viewModel: LatestTransactionsViewModel
    private lateinit var transactionsAdapter: TransactionItemListAdapter

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        // Try scoping the ViewModel to the parent fragment to allow data retention
        val scope = parentFragment ?: this

        viewModel = ViewModelProviders
            .of(scope, viewModelFactory)[LatestTransactionsViewModel::class.java]

        transactionsAdapter = TransactionItemListAdapter(dateUtils, groupByDates = false)
        transactionsAdapter.onTransactionItemClickedListener = {
            fragmentCoordinator.replace(
                CategorizationFlowFragment.newInstance(it),
                animation = FragmentAnimationFlags.FADE_IN_ONLY
            )
        }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        latestTransactionsView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
        }

        action.setOnClickListener { showTransactions() }

        // TODO: Remove this and instead have latestTransactions use ResponseState
        viewModel.categoriesState.observe(
            viewLifecycleOwner
        ) {
            card.visibleIf { it is SuccessState }
        }

        viewModel.latestTransactions.observe(
            viewLifecycleOwner,
            Observer {
                transactionsAdapter.setTransactionItems(it)
            }
        )

        viewModel.hasTransactions.observe(
            viewLifecycleOwner,
            Observer { hasTransactions ->
                TransitionManager.beginDelayedTransition(view as ViewGroup)
                card.visibility = if (hasTransactions) View.VISIBLE else View.GONE
            }
        )

        viewModel.loading.observe(
            viewLifecycleOwner,
            Observer { loading ->
                TransitionManager.beginDelayedTransition(view as ViewGroup)
                loader.visibility = if (loading) View.VISIBLE else View.GONE
            }
        )
    }

    private fun showTransactions() {

        val context = context ?: return

        val metaData = TransactionsListMetaData(
            getString(R.string.tink_latest_transactions_toolbar_title),
            false,
            null, null,
            true,
            StatusSubtitleMode.SHOW_REDUCED_AMOUNT, null
        )

        fragmentCoordinator.replace(TransactionsListFragment.newInstance(metaData))
    }
}
