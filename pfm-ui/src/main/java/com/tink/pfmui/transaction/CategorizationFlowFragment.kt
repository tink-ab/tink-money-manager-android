package com.tink.pfmui.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.model.category.Category
import com.tink.model.misc.ExactNumber
import com.tink.model.transaction.Transaction
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.overview.charts.CategorySelectionFragment
import com.tink.pfmui.overview.charts.CategorySelectionListener
import se.tink.commons.extensions.isBiggerThan

private const val ARG_TRANSACTION_ID = "arg_transaction_id"

internal class CategorizationFlowFragment : BaseFragment(), CategorySelectionListener {

    override fun getLayoutId(): Int = R.layout.fragment_category_selection_flow
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
                    showSimilarTransactionsOnReturn(it.updatedCategoryId)

                is CategorizationFlowViewModel.State.Done -> fragmentCoordinator.popBackStack()
            }
        })
    }

    private fun showCategoryPickerView(transaction: Transaction) {

        val zero = ExactNumber(0, 0)

        val categoryType =
            if (transaction.amount.value.isBiggerThan(zero)) {
                Category.Type.INCOME
            } else {
                Category.Type.EXPENSE
            }

        CategorySelectionFragment
            .newInstance(
                categoryType,
                transaction.categoryId,
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

    override fun onCategorySelected(updatedCategoryId: String) =
        viewModel.categorySelected(updatedCategoryId)

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
