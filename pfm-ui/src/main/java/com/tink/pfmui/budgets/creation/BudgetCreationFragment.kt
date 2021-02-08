package com.tink.pfmui.budgets.creation

import android.os.Bundle
import com.tink.model.budget.BudgetSpecification
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.FragmentAnimationFlags
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.filterselection.BudgetCreationFilterSelectionFragment
import com.tink.pfmui.budgets.creation.search.BudgetCreationSearchFragment
import com.tink.pfmui.budgets.creation.specification.BudgetCreationSpecificationFragment
import com.tink.pfmui.budgets.details.BudgetDetailsFragment
import javax.inject.Inject

private const val BUDGET_ID = "BUDGET_ID"
private const val BUDGET_NAME = "BUDGET_NAME"
private const val AMOUNT = "AMOUNT"
private const val FILTER = "FILTER"
private const val PERIODICITY = "PERIODICITY"

internal class BudgetCreationFragment : BaseFragment() {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun newInstance(budgetSpecification: BudgetSpecification? = null): BudgetCreationFragment {
            val fragment = BudgetCreationFragment()
            if (budgetSpecification != null) {
                fragment.arguments = Bundle().apply {
                    putString(BUDGET_ID, budgetSpecification.id)
                    putString(BUDGET_NAME, budgetSpecification.name)
                    putParcelable(AMOUNT, budgetSpecification.amount)
                    putParcelable(FILTER, budgetSpecification.filter)
                    putParcelable(PERIODICITY, budgetSpecification.periodicity)
                }
            }
            return fragment
        }
    }

    @Inject
    internal lateinit var navigation: BudgetCreationNavigation

    @Inject
    internal lateinit var dataHolder: BudgetCreationDataHolder

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_creation
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun hasToolbar(): Boolean = false
    override fun doNotRecreateView(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        arguments?.run {
            dataHolder.id.value = requireNotNull(getString(BUDGET_ID))
            dataHolder.name.value = requireNotNull(getString(BUDGET_NAME))
            dataHolder.amount.value = requireNotNull(getParcelable(AMOUNT))
            dataHolder.selectedFilter.value = requireNotNull(getParcelable(FILTER))
            dataHolder.periodicity.value = requireNotNull(getParcelable(PERIODICITY))
        }

        val isEditing = dataHolder.id.value != null

        if (isEditing) {
            navigation.goToSpecificationFragment(false)
        } else {
            navigation.goToFilterSelectionFragment()
        }
    }

    override fun onBackPressed(): Boolean = navigation.handleBackPress()

    fun goToDetailsFragment(budgetId: String) {
        popToOverview()
        fragmentCoordinator.replace(BudgetDetailsFragment.newInstance(budgetId))
    }

    private fun popToOverview() {
        fragmentCoordinator.backToMainScreen()
    }
}

internal class BudgetCreationNavigation(private val fragmentCoordinator: FragmentCoordinator) {

    fun goToFilterSelectionFragment() {
        fragmentCoordinator.replace(
            fragment = BudgetCreationFilterSelectionFragment.newInstance(),
            addToBackStack = false,
            animation = FragmentAnimationFlags.NONE
        )
    }

    fun goToSpecificationFragment(addToBackStack: Boolean = true) {
        fragmentCoordinator.replace(
            fragment = BudgetCreationSpecificationFragment.newInstance(),
            addToBackStack = addToBackStack
        )
    }

    fun handleBackPress(): Boolean {
        return if (fragmentCoordinator.backStackEntryCount > 0) {
            // Navigate back within the budgets creation flow
            fragmentCoordinator.popBackStack()
            true
        } else {
            // Navigate back to overview
            false
        }
    }

    fun goToSearchFragment() {
        fragmentCoordinator.replace(fragment = BudgetCreationSearchFragment.newInstance())
    }
}