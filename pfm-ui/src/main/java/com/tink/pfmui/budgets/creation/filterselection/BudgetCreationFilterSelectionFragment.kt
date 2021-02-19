package com.tink.pfmui.budgets.creation.filterselection

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.BudgetCreationNavigation
import com.tink.pfmui.budgets.creation.di.BudgetCreationViewModelFactory
import com.tink.pfmui.databinding.TinkFragmentBudgetCreationFilterSelectionBinding
import com.tink.pfmui.tracking.ScreenEvent
import com.tink.pfmui.view.TreeListSelectionAdapter
import com.tink.pfmui.view.TreeListSelectionItem
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

    internal val adapter: TreeListSelectionAdapter = TreeListSelectionAdapter()

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
            R.string.tink_budget_edit_title
        } else {
            R.string.tink_budget_create_title
        })

        DataBindingUtil.bind<TinkFragmentBudgetCreationFilterSelectionBinding>(view)
            ?.also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
            }

        adapter.onItemSelectedListener = viewModel.selectedTreeListItem::postValue

        filterSelectionList.layoutManager = LinearLayoutManager(context)
        filterSelectionList.adapter = adapter

        viewModel.filterItems.observe(viewLifecycle, { items ->
            if (items != null) {
                // Fetch a TreeFilterSelectionItem from the pre-selected filter, if we have one.
                val selectedFilter = viewModel.selectedFilter?.categories?.firstOrNull()?.code
                    ?.let { categoryCode ->
                        items
                            .flatMap {
                                if (it is TreeListSelectionItem.TopLevelItem) {
                                    listOf(it) + it.children
                                } else {
                                    listOf(it)
                                }
                            }
                            .find { it.id == categoryCode }
                    }
                adapter.setData(items, selectedFilter)
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
            adapter.selectedItem?.let {
                navigation.goToSpecificationFragment()
            }
        }
    }
}