package com.tink.moneymanagerui.overview

import android.content.res.Resources
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.StatisticType
import com.tink.moneymanagerui.extensions.onPageSelected
import kotlinx.android.synthetic.main.tink_fragment_overview_chart.view.*
import kotlinx.android.synthetic.main.tink_overview_chart_page.view.*
import com.tink.moneymanagerui.charts.transitions.PieChartLabelTransition
import com.tink.moneymanagerui.charts.transitions.PieChartSegmentTransition
import com.tink.moneymanagerui.databinding.TinkOverviewChartPageBinding
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.overview.charts.ChartDetailsPagerFragment
import com.tink.moneymanagerui.overview.charts.ChartType
import com.tink.moneymanagerui.overview.charts.piechart.addBackSegment
import com.tink.moneymanagerui.overview.charts.piechart.addSegments
import kotlinx.android.synthetic.main.tink_overview_chart_page.*
import se.tink.commons.currency.AmountFormatter
import javax.inject.Inject
import kotlin.math.abs

internal class OverviewChartFragment : BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[OverviewChartViewModel::class.java] }
    private val pageTransformer by lazy { PageTransformer(resources) }

    @Inject
    lateinit var amountFormatter: AmountFormatter

    override fun getLayoutId() = R.layout.tink_fragment_overview_chart
    override fun needsLoginToBeAuthorized() = true
    override fun doNotRecreateView(): Boolean = false
    override fun viewReadyAfterLayout(): Boolean  = false
    override fun hasToolbar(): Boolean = false

    private val statistics: OverviewFeature.Statistics by lazy {
        requireNotNull(arguments?.getParcelable<OverviewFeature.Statistics>(ARG_STATISTICS))
    }

    override fun authorizedOnCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        with(view) {
            with(overviewPager) {
                adapter = ChartPagerAdapter(statistics.statisticTypes)
                offscreenPageLimit = statistics.statisticTypes.size
                setPageTransformer(false, pageTransformer)
            }
            pageIndicator.initialize(overviewPager)
            viewModel.isDoneLoading.observe(viewLifecycleOwner, Observer { isDoneLoading ->
                overviewProgressBar.visibleIf { !isDoneLoading }
                overviewPager.visibleIf { isDoneLoading }
                pageIndicator.visibleIf { isDoneLoading && statistics.statisticTypes.size > 1 }
            })
        }
    }

    private inner class ChartPagerAdapter(val statisticTypes: List<StatisticType>) : PagerAdapter() {
        override fun isViewFromObject(view: View, obj: Any) = obj == view
        override fun getCount() = statisticTypes.size
        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) = container.removeView(obj as View)

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = TinkOverviewChartPageBinding.inflate(layoutInflater, container, true)
            return binding.root.also {
                // Start observing after initial layout is done to support transitions
                it.post { doObserverModel(binding, position, it as ViewGroup) }
                it.setOnClickListener { onPageClicked(it, position) }
                // Reapply transformation on layout
                it.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> pageTransformer.transformPage(it, 0f) }
            }
        }

        private fun doObserverModel(binding: TinkOverviewChartPageBinding, position: Int, root: ViewGroup) {
            val data = getPageData(position)
            data.observe(viewLifecycle, Observer {
                it?.let { overviewChartModel ->
                    // Animate currently visible page only
                    // Do not animate the initial value
                    // Animate after an update when the value changes

                    val isFirstLoading = overviewAmountText.text.toString().isBlank()
                    if (position == view.overviewPager.currentItem && !isFirstLoading && !transitionCoordinator.hasTransitionInProgress()) {
                        // TODO: Transition doesn't work in Fragment 1.3.4, uncomment and use when it's fixed, see https://issuetracker.google.com/issues/188457866
//                        TransitionManager.beginDelayedTransition(root, changeTransition())
                    }
                    updateData(root, binding, overviewChartModel)
                    onViewReady()
                }
            })
        }

        private fun changeTransition() = TransitionSet().apply {
            addTransition(PieChartLabelTransition())
            addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
            addTransition(Fade().apply { addTarget(R.id.tink_back_segment) })
            // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//            addTransition(TextAmountTransition(CurrencyUtils.minusSign) {
//                CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(it.toDouble()).apply { }
//            }.apply {
//                addTarget(R.id.amount)
//            })
            duration = 1000
        }

        private fun updateData(root: ViewGroup, binding: TinkOverviewChartPageBinding, model: OverviewChartModel) {
            binding.pieChart.transitionName = getString(R.string.tink_pie_chart_transition, model.title)
            binding.overviewTitle.text = model.title
            binding.overviewTitle.transitionName = getString(R.string.tink_amount_title_transition, model.title)
            binding.overviewAmountText.text = model.amount
            binding.overviewAmountText.transitionName = getString(R.string.tink_amount_transition, model.title)
            binding.overviewPeriod.text = model.period
            binding.overviewPeriod.transitionName = getString(R.string.tink_amount_period_transition, model.title)
            root.pieChart.apply {
                removeAllViews()
                addBackSegment(model.title, model.color)
                addSegments(model.data, { it }, model.colorGenerator, model.color, model.currency)
            }
        }

        private fun getPageData(position: Int) =
            when (statisticTypes[position]) {
                StatisticType.EXPENSES -> viewModel.expenses
//                PAGE_LEFT_TO_SPEND -> viewModel.leftToSpend
                StatisticType.INCOME -> viewModel.income
                else -> throw IllegalArgumentException("Invalid position $position")
        }

        private fun onPageClicked(page: View, position: Int) {
            when (statisticTypes[position]) {
                StatisticType.EXPENSES -> replaceWithDetailsFragment(ChartDetailsPagerFragment.newInstance(
                    ChartType.EXPENSES), page)
//                PAGE_LEFT_TO_SPEND -> {
//                    val fragment = ChartDetailsPagerFragment.newInstance(ChartType.LEFT_TO_SPEND)
//                    fragmentCoordinator.replace(fragment, true, FragmentAnimationFlags.NONE)
//                }
                StatisticType.INCOME -> replaceWithDetailsFragment(ChartDetailsPagerFragment.newInstance(ChartType.INCOME), page)
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }

        private fun replaceWithDetailsFragment(fragment: BaseFragment, page: View) = fragmentCoordinator.replace(
            fragment, true
        // TODO: Transition doesn't work in Fragment 1.3.4, uncomment and use when it's fixed, see https://issuetracker.google.com/issues/188457866
//            , FragmentAnimationFlags.NONE,
//            sharedViews = listOf(page.pieChart, page.title, page.period, page.amount, page.findViewById(R.id.tink_back_segment))
        )
    }

    companion object {
        private const val ARG_STATISTICS = "ARG_STATISTICS"

        @JvmStatic
        fun newInstance(statistics: OverviewFeature.Statistics): OverviewChartFragment =
            OverviewChartFragment().apply {
                arguments = bundleOf(
                    ARG_STATISTICS to statistics
                )
            }
    }
}

