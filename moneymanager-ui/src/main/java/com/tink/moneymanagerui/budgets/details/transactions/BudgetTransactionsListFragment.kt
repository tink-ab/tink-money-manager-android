package com.tink.moneymanagerui.budgets.details.transactions

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.R
import se.tink.commons.transactions.TransactionItemListAdapter
import com.tink.moneymanagerui.budgets.details.di.BudgetDetailsViewModelFactory
import com.tink.moneymanagerui.databinding.TinkFragmentBudgetTransactionsListBinding
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import kotlinx.android.synthetic.main.tink_fragment_budget_transactions_list.*
import kotlinx.android.synthetic.main.tink_fragment_budget_transactions_list.view.*
import kotlinx.android.synthetic.main.tink_item_picker.view.iconLeft
import kotlinx.android.synthetic.main.tink_item_picker.view.iconRight
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class BudgetTransactionsListFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = BudgetTransactionsListFragment()
    }

    private lateinit var transactionsListViewModel: BudgetTransactionListViewModel

    @Inject
    internal lateinit var budgetDetailsViewModelFactory: BudgetDetailsViewModelFactory

    @Inject
    lateinit var dateUtils: DateUtils

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_transactions_list
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getTitle(): String = getString(R.string.tink_budget_transactions_toolbar_title)
    override fun doNotRecreateView(): Boolean = false
    override fun hasToolbar(): Boolean = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.BUDGET_TRANSACTIONS

    private lateinit var adapter: TransactionItemListAdapter

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        transactionsListViewModel = ViewModelProviders.of(
            this,
            budgetDetailsViewModelFactory
        )[BudgetTransactionListViewModel::class.java]

        adapter = TransactionItemListAdapter(dateUtils).also {
            it.onTransactionItemClickedListener = { transactionId ->
                CategorizationFlowFragment
                    .newInstance(transactionId)
                    .also { fragment ->
                        fragmentCoordinator.replace(
                            fragment,
                            animation = FragmentAnimationFlags.FADE_IN_ONLY
                        )
                    }
            }
        }

        DataBindingUtil.bind<TinkFragmentBudgetTransactionsListBinding>(view.root)
            ?.also {
                it.viewModel = transactionsListViewModel
                it.lifecycleOwner = viewLifecycleOwner
            }
        transactionsList.layoutManager = LinearLayoutManager(context)
        transactionsList.adapter = adapter
        transactionsListViewModel.apply {
            transactionItems.observe(viewLifecycle, { transactionItems ->
                transactionItems?.let {
                    adapter.setTransactionItems(it)
                }
            })
            budgetName.observe(viewLifecycle, { name ->
                name?.let {
                    title = it
                }
            })
            error.observe(viewLifecycle, { error ->
                if (error != null) {
                    snackbarManager.displayError(error, context)
                }
            })
        }
        periodPicker.iconLeft.setOnClickListener {
            transactionsListViewModel.showPreviousPeriod()
        }
        periodPicker.iconRight.setOnClickListener {
            transactionsListViewModel.showNextPeriod()
        }
    }
}