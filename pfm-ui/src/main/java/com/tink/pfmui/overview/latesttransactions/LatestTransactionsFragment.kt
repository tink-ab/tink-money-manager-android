package com.tink.pfmui.overview.latesttransactions

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.FragmentAnimationFlags
import com.tink.pfmui.R
import com.tink.pfmui.transaction.CategorizationFlowFragment
import com.tink.pfmui.transaction.StatusSubtitleMode
import com.tink.pfmui.transaction.TransactionsListFragment
import com.tink.pfmui.transaction.TransactionsListMetaData
import kotlinx.android.synthetic.main.tink_fragment_latest_transactions.*
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class LatestTransactionsFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.tink_fragment_latest_transactions
    override fun needsLoginToBeAuthorized(): Boolean = true

    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var viewModel: LatestTransactionsViewModel
    private lateinit var transactionsAdapter: TransactionItemListAdapter

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        //Try scoping the ViewModel to the parent fragment to allow data retention
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

        viewModel.latestTransactions.observe(viewLifecycleOwner, Observer {
            transactionsAdapter.setTransactionItems(it)
        })

        viewModel.hasTransactions.observe(viewLifecycleOwner, Observer { hasTransactions ->
            TransitionManager.beginDelayedTransition(view as ViewGroup)
            mainContent.visibility = if(hasTransactions) View.VISIBLE else View.GONE
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            TransitionManager.beginDelayedTransition(view as ViewGroup)
            loader.visibility = if(loading) View.VISIBLE else View.GONE
        })
    }

    private fun showTransactions() {

        val context = context ?: return

        val metaData = TransactionsListMetaData(
            context.getColorFromAttr(R.attr.tink_colorPrimaryDark),
            context.getColorFromAttr(R.attr.tink_colorPrimary),
            context.getColorFromAttr(R.attr.tink_colorOnPrimary),
            getString(R.string.tink_overview_latest_transactions_title),
            false,
            null, null,
            true,
            StatusSubtitleMode.SHOW_REDUCED_AMOUNT, null
        )

        fragmentCoordinator.replace(TransactionsListFragment.newInstance(metaData))
    }
}