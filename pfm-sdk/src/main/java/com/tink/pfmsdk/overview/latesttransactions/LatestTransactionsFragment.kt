package com.tink.pfmsdk.overview.latesttransactions

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import kotlinx.android.synthetic.main.fragment_latest_transactions.*
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

class LatestTransactionsFragment : BaseFragment() {
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
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        latestTransactionsView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
        }

        viewModel.latestTransactions.observe(viewLifecycleOwner, Observer {
            transactionsAdapter.setTransactionItems(it)
        })
    }
}