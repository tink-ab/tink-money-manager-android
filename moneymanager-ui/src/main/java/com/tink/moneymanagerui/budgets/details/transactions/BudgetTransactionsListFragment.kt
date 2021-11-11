package com.tink.moneymanagerui.budgets.details.transactions

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import se.tink.commons.transactions.TransactionItemListAdapter
import com.tink.moneymanagerui.budgets.details.di.BudgetDetailsViewModelFactory
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import kotlinx.android.synthetic.main.tink_fragment_budget_transactions_list.*
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
                    .newInstance(transactionId, MoneyManagerFeatureType.BUDGETS)
                    .also { fragment ->
                        fragmentCoordinator.replace(
                            fragment,
                            animation = FragmentAnimationFlags.FADE_IN_ONLY
                        )
                    }
            }
        }

        transactionsList.layoutManager = LinearLayoutManager(context)
        transactionsList.adapter = adapter
        transactionsListViewModel.apply {

            budgetProgressStr.observe(viewLifecycle, { budgetProgressString ->
                budgetProgressText.text = budgetProgressString
            })

            budgetPeriodInterval.observe(viewLifecycle, { budgetPeriodInterval ->
                budgetDateInterval.text = budgetPeriodInterval
            })

            totalAmount.observe(viewLifecycle, { totalAmount ->
                budgetProgress.max = totalAmount
            })

            amountLeft.observe(viewLifecycle, { amountLeft ->
                budgetProgress.progress = if(amountLeft > 0) amountLeft else budgetProgress.max
            })

            emptyState.observe(viewLifecycle, { isEmpty ->
                transactionsList.visibleIf(View.INVISIBLE) { !isEmpty }
            })

            loading.observe(viewLifecycle, { isLoading ->
                loadingSpinner.visibleIf { isLoading }
            })

            emptyState.observe(viewLifecycle, { isEmpty ->
                emptyMessage.visibleIf { isEmpty }
            })

            budgetPeriodInterval.observe(viewLifecycle, { budgetPeriodInterval ->
                periodPicker.setText(budgetPeriodInterval)
            })

            showPicker.observe(viewLifecycle, { showPicker ->
                periodPicker.visibleIf { showPicker }
            })

            hasNext.observe(viewLifecycle, { hasNext ->
                periodPicker.setNextButtonEnabled(hasNext)
            })

            hasPrevious.observe(viewLifecycle, { hasPrevious ->
                periodPicker.setPreviousButtonEnabled(hasPrevious)
            })

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

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.BUDGETS
}