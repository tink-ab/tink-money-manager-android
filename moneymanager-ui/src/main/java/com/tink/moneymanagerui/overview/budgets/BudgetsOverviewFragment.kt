package com.tink.moneymanagerui.overview.budgets

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.BudgetCreationFragment
import com.tink.moneymanagerui.budgets.details.BudgetDetailsFragment
import kotlinx.android.synthetic.main.tink_fragment_budgets_overview.*
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class BudgetsOverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.tink_fragment_budgets_overview
    override fun needsLoginToBeAuthorized(): Boolean = true

    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var viewModel: BudgetsOverviewViewModel
    private val budgetOverviewListAdapter: BudgetOverviewListAdapter = BudgetOverviewListAdapter()

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)[BudgetsOverviewViewModel::class.java]

        budgetOverviewListAdapter.onItemClickedListener = { budgetId ->
            fragmentCoordinator.replace(BudgetDetailsFragment.newInstance(budgetId))
        }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        budgetsOverviewList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = budgetOverviewListAdapter
        }

        newBudgetButton.setOnClickListener {
            fragmentCoordinator.replace(BudgetCreationFragment.newInstance())
        }

        budgetsEmptyIcon.setOnClickListener {
            fragmentCoordinator.replace(BudgetCreationFragment.newInstance())
        }

        emptyCreateNewButton.setOnClickListener {
            fragmentCoordinator.replace(BudgetCreationFragment.newInstance())
        }

        viewModel.budgetItems.observe(viewLifecycleOwner, {
            budgetOverviewListAdapter.setItems(it)
        })

        viewModel.hasBudgets.observe(viewLifecycleOwner, { hasBudgets ->
            //TransitionManager.beginDelayedTransition(view as ViewGroup)
            if (hasBudgets) {
                budgetsEmpty.visibility = View.GONE
                budgetsOverviewContent.visibility = View.VISIBLE
            } else {
                budgetsOverviewContent.visibility = View.GONE
                budgetsEmpty.visibility = View.VISIBLE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { loading ->
            //TransitionManager.beginDelayedTransition(view as ViewGroup)
            loader.visibility = if(loading) View.VISIBLE else View.GONE
        })
    }
}
