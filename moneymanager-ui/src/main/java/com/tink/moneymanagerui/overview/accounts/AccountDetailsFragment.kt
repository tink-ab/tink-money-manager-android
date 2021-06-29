package com.tink.moneymanagerui.overview.accounts

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.databinding.TinkFragmentAccountDetailsBinding
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import com.tink.moneymanagerui.transaction.TransactionListViewModel
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import com.tink.moneymanagerui.transaction.toListMode
import com.tink.moneymanagerui.view.ParallaxHeaderScrollListener
import kotlinx.android.synthetic.main.tink_fragment_account_details.*
import kotlinx.android.synthetic.main.tink_transactions_list_fragment.recyclerView
import se.tink.commons.transactions.TransactionItemListAdapter
import com.tink.model.account.Account
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class AccountDetailsFragment : BaseFragment() {

    @Inject
    lateinit var dateUtils: DateUtils

    private val accountId: String by lazy { requireNotNull(arguments?.getString(ACCOUNT_ID_ARGS)) }

    private lateinit var viewModel: AccountDetailsViewModel
    private lateinit var transactionListViewModel: TransactionListViewModel

    private lateinit var adapter: TransactionItemListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var metadata: TransactionsListMetaData

    override fun getLayoutId(): Int = R.layout.tink_fragment_account_details
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getScreenEvent(): ScreenEvent? = ScreenEvent.ACCOUNT_DETAILS
    override fun hasToolbar(): Boolean = true

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                transactionListViewModel.loadMoreItems()
            }
        }
    }

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        with(ViewModelProviders.of(this, viewModelFactory)) {
            viewModel = get(AccountDetailsViewModel::class.java)
            transactionListViewModel = get(TransactionListViewModel::class.java)
        }

        viewModel.setAccountId(accountId)
        metadata = TransactionsListMetaData(
            title = "",
            accountId = accountId
        )
        transactionListViewModel.setListMode(metadata.toListMode())
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<TinkFragmentAccountDetailsBinding>(view)?.also {
            it.viewModel = viewModel
            it.transactionListModel = transactionListViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        setupViews()

        viewModel.account.observe(viewLifecycle, Observer {
            it?.let { account ->
                title = account.name
                accountBalance.text = account.balance.formatCurrencyExact()
                accountNumber.text = account.accountNumber
                // Pronounce account number letter by letter in talkback instead of as a number
                accountNumber.contentDescription = account.accountNumber.replace(".".toRegex(), "$0 ")
            }
        })

        transactionListViewModel.errors.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { error ->
                snackbarManager.displayError(error, requireContext())
            }
        })

        transactionListViewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            loader.visibility = if (loading) View.VISIBLE else View.GONE
        })

        transactionListViewModel.transactionItems.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setTransactionItems(it.transactions)
            }
        )
    }

    private fun setupViews() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        val headerHeight = resources.getDimension(R.dimen.tink_account_details_header_height)
        val headers = listOf(accountBalance, accountNumber, divider, extraText)
        recyclerView.addOnScrollListener(ParallaxHeaderScrollListener(headers, headerHeight))
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        adapter = TransactionItemListAdapter(dateUtils = dateUtils, groupByDates = true)

        adapter.onTransactionItemClickedListener = { id ->
            CategorizationFlowFragment
                .newInstance(id)
                .also {
                    fragmentCoordinator.replace(
                        it,
                        addToBackStack = true,
                        animation = FragmentAnimationFlags.FADE_IN_ONLY
                    )
                }
        }
        recyclerView.adapter = adapter
    }

    companion object {

        private const val ACCOUNT_ID_ARGS = "account_id_args"

        fun newInstance(account: Account): BaseFragment = newInstance(account.id)

        fun newInstance(accountId: String): BaseFragment =
            AccountDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ACCOUNT_ID_ARGS, accountId)

                }
            }
    }
}
