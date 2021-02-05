package com.tink.pfmui.budgets.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.budgets.details.di.BudgetDetailsViewModelFactory
import com.tink.pfmui.databinding.TinkBudgetDetailsBarChartPageBinding
import com.tink.pfmui.databinding.TinkBudgetDetailsProgressChartPageBinding
import com.tink.pfmui.databinding.TinkFragmentBudgetDetailsChartBinding
import com.tink.pfmui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.*
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.periodPicker
import kotlinx.android.synthetic.main.tink_fragment_budget_details_chart.view.*
import kotlinx.android.synthetic.main.tink_fragment_budget_transactions_list.*
import kotlinx.android.synthetic.main.tink_item_picker.view.iconLeft
import kotlinx.android.synthetic.main.tink_item_picker.view.iconRight
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

        DataBindingUtil.bind<TinkFragmentBudgetDetailsChartBinding>(view.root)
            ?.also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
            }

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
                    pagingIndicator.initialize(viewPager)
                } else {
                    viewPager.adapter = ChartPagerAdapter(pageCount = 1)
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
        override fun isViewFromObject(view: View, obj: Any) = obj == view
        override fun getCount(): Int = pageCount

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (position == 0) {
                val binding = DataBindingUtil.inflate<TinkBudgetDetailsProgressChartPageBinding>(
                    layoutInflater,
                    R.layout.tink_budget_details_progress_chart_page,
                    container,
                    true
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = this@BudgetDetailsChartFragment.viewLifecycle
                return binding.root
            } else {
                val binding = DataBindingUtil.inflate<TinkBudgetDetailsBarChartPageBinding>(
                    layoutInflater,
                    R.layout.tink_budget_details_bar_chart_page,
                    container,
                    true
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = this@BudgetDetailsChartFragment.viewLifecycle
                return binding.root
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
            container.removeView(obj as View)
    }

    override fun onDestroyView() {
        viewPager?.clearOnPageChangeListeners()
        super.onDestroyView()
    }
}
