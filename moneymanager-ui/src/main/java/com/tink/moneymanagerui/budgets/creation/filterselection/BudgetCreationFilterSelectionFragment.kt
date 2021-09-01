package com.tink.moneymanagerui.budgets.creation.filterselection

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.BudgetCreationNavigation
import com.tink.moneymanagerui.budgets.creation.di.BudgetCreationViewModelFactory
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.view.TreeListMultiSelectionAdapter
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_filter_selection.*
import javax.inject.Inject

internal class BudgetCreationFilterSelectionFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = BudgetCreationFilterSelectionFragment()
    }

    @Inject
    internal lateinit var navigation: BudgetCreationNavigation

    @Inject
    internal lateinit var budgetCreationViewModelFactory: BudgetCreationViewModelFactory

    internal lateinit var viewModel: BudgetCreationFilterSelectionViewModel

    internal val adapter: TreeListMultiSelectionAdapter = TreeListMultiSelectionAdapter()

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_creation_filter_selection
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.CREATE_BUDGET
    override fun hasToolbar(): Boolean = true

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            budgetCreationViewModelFactory
        )[BudgetCreationFilterSelectionViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        title = getString(if (viewModel.isEditing) {
            R.string.tink_budget_edit_toolbar_title
        } else {
            R.string.tink_budget_create_title
        })

        viewModel.showActionButton.observe(viewLifecycle, { shouldShowActionButton ->
            actionButton.visibleIf { shouldShowActionButton }
        })

        adapter.onSelectionListener = viewModel.selectedTreeListItems::postValue

        filterSelectionList.layoutManager = LinearLayoutManager(context)
        filterSelectionList.adapter = adapter

        viewModel.filterItems.observe(viewLifecycleOwner, { items ->
            if (items != null) {
                adapter.setMultiSelectionData(items)
            }
        })

        viewModel.searchClicked.observe(viewLifecycle, { event ->
            event.getContentIfNotHandled()?.let { isSearchClicked ->
                if (isSearchClicked) {
                    navigation.goToSearchFragment()
                }
            }
        })

        actionButton.setOnClickListener {
            adapter.selectedItems
                .takeIf { it.isNotEmpty() }
                ?.let {
                    viewModel.onSelectionDone()
                    navigation.goToSpecificationFragment()
                }
        }
    }
}