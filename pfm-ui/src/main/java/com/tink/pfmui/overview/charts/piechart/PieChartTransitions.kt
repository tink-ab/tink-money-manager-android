package com.tink.pfmui.overview.charts.piechart

import androidx.fragment.app.Fragment
import android.transition.Fade
import android.transition.TransitionSet
import android.view.View
import com.tink.pfmui.R
import com.tink.pfmui.TransitionDescription
import com.tink.pfmui.util.CurrencyUtils
import kotlinx.android.synthetic.main.tink_fragment_full_pie_chart.view.*
import com.tink.pfmui.charts.PieChartView
import com.tink.pfmui.charts.transitions.ChangePositionTransition
import com.tink.pfmui.charts.transitions.PieChartSegmentTransition
import com.tink.pfmui.charts.transitions.PieChartTransition
import com.tink.pfmui.charts.transitions.TextAmountTransition
import com.tink.pfmui.charts.extensions.doOnEnd
import javax.inject.Inject


internal class FullToHalfChartTransition @Inject constructor() : TransitionDescription {
    override val fragment1 get() = FullPieChartFragment::class
    override val fragment2 get() = HalfPieChartFragment::class

    override fun setup(f1: Fragment, f2: Fragment) {
        setupFullChartTransitions(f1)
        setupHalfChartTransitions(f2)
    }

    private fun setupFullChartTransitions(fragment: Fragment) {
        with(fragment) {
            sharedElementEnterTransition = TransitionSet().apply {
                ordering = TransitionSet.ORDERING_SEQUENTIAL
                addTransition(TransitionSet().apply {
                    addTransition(ChangePositionTransition().apply {
                        addTarget(R.id.pieChart)
                    })
                    addTransition(PieChartTransition())
                })

                addTransition(TransitionSet().apply {
                    addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
                    // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//                    addTransition(TextAmountTransition(CurrencyUtils.getMinusSign()) {
//                        CurrencyUtils.formatAmountRoundWithoutCurrencySymbol(it.toDouble())
//                    }.apply {
//                        addTarget(R.id.amount)
//                    })
                })
            }
            setDecoratorsVisibility(fragment.view, false)
            enterTransition = TransitionSet().apply {
                addTransition(Fade().apply {
                    excludeTarget(PieChartView::class.java, true)
                    excludeTarget(PieChartView.PieChartSegmentView::class.java, true)
                })
                startDelay = 150
                doOnEnd { setDecoratorsVisibility(fragment.view, true) }
            }
            exitTransition = null
            allowEnterTransitionOverlap = false
            allowReturnTransitionOverlap = false
        }
    }

    private fun setupHalfChartTransitions(fragment: Fragment) {
        with(fragment) {
            sharedElementEnterTransition = TransitionSet().apply {
                ordering = TransitionSet.ORDERING_SEQUENTIAL
                addTransition(TransitionSet().apply {
                    addTransition(ChangePositionTransition().apply {
                        addTarget(R.id.pieChart)
                    })
                    addTransition(PieChartTransition())
                })

                addTransition(TransitionSet().apply {
                    addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
                    // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//                    addTransition(TextAmountTransition(CurrencyUtils.getMinusSign()) {
//                        CurrencyUtils.formatAmountExactWithCurrencySymbol(it.toDouble())
//                    }.apply {
//                        addTarget(R.id.amount)
//                    })
                })
            }
            enterTransition = Fade()
            allowEnterTransitionOverlap = false
            allowReturnTransitionOverlap = false
        }
    }

    private fun setDecoratorsVisibility(view: View?, visible: Boolean) = view?.pieChart?.labels?.forEach { it.decorator?.visible = visible }
}