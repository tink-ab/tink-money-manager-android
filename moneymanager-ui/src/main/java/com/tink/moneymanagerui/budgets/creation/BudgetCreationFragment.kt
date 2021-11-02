package com.tink.moneymanagerui.budgets.creation

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.tink.model.budget.BudgetFilter
import com.tink.model.budget.BudgetPeriodicity
import com.tink.model.budget.BudgetSpecification
import com.tink.model.misc.Amount
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.filterselection.BudgetCreationFilterSelectionFragment
import com.tink.moneymanagerui.budgets.creation.search.BudgetCreationSearchFragment
import com.tink.moneymanagerui.budgets.creation.specification.BudgetCreationSpecificationFragment
import com.tink.moneymanagerui.budgets.details.BudgetDetailsFragment
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
            fragment.arguments = bundleOf(BUDGET_OPERATION to budgetOperation,)
            return fragment
        }

        @JvmStatic
        fun newInstance(
            name: String? = null,
            amount: Amount? = null,
            budgetFilter: BudgetFilter? = null,
            budgetPeriodicity: BudgetPeriodicity? = null
        ): BudgetCreationFragment {
            return BudgetCreationFragment().apply {
                arguments = bundleOf(
                    BUDGET_OPERATION to BudgetCreateOperation(name, amount, budgetFilter, budgetPeriodicity),
                )
            }
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

    override fun getMoneyManagerFeatureType(): MoneyManagerFeatureType? {
        return MoneyManagerFeatureType.BUDGETS
    }

    private fun BudgetCreateOperation.setDataForPrefill() {
        name?.let { dataHolder.name.value = it }
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

    fun goToFilterSelectionFragment(addToBackStack: Boolean = false) {
        fragmentCoordinator.replace(
            fragment = BudgetCreationFilterSelectionFragment.newInstance(),
            addToBackStack = addToBackStack,
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
    val name: String? = null,
    val amount: Amount? = null,
    val filter: BudgetFilter? = null,
    val periodicity: BudgetPeriodicity? = null
) : Parcelable
