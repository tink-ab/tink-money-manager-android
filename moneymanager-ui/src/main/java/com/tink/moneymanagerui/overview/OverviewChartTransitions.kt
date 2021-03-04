package com.tink.moneymanagerui.overview

import android.transition.Fade
import android.transition.TransitionSet
import android.view.View
import androidx.fragment.app.Fragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.TransitionDescription
import kotlinx.android.synthetic.main.tink_fragment_full_pie_chart.view.*
import com.tink.moneymanagerui.charts.PieChartView
import com.tink.moneymanagerui.charts.transitions.ChangePositionTransition
import com.tink.moneymanagerui.charts.transitions.PieChartSegmentTransition
import com.tink.moneymanagerui.charts.transitions.PieChartTransition
import com.tink.moneymanagerui.charts.transitions.TextScaleTransition
import com.tink.moneymanagerui.charts.extensions.doOnEnd
import com.tink.moneymanagerui.overview.charts.piechart.FullPieChartFragment
import javax.inject.Inject


internal class OverviewChartTransition @Inject constructor() : TransitionDescription {
    override val fragment1 get() = OverviewChartFragment::class
    override val fragment2 get() = FullPieChartFragment::class

    override fun setup(f1: Fragment, f2: Fragment) {
        setupExitTransition(f1)
        setupEnterTransition(f2)
    }

    private fun setupExitTransition(overview: Fragment) {
        with(overview) {
            exitTransition = Fade()
            reenterTransition = exitTransition
            allowReturnTransitionOverlap = false
        }
    }

    private fun setupEnterTransition(chartFragment: Fragment) {
        with(chartFragment) {
            allowEnterTransitionOverlap = false
            sharedElementEnterTransition = TransitionSet().apply {
                addTransition(TextScaleTransition().apply {
                    addTarget(R.id.amount)
                    addTarget(R.id.period)
                    addTarget(R.id.title)
                })
                addTransition(ChangePositionTransition().apply {
                    addTarget(R.id.pieChart)
                    addTarget(R.id.amount)
                    addTarget(R.id.title)
                    addTarget(R.id.period)
                })
                addTransition(PieChartTransition())
                addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
                // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//                addTransition(TextAmountTransition(CurrencyUtils.minusSign) {
//                    CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(it.toDouble())
//                }.apply {
//                    addTarget(R.id.amount)
//                })
            }
            sharedElementReturnTransition = sharedElementEnterTransition

            setDecoratorsVisibility(chartFragment.view, false)
            enterTransition = TransitionSet().apply {
                addTransition(Fade().apply {
                    excludeTarget(PieChartView::class.java, true)
                    excludeTarget(PieChartView.PieChartSegmentView::class.java, true)
                })
                doOnEnd { setDecoratorsVisibility(chartFragment.view, true) }
            }
            returnTransition = TransitionSet().apply {
                addTransition(Fade().apply {
                    excludeTarget(PieChartView::class.java, true)
                    excludeTarget(PieChartView.PieChartSegmentView::class.java, true)
                    excludeTarget(R.id.labelRoot, true)
                })
            }
        }
    }

    private fun setDecoratorsVisibility(view: View?, visible: Boolean) = view?.pieChart?.labels?.forEach { it.decorator?.visible = visible }
}