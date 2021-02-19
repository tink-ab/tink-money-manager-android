package com.tink.pfmui.budgets.creation

import android.os.Bundle
import android.os.Parcelable
import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.budget.BudgetSpecification
import com.tink.model.misc.Amount
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.FragmentAnimationFlags
import com.tink.pfmui.FragmentCoordinator
import com.tink.pfmui.R
import com.tink.pfmui.budgets.creation.filterselection.BudgetCreationFilterSelectionFragment
import com.tink.pfmui.budgets.creation.search.BudgetCreationSearchFragment
import com.tink.pfmui.budgets.creation.specification.BudgetCreationSpecificationFragment
import com.tink.pfmui.budgets.details.BudgetDetailsFragment
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

private const val BUDGET_OPERATION = "BUDGET_OPERATION"

internal class BudgetCreationFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance(budgetSpecification: BudgetSpecification? = null): BudgetCreationFragment {
            val fragment = BudgetCreationFragment()
            val budgetOperation = if (budgetSpecification != null) {
                BudgetEditOperation(
                    budgetSpecification.id,
                    budgetSpecification.name,
                    budgetSpecification.amount,
                    budgetSpecification.filter,
                    budgetSpecification.periodicity
                )
            } else {
                BudgetCreateOperation()
            }
            fragment.arguments = Bundle().apply { putParcelable(BUDGET_OPERATION, budgetOperation) }
            return fragment
        }

        @JvmStatic
        fun newInstance(
            amount: Amount? = null,
            budgetFilter: BudgetFilter? = null,
            budgetPeriodicity: BudgetPeriodicity? = null
        ): BudgetCreationFragment {
            val fragment = BudgetCreationFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(
                    BUDGET_OPERATION,
                    BudgetCreateOperation(amount, budgetFilter, budgetPeriodicity)
                )
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
            when (val operation = requireNotNull(getParcelable(BUDGET_OPERATION))) {
                is BudgetCreateOperation -> operation.setDataForPrefill()
                is BudgetEditOperation -> operation.setDataForEditing()
            }
        }

        val isEditing = dataHolder.id.value != null

        val isCategorySelected = dataHolder.selectedFilter.value?.categories?.isNotEmpty() ?: false

        if (isEditing || isCategorySelected) {
            navigation.goToSpecificationFragment(false)
        } else {
            navigation.goToFilterSelectionFragment()
        }
    }

    private fun BudgetCreateOperation.setDataForPrefill() {
        amount?.let { dataHolder.amount.value = it }
        filter?.let { dataHolder.selectedFilter.value = it }
        periodicity?.let { dataHolder.periodicity.value = it }
    }

    private fun BudgetEditOperation.setDataForEditing() {
        dataHolder.id.value = budgetId
        dataHolder.name.value = budgetName
        dataHolder.amount.value = amount
        dataHolder.selectedFilter.value = filter
        dataHolder.periodicity.value = periodicity
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

@Parcelize
internal data class BudgetEditOperation(
    val budgetId: String,
    val budgetName: String,
    val amount: Amount,
    val filter: BudgetFilter,
    val periodicity: BudgetPeriodicity
) : Parcelable

@Parcelize
internal data class BudgetCreateOperation(
    val amount: Amount? = null,
    val filter: BudgetFilter? = null,
    val periodicity: BudgetPeriodicity? = null
) : Parcelable
