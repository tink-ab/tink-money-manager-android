package com.tink.pfmui.transaction

import com.tink.pfmui.R
import com.tink.pfmui.tracking.ScreenEvent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.FragmentAnimationFlags
import kotlinx.android.synthetic.main.tink_transactions_list_fragment.*
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class TransactionsListFragment : BaseFragment() {

    @Inject
    lateinit var dateUtils: DateUtils

    override fun getLayoutId(): Int = R.layout.tink_transactions_list_fragment
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRANSACTIONS
    override fun hasToolbar(): Boolean = true

    private lateinit var adapter: TransactionItemListAdapter

    private lateinit var layoutManager: LinearLayoutManager

    private val metaData: TransactionsListMetaData by lazy {
        requireNotNull(arguments?.getParcelable(
            METADATA
        ) as? TransactionsListMetaData)
    }

    private lateinit var viewModel: TransactionListViewModel

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                viewModel.loadMoreItems()
            }
        }
    }

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TransactionListViewModel::class.java)
        viewModel.setListMode(metaData.toListMode())
    }

    public override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        title = metaData.title

        setupTransactionAdapter()

        viewModel.transactionItems.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setTransactionItems(it.transactions)
            }
        )

        viewModel.errors.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { error ->
                snackbarManager.displayError(error, requireContext())
            }
        })

        viewModel.loading.observe(
            viewLifecycleOwner,
            Observer { loading ->
                loader.visibility = if (loading) View.VISIBLE else View.GONE
            })
    }

    private fun setupTransactionAdapter() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        adapter = TransactionItemListAdapter(dateUtils = dateUtils, groupByDates = true)

        adapter.onTransactionItemClickedListener = { id ->
            CategorizationFlowFragment
                .newInstance(id)
                .also {
                    fragmentCoordinator.replace(
                        it,
                        animation = FragmentAnimationFlags.FADE_IN_ONLY
                    )
                }
        }
        recyclerView.adapter = adapter
    }

    companion object {

        private const val METADATA = "metadata_args_key"

        @JvmStatic
        fun newInstance(data: TransactionsListMetaData) = TransactionsListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(METADATA, data)
            }
        }
    }
}
