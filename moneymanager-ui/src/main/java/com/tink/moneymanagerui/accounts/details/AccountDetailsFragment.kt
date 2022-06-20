package com.tink.moneymanagerui.accounts.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.account.Account
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.edit.AccountDetailsEditFragment
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.transaction.TransactionListViewModel
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import com.tink.moneymanagerui.transaction.toListMode
import com.tink.moneymanagerui.util.TransactionListHelper
import kotlinx.android.synthetic.main.tink_fragment_account_details.*
import kotlinx.android.synthetic.main.tink_fragment_account_details.view.*
import kotlinx.android.synthetic.main.tink_transactions_list_fragment.recyclerView
import se.tink.commons.transactions.TransactionItemListAdapter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class AccountDetailsFragment : BaseFragment() {

    @Inject
    lateinit var dateUtils: DateUtils

    private val accountId: String by lazy { requireNotNull(arguments?.getString(ACCOUNT_ID_ARGS)) }

    private lateinit var viewModel: AccountDetailsViewModel
    private lateinit var transactionListViewModel: TransactionListViewModel

    private val accountHeaderAdapter = AccountHeaderAdapter()
    private lateinit var transactionsAdapter: TransactionItemListAdapter
    private lateinit var accountDetailsAdapter: ConcatAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var metadata: TransactionsListMetaData

    override fun getLayoutId(): Int = R.layout.tink_fragment_account_details
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.ACCOUNT_DETAILS
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

        transactionListViewModel.isEditableOnPendingTransaction =
            TransactionListHelper().isEditableOnPendingValue(requireActivity())

        viewModel.setAccountId(accountId)
        metadata = TransactionsListMetaData(
            title = "",
            accountId = accountId
        )
        transactionListViewModel.setListMode(metadata.toListMode())

        transactionsAdapter = TransactionItemListAdapter(dateUtils = dateUtils, groupByDates = true)
        accountDetailsAdapter = ConcatAdapter(accountHeaderAdapter, transactionsAdapter)
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        setupViews()

        viewModel.account.observe(
            viewLifecycle,
            Observer {
                it?.let { account ->
                    title = account.name
                    accountHeaderAdapter.submitList(listOf(AccountInformation(account.accountNumber, account.balance)))
                }
            }
        )

        transactionListViewModel.errors.observe(
            viewLifecycleOwner,
            Observer { event ->
                event?.getContentIfNotHandled()?.let { error ->
                    snackbarManager.displayError(error, requireContext())
                }
            }
        )

        transactionListViewModel.loading.observe(
            viewLifecycleOwner,
            Observer { loading ->
                view.loader.visibleIf { loading }
                if (loading) {
                    noTransactionsText.visibility = View.GONE
                }
            }
        )

        transactionListViewModel.transactionItems.observe(
            viewLifecycleOwner,
            Observer {
                transactionsAdapter.setTransactionItems(it.transactions)
                noTransactionsText.visibleIf { it.transactions.isEmpty() }
            }
        )
    }

    private fun setupViews() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)

        accountDetailsAdapter.addAdapter(transactionsAdapter)

        TransactionListHelper().navToCategorizationOrShowUneditableMsg(
            fragmentCoordinator,
            requireActivity(),
            transactionsAdapter,
            MoneyManagerFeatureType.ACCOUNTS
        )
        recyclerView.adapter = accountDetailsAdapter
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.ACCOUNTS

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        super.onCreateToolbarMenu(toolbar)
        if (viewModel.accountCanBeEdited) {
            toolbar.inflateMenu(R.menu.tink_menu_account_details)
        }
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            fragmentCoordinator.replace(AccountDetailsEditFragment.newInstance(accountId))
            return true
        }
        return super.onToolbarMenuItemSelected(item)
    }

    companion object {

        private const val ACCOUNT_ID_ARGS = "account_id_args"

        fun newInstance(account: Account): BaseFragment = newInstance(account.id)

        fun newInstance(accountId: String): BaseFragment =
            AccountDetailsFragment().apply {
                arguments = bundleOf(ACCOUNT_ID_ARGS to accountId)
            }
    }
}
