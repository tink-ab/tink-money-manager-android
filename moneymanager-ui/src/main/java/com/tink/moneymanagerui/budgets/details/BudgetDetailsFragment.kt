package com.tink.moneymanagerui.budgets.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tink.model.budget.BudgetSpecification
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.BudgetCreationFragment
import com.tink.moneymanagerui.budgets.details.transactions.BudgetTransactionsListFragment
import javax.inject.Inject

internal class BudgetDetailsFragment : BaseFragment() {
    companion object {
        const val BUDGET_ID = "budgetId"
        const val PERIOD_START = "periodStart"

        @JvmStatic
        @JvmOverloads
        fun newInstance(budgetId: String, periodStartDate: String? = null): BudgetDetailsFragment {
            return BudgetDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(BUDGET_ID, budgetId)
                    periodStartDate?.let { putString(PERIOD_START, it) }
                }
            }
        }
    }

    @Inject
    internal lateinit var navigation: BudgetDetailsNavigation

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_details
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getTitle(): String = getString(R.string.tink_budget_details_toolbar_title)
    override fun hasToolbar(): Boolean = false
    override fun doNotRecreateView(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        requireNotNull(arguments?.getString(BUDGET_ID))
        navigation.openChartDetails()
    }

    override fun authorizedOnCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        super.authorizedOnCreateView(inflater, container, savedInstanceState)
        snackbarManager.register(view, viewLifecycle.lifecycle)
    }

    override fun onBackPressed(): Boolean = navigation.handleBackPress()
    override fun onUpPressed() { onBackPressed() }

    fun editBudget(budgetSpecification: BudgetSpecification) {
        fragmentCoordinator.replace(BudgetCreationFragment.newInstance(budgetSpecification))
    }
}

internal class BudgetDetailsNavigation(
    private val fragmentCoordinator: FragmentCoordinator,
    private val budgetDetailsFragment: BudgetDetailsFragment
) {

    fun openChartDetails() =
        fragmentCoordinator.replace(
            fragment = BudgetDetailsChartFragment.newInstance(),
            addToBackStack = false, // Not added to back stack since it's the first child fragment.
            animation = FragmentAnimationFlags.NONE
        )

    fun openTransactionsList() =
        fragmentCoordinator.replace(BudgetTransactionsListFragment.newInstance())

    fun handleBackPress(): Boolean {
        return if (fragmentCoordinator.backStackEntryCount > 0) {
            // Navigate back within the budgets details flow
            fragmentCoordinator.popBackStack()
            true
        } else {
            // Navigate back to overview
            false
        }
    }

    fun handleUpPressed() = budgetDetailsFragment.onUpPressed()
}
