package com.tink.pfmsdk.overview.latesttransactions

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.FragmentAnimationFlags
import com.tink.pfmsdk.R
import com.tink.pfmsdk.transaction.CategorizationFlowFragment
import com.tink.pfmsdk.transaction.StatusSubtitleMode
import com.tink.pfmsdk.transaction.TransactionsListFragment
import com.tink.pfmsdk.transaction.TransactionsListMetaData
import kotlinx.android.synthetic.main.fragment_latest_transactions.*
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class LatestTransactionsFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_latest_transactions
    override fun needsLoginToBeAuthorized(): Boolean = true

    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var viewModel: LatestTransactionsViewModel
    private lateinit var transactionsAdapter: TransactionItemListAdapter

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)[LatestTransactionsViewModel::class.java]

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
            getString(R.string.overview_latest_transactions_title),
            false,
            null, null,
            true,
            StatusSubtitleMode.SHOW_REDUCED_AMOUNT, null
        )

        fragmentCoordinator.replace(TransactionsListFragment.newInstance(metaData))
    }
}