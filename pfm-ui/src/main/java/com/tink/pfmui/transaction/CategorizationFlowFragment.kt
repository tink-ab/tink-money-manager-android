package com.tink.pfmui.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.overview.charts.CategorySelectionFragment
import com.tink.pfmui.overview.charts.CategorySelectionListener
import se.tink.core.models.Category
import se.tink.core.models.misc.ExactNumber
import se.tink.core.models.transaction.Transaction

private const val ARG_TRANSACTION_ID = "arg_transaction_id"

internal class CategorizationFlowFragment : BaseFragment(), CategorySelectionListener {

    override fun getLayoutId(): Int = R.layout.tink_fragment_category_selection_flow
    override fun needsLoginToBeAuthorized(): Boolean = true

    private val transactionId: String by lazy {
        requireNotNull(
            arguments?.getString(
                ARG_TRANSACTION_ID
            )
        )
    }

    private lateinit var viewModel: CategorizationFlowViewModel

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)[CategorizationFlowViewModel::class.java]
            .also { it.setTransactionId(transactionId) }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CategorizationFlowViewModel.State.CategorySelection -> showCategoryPickerView(it.transaction)

                is CategorizationFlowViewModel.State.SimilarTransactions ->
                    showSimilarTransactionsOnReturn(it.updatedCategoryCode)

                is CategorizationFlowViewModel.State.Done -> fragmentCoordinator.popBackStack()
            }
        })
    }

    private fun showCategoryPickerView(transaction: Transaction) {

        val categoryType =
            if (transaction.amount.value.isBiggerThan(ExactNumber.ZERO)) {
                Category.Type.TYPE_INCOME
            } else {
                Category.Type.TYPE_EXPENSES
            }

        CategorySelectionFragment
            .newInstance(
                categoryType,
                transaction.categoryCode,
                CategorySelectionFragment.Options(
                    dropdownToolbarAppearance = false,
                    includeTransferTypes = true,
                    includeTopLevelItem = false
                )
            )
            .also {
                it.setTargetFragment(this, 0)
                fragmentCoordinator.replace(it)
            }
    }

    private fun showSimilarTransactionsOnReturn(updatedCategoryCode: String) {
        viewModel.similarTransactions.observe(this, object : Observer<List<Transaction>?> {
            override fun onChanged(list: List<Transaction>?) {
                list?.let {
                    viewModel.similarTransactions.removeObserver(this)
                    if (list.isNotEmpty()) {
                        showSimilarTransactionFragment(list, updatedCategoryCode)
                    } else {
                        viewModel.similarTransactionsDone()
                    }
                }
            }
        })
    }

    override fun onCategorySelected(updatedCategoryCode: String) =
        viewModel.categorySelected(updatedCategoryCode)

    override fun onCategorySelectionCancelled() = viewModel.categorySelectionCancelled()

    private fun showSimilarTransactionFragment(transactions: List<Transaction>, code: String) {
        SimilarTransactionsFragment.newInstance(transactions, code).also {
            it.onSimilarTransactionsDone = viewModel::similarTransactionsDone
            fragmentCoordinator.replace(it)
        }
    }

    companion object {
        fun newInstance(transactionId: String) =
            CategorizationFlowFragment().apply {
                arguments = bundleOf(ARG_TRANSACTION_ID to transactionId)
            }
    }
}
