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
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.details.di.BudgetDetailsViewModelFactory
import com.tink.moneymanagerui.databinding.TinkBudgetDetailsBarChartPageBinding
import com.tink.moneymanagerui.databinding.TinkBudgetDetailsProgressChartPageBinding
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.*
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.periodPicker
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.view.*
import kotlinx.android.synthetic.main.tink_fragment_budget_transactions_list.*
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


        viewModel.budgetDetailsDataState.observe(viewLifecycleOwner) { responseState ->
            view.loadingSpinner.visibleIf { responseState is LoadingState }
            view.periodPicker.visibleIf { responseState !is LoadingState }
        }

        viewModel.statusMessage.observe(viewLifecycleOwner, Observer { message ->
            view.statusMessage.text = message
        })

        viewModel.visibilityState.observe(viewLifecycleOwner, Observer { visibilityState ->
            view.showTransactionsBtn.visibleIf { visibilityState.isVisible }
        })

        viewModel.budgetPeriodInterval.observe(viewLifecycleOwner, Observer { budgetPeriodInterval ->
            view.periodPicker.setText(budgetPeriodInterval)
        })

        viewModel.showPickerButtons.observe(viewLifecycleOwner, Observer { showPickerButtons ->
            view.periodPicker.setShowButtons(showPickerButtons)
        })

        viewModel.hasNext.observe(viewLifecycleOwner, Observer { hasNext ->
            view.periodPicker.setNextButtonEnabled(hasNext)
        })

        viewModel.hasPrevious.observe(viewLifecycleOwner, Observer { hasPrevious ->
            view.periodPicker.setPreviousButtonEnabled(hasPrevious)
        })

        viewModel.apply {
            budgetName.observe(viewLifecycle, { name ->
                name?.let {
                    title = it
                }
            })
            error.observe(viewLifecycle, { event ->
                event.getContentIfNotHandled()?.let { snackbarManager.displayError(it, context) }
            })
            barChartEnabled.observe(viewLifecycle, {
                if (it == true) {
                    viewPager.adapter = ChartPagerAdapter(pageCount = 2)
                    tabs.visibility = View.VISIBLE
                    tabs.setupWithViewPager(viewPager)
                } else {
                    viewPager.adapter = ChartPagerAdapter(pageCount = 1)
                    tabs.visibility = View.GONE
                }
            })
        }

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

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.tink_budget_details_menu)
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_edit_budget) {
            viewModel.budget.value?.let { budget ->
                (parentFragment as BudgetDetailsFragment).editBudget(budget)
                return true
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
                val budgetDetailsProgressChartPage: TinkBudgetDetailsProgressChartPageBinding = TinkBudgetDetailsProgressChartPageBinding.inflate(layoutInflater, container, true)

                viewModel.budgetDetailsDataState.observe(this@BudgetDetailsChartFragment.viewLifecycle) { responseState ->
                    when(responseState) {
                        is SuccessState -> {
                            budgetDetailsProgressChartPage.budgetHeader.text = responseState.data.headerText
                            budgetDetailsProgressChartPage.amountLeft.text = responseState.data.amountLeft
                            budgetDetailsProgressChartPage.totalAmount.text =
                                requireContext().getString(R.string.tink_budget_details_total_amount, responseState.data.totalAmount)
                            budgetDetailsProgressChartPage.budgetProgress.progress = responseState.data.progress
                            budgetDetailsProgressChartPage.budgetProgress.setProgressArcColor(
                                responseState.data.amountLeftColor, MoneyManagerFeatureType.BUDGETS)
                        }
                        is LoadingState -> Unit // TODO
                        is ErrorState -> Unit // TODO
                    }
                }

                viewModel.visibilityState.observe(this@BudgetDetailsChartFragment.viewLifecycle, { visibilityState ->
                    budgetDetailsProgressChartPage.budgetProgress.visibleIf { visibilityState.isVisible }
                })
                return budgetDetailsProgressChartPage.root
            } else {
                val budgetDetailsBarChartPage = TinkBudgetDetailsBarChartPageBinding.inflate(layoutInflater, container, true)

                viewModel.barChartEmptyMessage.observe(this@BudgetDetailsChartFragment.viewLifecycle, { message ->
                    budgetDetailsBarChartPage.emptyChartMessage.text = message
                })
                viewModel.barChartEmpty.observe(this@BudgetDetailsChartFragment.viewLifecycle, { barChartEmpty ->
                    budgetDetailsBarChartPage.budgetProgress.visibleIf { !barChartEmpty }
                    budgetDetailsBarChartPage.emptyChartMessage.visibleIf { barChartEmpty }
                })
                viewModel.activeBudgetPeriodsCount.observe(this@BudgetDetailsChartFragment.viewLifecycle, { activeBudgetPeriodsCount ->
                    budgetDetailsBarChartPage.budgetProgress.activeBarCount = activeBudgetPeriodsCount
                })
                viewModel.periodChartThreshold.observe(this@BudgetDetailsChartFragment.viewLifecycle, { periodChartThreshold ->
                    budgetDetailsBarChartPage.budgetProgress.threshold = periodChartThreshold
                })
                viewModel.historicPeriodData.observe(this@BudgetDetailsChartFragment.viewLifecycle, { historicPeriodData ->
                    budgetDetailsBarChartPage.budgetProgress.data = historicPeriodData
                })
                viewModel.periodChartLabels.observe(this@BudgetDetailsChartFragment.viewLifecycle, { periodChartLabels ->
                    budgetDetailsBarChartPage.budgetProgress.labels = periodChartLabels
                })
                viewModel.periodChartThresholdLabel.observe(this@BudgetDetailsChartFragment.viewLifecycle, { periodChartThresholdLabel ->
                    budgetDetailsBarChartPage.budgetProgress.thresholdLabel = periodChartThresholdLabel
                })
                viewModel.budgetPeriodicity.observe(this@BudgetDetailsChartFragment.viewLifecycle, { budgetPeriodicity ->
                    periodicityTitle = context?.getString(
                        when((budgetPeriodicity as? Budget.Periodicity.Recurring)?.unit) {
                            Budget.Periodicity.Recurring.PeriodUnit.WEEK -> R.string.tink_budget_details_selector_weekly
                            Budget.Periodicity.Recurring.PeriodUnit.YEAR -> R.string.tink_budget_details_selector_yearly
                            else -> R.string.tink_budget_details_selector_monthly
                        }
                    ).orEmpty()
                    notifyDataSetChanged()
                })

                return budgetDetailsBarChartPage.root
            }
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
