package com.tink.moneymanagerui.budgets.creation.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.BudgetCreationNavigation
import com.tink.moneymanagerui.budgets.creation.di.BudgetCreationViewModelFactory
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.util.SoftKeyboardUtils
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_search.*
import javax.inject.Inject

internal class BudgetCreationSearchFragment : BaseFragment() {
    companion object {
        fun newInstance() = BudgetCreationSearchFragment()
    }

    @Inject
    internal lateinit var navigation: BudgetCreationNavigation

    @Inject
    internal lateinit var budgetCreationViewModelFactory: BudgetCreationViewModelFactory

    lateinit var viewModel: BudgetCreationSearchViewModel

    val adapter: BudgetCreationSearchSuggestionsAdapter = BudgetCreationSearchSuggestionsAdapter()

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_creation_search
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.CREATE_BUDGET
    override fun hasToolbar(): Boolean = true

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            budgetCreationViewModelFactory
        )[BudgetCreationSearchViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        val searchHeader = SearchHeader(context!!)
        searchHeader.onTextChangedListener = {
            viewModel.searchText.postValue(it.toString())
        }
        searchHeader.onEditorActionListener = {
            viewModel.freeTextQuery.postValue(it.toString())
            SoftKeyboardUtils.closeSoftKeyboard(activity)
            navigation.goToSpecificationFragment()
            true
        }
        toolbar?.setCustomView(searchHeader)
        searchHeader.focus()

        adapter.onItemClickedListener = {
            val tagName = it.replace("#", "")
            viewModel.selectedTag.postValue(tagName)
            SoftKeyboardUtils.closeSoftKeyboard(activity)
            navigation.goToSpecificationFragment()
        }

        searchSuggestionsList.layoutManager = LinearLayoutManager(context)
        searchSuggestionsList.adapter = adapter

        viewModel.suggestions.observe(viewLifecycle, Observer { suggestions ->
            if (suggestions != null) {
                adapter.setData(suggestions)
            }
        })
    }

    override fun onDestroyView() {
        SoftKeyboardUtils.closeSoftKeyboard(activity)
        toolbar?.clearCustomView()
        super.onDestroyView()
    }
}