package com.tink.pfmsdk.overview

import android.content.res.Resources
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.FragmentAnimationFlags
import com.tink.pfmsdk.OverviewFeature
import com.tink.pfmsdk.R
import com.tink.pfmsdk.StatisticType
import com.tink.pfmsdk.databinding.FragmentOverviewChartBinding
import com.tink.pfmsdk.databinding.OverviewChartPageBinding
import com.tink.pfmsdk.extensions.onPageSelected
import kotlinx.android.synthetic.main.fragment_overview_chart.view.*
import kotlinx.android.synthetic.main.overview_chart_page.view.*
import com.tink.pfmsdk.charts.transitions.PieChartLabelTransition
import com.tink.pfmsdk.charts.transitions.PieChartSegmentTransition
import com.tink.pfmsdk.charts.transitions.TextAmountTransition
import com.tink.pfmsdk.overview.charts.ChartDetailsPagerFragment
import com.tink.pfmsdk.overview.charts.ChartType
import com.tink.pfmsdk.overview.charts.piechart.addBackSegment
import com.tink.pfmsdk.overview.charts.piechart.addSegments
import com.tink.pfmsdk.util.CurrencyUtils
import kotlin.math.abs

internal class OverviewChartFragment : BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[OverviewChartViewModel::class.java] }
    private val pageTransformer by lazy { PageTransformer(resources) }

    override fun getLayoutId() = R.layout.fragment_overview_chart
    override fun needsLoginToBeAuthorized() = true
    override fun doNotRecreateView(): Boolean = false
    override fun viewReadyAfterLayout(): Boolean  = false
    override fun hasToolbar(): Boolean = false

    private val statistics: OverviewFeature.Statistics by lazy {
        requireNotNull(arguments?.getParcelable<OverviewFeature.Statistics>(ARG_STATISTICS))
    }

    override fun authorizedOnCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        DataBindingUtil.bind<FragmentOverviewChartBinding>(view.chartRoot)?.also {
            it.loaded = viewModel.loaded
            it.setLifecycleOwner(viewLifecycle)
        }
        with(view) {
            with(pager) {
                adapter = ChartPagerAdapter(statistics.statisticTypes)
                offscreenPageLimit = statistics.statisticTypes.size
                currentItem = viewModel.lastVisitedPageInOverview
                setPageTransformer(false, pageTransformer)
                onPageSelected { viewModel.lastVisitedPageInOverview = it }
            }
            pageIndicator.initialize(pager)
            viewModel.loaded.observe(viewLifecycleOwner, Observer { loaded ->
                pageIndicator.visibility =
                    if (loaded && statistics.statisticTypes.size > 1) View.VISIBLE else View.GONE
            })
        }
    }

    private inner class ChartPagerAdapter(val statisticTypes: List<StatisticType>) : PagerAdapter() {
        override fun isViewFromObject(view: View, obj: Any) = obj == view
        override fun getCount() = statisticTypes.size
        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) = container.removeView(obj as View)

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = DataBindingUtil.inflate<OverviewChartPageBinding>(layoutInflater, R.layout.overview_chart_page, container, true)
            return binding.root.also {
                // Start observing after initial layout is done to support transitions
                it.post { doObserverModel(binding, position, it as ViewGroup) }
                it.setOnClickListener { onPageClicked(it, position) }
                // Reapply transformation on layout
                it.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> pageTransformer.transformPage(it, 0f) }
            }
        }

        private fun doObserverModel(binding: OverviewChartPageBinding, position: Int, root: ViewGroup) {
            val data = getPageData(position)
            data.observe(viewLifecycle, Observer {
                it?.let {
                    // Animate currently visible page only
                    // Do not animate the initial value
                    // Animate after an update when the value changes

                    val firstLoading = binding.amount.text.toString() == ""
                    if (position == view.pager.currentItem && !firstLoading && !transitionCoordinator.hasTransitionInProgress()) {
                        TransitionManager.beginDelayedTransition(root, changeTransition())
                    }
                    updateData(root, binding, it)
                    if (position == view.pager.currentItem) {
                        binding.root.post { onViewReady() }
                    }
                }
            })
        }

        private fun changeTransition() = TransitionSet().apply {
            addTransition(PieChartLabelTransition())
            addTransition(PieChartSegmentTransition(R.id.transition_group_main))
            addTransition(Fade().apply { addTarget(R.id.back_segment) })
            addTransition(TextAmountTransition(CurrencyUtils.getMinusSign()) {
                CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(it.toDouble()).apply { }
            }.apply {
                addTarget(R.id.amount)
            })
            duration = 1000
        }

        private fun updateData(root: ViewGroup, binding: OverviewChartPageBinding, model: OverviewChartModel) {
            binding.model = model
            root.pieChart.apply {
                removeAllViews()
                addBackSegment(model.title, model.color)
                addSegments(model.data, { it }, model.colorGenerator, model.color)
            }
            binding.executePendingBindings()
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
            fragment, true, FragmentAnimationFlags.NONE,
            sharedViews = listOf(page.pieChart, page.title, page.period, page.amount, page.findViewById(R.id.back_segment))
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
    private val minRadius = resources.getDimensionPixelSize(R.dimen.overview_chart_preview_radius)

    override fun transformPage(page: View, wrongPos: Float) {
        val position = calculatePosition(page)
        val absPosition = abs(position)
        with(page.pieChart) {
            transitionOuterRadius = (minRadius - outerRadius) * absPosition
            transitionThickness = (minRadius - thickness) * absPosition
        }
        page.pieChart.segments
            .filter { it.transitionGroup == R.id.transition_group_main }
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



