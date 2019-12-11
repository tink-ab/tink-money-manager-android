package com.tink.pfmsdk.transaction

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ScreenEvent
import com.tink.pfmsdk.collections.Categories
import com.tink.pfmsdk.view.TinkSnackbar
import kotlinx.android.synthetic.main.transaction_similar_fragment.*
import kotlinx.android.synthetic.main.transaction_similar_fragment.view.*
import se.tink.commons.transactions.SimilarTransactionsAdapter
import se.tink.core.models.Category
import se.tink.core.models.transaction.Transaction
import javax.inject.Inject

class SimilarTransactionsFragment : BaseFragment() {

    var onSimilarTransactionsDone: (() -> Unit)? = null

    @Inject
    lateinit var ownTheme: Theme

    private lateinit var viewModel: SimilarTransactionsViewModel

    private val transactions: List<Transaction> by lazy {
        requireNotNull(
            arguments?.getParcelableArrayList<Transaction>(TRANSACTION_SIMILAR_TRANSACTIONS)
        ).toList().sorted()
    }

    private val newCategoryCode by lazy {
        requireNotNull(arguments?.getString(TRANSACTION_SIMILAR_CATEGORY_CODE))
    }

    private val adapter = SimilarTransactionsAdapter()

    override fun getLayoutId(): Int = R.layout.transaction_similar_fragment
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String? = getString(R.string.transaction_similar_title)
    override fun getTheme(): Theme? = ownTheme
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRANSACTIONS_SIMILAR
    override fun doNotRecreateView(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        ownTheme.setCategory(Categories.getSharedInstance().getCategory(newCategoryCode))
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        )[SimilarTransactionsViewModel::class.java].apply {
            initialize(transactions, newCategoryCode)
        }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        with(view) {
            recyclerView.adapter = adapter
            adapter.onMarkedStateChangeListener = { marked -> viewModel.updateMarkedState(marked) }
            adapter.markedTransactionsChangedListener = { markedTransactions ->
                viewModel.updateMarkedTransactions(markedTransactions)
            }
            doneBtn.setOnClickListener {
                viewModel.updateTransactions(
                    transactionIDs = adapter.selectedTransactions,
                    categoryCode = newCategoryCode,
                    onError = { error ->
                        context?.let {
                            snackbarManager.displayError(error, it, ownTheme.snackbarErrorTheme)
                        }
                    }
                )
                onSimilarTransactionsDone?.invoke()
                fragmentCoordinator.popBackStack()
            }
            skipBtn.setOnClickListener {
                onSimilarTransactionsDone?.invoke()
                fragmentCoordinator.popBackStack()
            }


            markerButton.setOnClickListener { adapter.toggleMarked() }
        }
        viewModel.apply {
            similarTransactionItems.observe(viewLifecycleOwner, Observer { items ->
                adapter.setData(items)
            })
            markButtonText.observe(viewLifecycleOwner, Observer { buttonText ->
                buttonText?.let { markerButton.text = it }
            })
        }
    }

    override fun onBackPressed(): Boolean {
        onSimilarTransactionsDone?.invoke()
        return super.onBackPressed()
    }

    override fun onUpPressed() {
        onSimilarTransactionsDone?.invoke()
        super.onUpPressed()
    }

    interface Theme : BaseFragment.Theme {

        val snackbarErrorTheme: TinkSnackbar.Theme

        fun setCategory(category: Category)
    }

    companion object {

        private const val TRANSACTION_SIMILAR_CATEGORY_CODE = "transaction_similar_category_code"
        private const val TRANSACTION_SIMILAR_TRANSACTIONS = "transaction_similar_transactions"

        @JvmStatic
        fun newInstance(
            transactions: List<Transaction>,
            newCategoryCode: String
        ): SimilarTransactionsFragment {
            val transactionsArray = ArrayList(transactions)
            return SimilarTransactionsFragment().apply {
                arguments = Bundle().apply {
                    putString(TRANSACTION_SIMILAR_CATEGORY_CODE, newCategoryCode)
                    putParcelableArrayList(TRANSACTION_SIMILAR_TRANSACTIONS, transactionsArray)
                }
            }
        }
    }
}

