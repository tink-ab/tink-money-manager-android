package com.tink.pfmsdk.transaction

import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ScreenEvent
import com.tink.pfmsdk.view.TinkToolbar
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.FragmentAnimationFlags
import kotlinx.android.synthetic.main.transactions_list_fragment.*
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

class TransactionsListFragment : BaseFragment() {

    @Inject
    lateinit var ownTheme: Theme

    @Inject
    lateinit var dateUtils: DateUtils

    override fun getLayoutId(): Int = R.layout.transactions_list_fragment
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getTheme(): Theme = ownTheme
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
        theme.setStatusbarColor(metaData.statusBarColor)
        theme.setToolbarBackgroundColor(metaData.backgroundColor)

        setupTransactionAdapter()

        viewModel.transactionItems.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setTransactionItems(it.transactions)
            }
        )

        viewModel.errors.observe(
            viewLifecycleOwner,
            Observer { error ->
                snackbarManager.displayError(
                    error,
                    requireContext()
                )
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



    interface Theme : BaseFragment.Theme {

        override fun getToolbarTheme(): TinkToolbar.Theme

        fun setStatusbarColor(color: Int)

        fun setToolbarBackgroundColor(backgroundColor: Int)
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
