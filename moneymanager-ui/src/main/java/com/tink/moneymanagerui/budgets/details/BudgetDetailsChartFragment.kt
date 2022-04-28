package com.tink.moneymanagerui.budgets.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetPeriodicity
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.details.di.BudgetDetailsViewModelFactory
import com.tink.moneymanagerui.databinding.TinkBudgetDetailsBarChartPageBinding
import com.tink.moneymanagerui.databinding.TinkBudgetDetailsProgressChartPageBinding
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.util.extensions.formatCurrencyRound
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.*
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.view.*
import kotlinx.android.synthetic.main.tink_item_picker.view.*
import javax.inject.Inject

internal class BudgetDetailsChartFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance() = BudgetDetailsChartFragment()
    }

    @Inject
    internal lateinit var budgetDetailsNavigation: BudgetDetailsNavigation

    internal lateinit var viewModel: BudgetDetailsViewModel

    private val onePageAdapter = ChartPagerAdapter(pageCount = 1)
    private val twoPageAdapter = ChartPagerAdapter(pageCount = 2)

    @Inject
    internal lateinit var budgetDetailsViewModelFactory: BudgetDetailsViewModelFactory

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_details_chart
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getTitle(): String = getString(R.string.tink_budget_details_toolbar_title)
    override fun doNotRecreateView(): Boolean = false
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.BUDGET_DETAILS
    override fun hasToolbar(): Boolean = true

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            budgetDetailsViewModelFactory
        )[BudgetDetailsViewModel::class.java]

        viewModel.budgetDetailsData.observe(viewLifecycleOwner) { responseState ->
            view.loadingSpinner.visibleIf { responseState is LoadingState }
            view.periodPicker.visibleIf { responseState is SuccessState }
            view.showTransactionsBtn.visibleIf { responseState is SuccessState }
            view.statusMessage.visibleIf { responseState is SuccessState }
            setBarChartVisibility(responseState)
            view.periodPicker.setShowButtons(responseState is SuccessState && responseState.data.showPickerButtons)

            when (responseState) {
                is SuccessState -> {
                    title = responseState.data.budget.name
                    view.periodPicker.setText(responseState.data.budgetPeriodIntervalText)
                    view.statusMessage.text = responseState.data.statusMessage
                }
                is LoadingState -> Unit
                is ErrorState -> {
                    snackbarManager.displayError(R.string.tink_snackbar_utils_error_default, context)
                }
            }
        }

        viewModel.hasNext.observe(
            viewLifecycleOwner,
            Observer { hasNext ->
                view.periodPicker.setNextButtonEnabled(hasNext)
            }
        )

        viewModel.hasPrevious.observe(
            viewLifecycleOwner,
            Observer { hasPrevious ->
                view.periodPicker.setPreviousButtonEnabled(hasPrevious)
            }
        )

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.isBarChartShowing.postValue(position == 1)
            }
        })

        showTransactionsBtn.setOnClickListener {
            // Go to transactions list. Fetch transactions here or in the BudgetTransactionsListFragment?
            budgetDetailsNavigation.openTransactionsList()
        }

        periodPicker.iconLeft.setOnClickListener {
            viewModel.showPreviousPeriod()
        }
        periodPicker.iconRight.setOnClickListener {
            viewModel.showNextPeriod()
        }
    }

    private fun setBarChartVisibility(responseState: ResponseState<BudgetDetailsData>?) {
        (responseState as? SuccessState)?.data?.barChartEnabled?.let { enabled ->
            tabs.visibleIf { enabled }

            if (enabled) {
                if (viewPager.adapter != twoPageAdapter) {
                    viewPager.adapter = twoPageAdapter
                    tabs.setupWithViewPager(viewPager)
                }
            } else if (viewPager.adapter != onePageAdapter) {
                viewPager.adapter = onePageAdapter
            }
        }
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.tink_budget_details_menu)
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_edit_budget) {
            viewModel.budgetDetailsData.value?.let { budgetState ->
                if (budgetState is SuccessState) {
                    (parentFragment as BudgetDetailsFragment).editBudget(budgetState.data.budget)
                    return true
                }
            }
        } else if (item.itemId == android.R.id.home) {
            budgetDetailsNavigation.handleUpPressed()
            return true
        }
        return super.onToolbarMenuItemSelected(item)
    }

    inner class ChartPagerAdapter(val pageCount: Int) : PagerAdapter() {
        private var periodicityTitle: String = ""

        override fun isViewFromObject(view: View, obj: Any) = obj == view
        override fun getCount(): Int = pageCount

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (position == 0) {
                val budgetDetailsProgressChartPage: TinkBudgetDetailsProgressChartPageBinding =
                    TinkBudgetDetailsProgressChartPageBinding.inflate(layoutInflater, container, true)

                viewModel.budgetDetailsData.observe(this@BudgetDetailsChartFragment.viewLifecycle) { responseState ->
                    budgetDetailsProgressChartPage.budgetProgress.visibleIf { responseState is SuccessState }
                    budgetDetailsProgressChartPage.amountLeft.visibleIf { responseState is SuccessState }
                    budgetDetailsProgressChartPage.totalAmount.visibleIf { responseState is SuccessState }

                    when (responseState) {
                        is SuccessState -> {
                            budgetDetailsProgressChartPage.budgetHeader.text = responseState.data.headerText
                            budgetDetailsProgressChartPage.amountLeft.text = responseState.data.amountLeft
                            budgetDetailsProgressChartPage.totalAmount.text =
                                requireContext().getString(R.string.tink_budget_details_total_amount, responseState.data.totalAmount)
                            budgetDetailsProgressChartPage.budgetProgress.progress = responseState.data.progress
                            budgetDetailsProgressChartPage.budgetProgress.setProgressArcColor(
                                responseState.data.amountLeftColor, MoneyManagerFeatureType.BUDGETS
                            )
                        }
                        is LoadingState -> Unit
                        is ErrorState -> Unit
                    }
                }

                return budgetDetailsProgressChartPage.root
            } else {
                val budgetDetailsBarChartPage = TinkBudgetDetailsBarChartPageBinding.inflate(layoutInflater, container, true)

                viewModel.budgetDetailsData.observe(this@BudgetDetailsChartFragment.viewLifecycle) { responseState ->
                    when (responseState) {
                        is SuccessState -> {
                            budgetDetailsBarChartPage.budgetProgress.visibleIf { !responseState.data.barChartEmpty }
                            budgetDetailsBarChartPage.emptyChartMessage.visibleIf { responseState.data.barChartEmpty }

                            budgetDetailsBarChartPage.budgetProgress.threshold =
                                responseState.data.budget.amount.value.toBigDecimal().toFloat()
                            setPeriodicityTitle(responseState.data.budget.periodicity)
                            budgetDetailsBarChartPage.budgetProgress.thresholdLabel =
                                responseState.data.budget.amount.formatCurrencyRound()
                            budgetDetailsBarChartPage.budgetProgress.labels = responseState.data.periodChartLabels
                            budgetDetailsBarChartPage.budgetProgress.activeBarCount = responseState.data.activeBudgetPeriodsCount
                            budgetDetailsBarChartPage.emptyChartMessage.text = responseState.data.barChartEmptyMessage
                            budgetDetailsBarChartPage.budgetProgress.data = responseState.data.budgetPeriodsData
                            budgetDetailsBarChartPage.budgetProgress.labels = responseState.data.periodChartLabels
                        }
                        is LoadingState -> Unit
                        is ErrorState -> Unit
                    }
                }

                return budgetDetailsBarChartPage.root
            }
        }

        private fun setPeriodicityTitle(budgetPeriodicity: BudgetPeriodicity) {
            periodicityTitle = context?.getString(
                when ((budgetPeriodicity as? Budget.Periodicity.Recurring)?.unit) {
                    Budget.Periodicity.Recurring.PeriodUnit.WEEK -> R.string.tink_budget_details_selector_weekly
                    Budget.Periodicity.Recurring.PeriodUnit.YEAR -> R.string.tink_budget_details_selector_yearly
                    else -> R.string.tink_budget_details_selector_monthly
                }
            ).orEmpty()
            notifyDataSetChanged()
        }

        override fun getPageTitle(position: Int) =
            if (position == 0) {
                periodicityTitle
            } else {
                context?.getString(R.string.tink_budget_details_selector_over_time).orEmpty()
            }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
            container.removeView(obj as View)
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.BUDGETS

    override fun onDestroyView() {
        viewPager?.clearOnPageChangeListeners()
        super.onDestroyView()
    }
}
