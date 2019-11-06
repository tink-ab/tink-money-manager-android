package com.tink.pfmsdk.transaction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.overview.charts.CategorySelectionFragment
import com.tink.pfmsdk.overview.charts.CategorySelectionListener
import se.tink.core.models.Category
import se.tink.core.models.misc.ExactNumber
import se.tink.core.models.transaction.Transaction

private const val ARG_TRANSACTION_ID = "arg_transaction_id"

class CategorizationFlowFragment : BaseFragment(), CategorySelectionListener {

    override fun getLayoutId(): Int = R.layout.fragment_category_selection_flow
    override fun needsLoginToBeAuthorized(): Boolean = true

    private var updatedCategoryCode: String? = null
    private val transactionId: String by lazy { requireNotNull(arguments?.getString(ARG_TRANSACTION_ID)) }

    lateinit var viewModel: CategorizationFlowViewModel

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, viewModelFactory)[CategorizationFlowViewModel::class.java]
            .also { it.setTransactionId(transactionId) }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            showCategoryPickerView(it)
        })
    }


    private fun showCategoryPickerView(transaction: Transaction) {

        // TODO: PFMSDK: Fix logic to show category selection fragment

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
            .apply { setTargetFragment(this@CategorizationFlowFragment, 0) }
            .also { fragmentCoordinator.replace(it) }

        showSimilarTransactionsOnReturn()
    }

    /**
     *
     * Pre-load similar transactions. The LiveData-framework and the lifecycleOwner will make sure
     *that the result is only fired when returning back to this fragment
     */
    private fun showSimilarTransactionsOnReturn() {

        // TODO: PFMSDK: Show similar transactions upon selection of category
        val similarTransactionsLiveData = viewModel.fetchSimilarTransactions()

        similarTransactionsLiveData?.observe(this, object : Observer<List<Transaction>?> {

            override fun onChanged(list: List<Transaction>?) {
                list?.let {
                    similarTransactionsLiveData.removeObserver(this)
                    if (list.isNotEmpty()) {
                        updatedCategoryCode?.let {
                            showSimilarTransactionFragment(list, it)
                            updatedCategoryCode = null
                        }
                    }
                }
            }
        })
    }

    override fun onCategorySelected(updatedCategoryCode: String) {

        val transaction = viewModel.transaction.value ?: return

        if (updatedCategoryCode == transaction.categoryCode) {
            return
        }

        this.updatedCategoryCode = updatedCategoryCode

        updateTransactionCategory(updatedCategoryCode)
    }

    private fun updateTransactionCategory(updatedCategoryCode: String) {
        val transaction = viewModel.transaction.value ?: return

        viewModel.categorizeTransactions(listOf(transaction.id), updatedCategoryCode)
    }

    private fun showSimilarTransactionFragment(transactions: List<Transaction>, code: String) {
        fragmentCoordinator.replace(
            SimilarTransactionsFragment.newInstance(transactions, code)
        )
    }

    companion object {
        fun newInstance(transactionId: String) =
            CategorizationFlowFragment().apply {
                arguments = bundleOf(ARG_TRANSACTION_ID to transactionId)
            }
    }
}
