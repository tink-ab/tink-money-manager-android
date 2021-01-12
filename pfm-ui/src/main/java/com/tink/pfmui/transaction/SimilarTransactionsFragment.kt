package com.tink.pfmui.transaction

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.model.transaction.Transaction
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.tracking.ScreenEvent
import com.tink.pfmui.view.TinkSnackbar
import kotlinx.android.synthetic.main.tink_transaction_similar_fragment.view.*
import se.tink.commons.transactions.SimilarTransactionsAdapter
import javax.inject.Inject
import javax.inject.Named

internal class SimilarTransactionsFragment : BaseFragment() {

    @Inject
    @field:Named(TinkSnackbar.Theme.ERROR_THEME)
    lateinit var errorSnackbarTheme: TinkSnackbar.Theme

    var onSimilarTransactionsDone: (() -> Unit)? = null

    private lateinit var viewModel: SimilarTransactionsViewModel

    private val transactions: List<Transaction> by lazy {
        requireNotNull(
            arguments?.getParcelableArrayList<Transaction>(
                TRANSACTION_SIMILAR_TRANSACTIONS
            )
        ).toList().sortedWith(compareByDescending<Transaction> { it.date }.thenBy { it.id })
    }

    private val newCategoryCode by lazy {
        requireNotNull(arguments?.getString(TRANSACTION_SIMILAR_CATEGORY_ID))
    }

    private val adapter = SimilarTransactionsAdapter()

    override fun getLayoutId(): Int = R.layout.tink_transaction_similar_fragment
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String? = getString(R.string.tink_transaction_similar_title)
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.SIMILAR_TRANSACTIONS
    override fun doNotRecreateView(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
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
                            snackbarManager.displayError(error, it, errorSnackbarTheme)
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


        }
        viewModel.apply {
            similarTransactionItems.observe(viewLifecycleOwner, Observer { items ->
                adapter.setData(items)
            })
            markButtonText.observe(viewLifecycleOwner, Observer {
                invalidateToolbarMenu()
            })
        }
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.tink_menu_similar_transactions)
        viewModel.markButtonText.value?.let {
            toolbar.menu.findItem(R.id.menu_item_marker_button).setTitle(it)
        }
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_marker_button) {
            adapter.toggleMarked()
            return true
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        onSimilarTransactionsDone?.invoke()
        return super.onBackPressed()
    }

    override fun onUpPressed() {
        onSimilarTransactionsDone?.invoke()
        super.onUpPressed()
    }

    companion object {

        private const val TRANSACTION_SIMILAR_CATEGORY_ID = "transaction_similar_category_id"
        private const val TRANSACTION_SIMILAR_TRANSACTIONS = "transaction_similar_transactions"

        @JvmStatic
        fun newInstance(
            transactions: List<Transaction>,
            newCategoryId: String
        ): SimilarTransactionsFragment {
            val transactionsArray = ArrayList(transactions)
            return SimilarTransactionsFragment().apply {
                arguments = Bundle().apply {
                    putString(TRANSACTION_SIMILAR_CATEGORY_ID, newCategoryId)
                    putParcelableArrayList(TRANSACTION_SIMILAR_TRANSACTIONS, transactionsArray)
                }
            }
        }
    }
}