private class PageTransformer(resources: Resources) : ViewPager.PageTransformer {
    private val minRadius = resources.getDimensionPixelSize(R.dimen.tink_overview_chart_preview_radius)

    override fun transformPage(page: View, wrongPos: Float) {
        val position = calculatePosition(page)
        val absPosition = abs(position)
        with(page.pieChart) {
            transitionOuterRadius = (minRadius - outerRadius) * absPosition
            transitionThickness = (minRadius - thickness) * absPosition
        }
        page.pieChart.segments
            .filter { it.transitionGroup == R.id.tink_transition_group_main }
            .forEach { it.alpha = 1 - absPosition }

        with(page.content) {
            val center = width / 2
            val finalCenter = width - minRadius
            translationX = -(finalCenter - center) * position
        }
        with(page.fadingGroup) {
            scaleX = 1 - absPosition
            scaleY = 1 - absPosition
            alpha = maxOf(1 - absPosition * 2, 0f)
        }
    }

    /**
     * ViewPager has a bug in position calculation, it does not take padding into account
     */
    private fun calculatePosition(page: View): Float = with(page.parent as View) {
        val clientWidth = measuredWidth - paddingLeft - paddingRight
        return maxOf(-1f, minOf(1f, (page.left - paddingLeft - scrollX) / clientWidth.toFloat()))
    }
}



