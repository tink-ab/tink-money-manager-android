package com.tink.moneymanagerui.charts.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup
import com.tink.moneymanagerui.charts.PieChartView
import com.tink.moneymanagerui.charts.extensions.disableClipping
import com.tink.moneymanagerui.charts.extensions.doOnEnd
import com.tink.moneymanagerui.charts.extensions.get
import com.tink.moneymanagerui.charts.extensions.restoreClipping
import se.tink.commons.extensions.whenNonNull

private data class PieChartInfo(val radius: Float, val thickness: Float) {
    constructor(v: PieChartView) : this(v.outerRadius, v.thickness)
}

private const val PIE_CHART = "pie_chart"

internal class PieChartTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        values.values[PIE_CHART] = PieChartInfo(values.view as? PieChartView ?: return)
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        val view = endValues?.view as? PieChartView
        return whenNonNull(
            startValues[PIE_CHART],
            endValues[PIE_CHART],
            view,
            ::createChartAnimators
        )?.also {
            view?.disableClipping()
            it.doOnEnd { view?.restoreClipping() }
        }
    }

    private fun createChartAnimators(s: PieChartInfo, e: PieChartInfo, view: PieChartView): Animator? {
        val animators = mutableListOf<Animator>()

        if (s.radius != e.radius) {
            view.outerRadius = s.radius
            animators.add(ObjectAnimator.ofFloat(view, PieChartView.RADIUS, e.radius))
        }

        if (s.thickness != e.thickness) {
            view.thickness = s.thickness
            animators.add(ObjectAnimator.ofFloat(view, PieChartView.THICKNESS, e.thickness))
        }

        return if (!animators.isEmpty()) AnimatorSet().apply { playTogether(animators) } else null
    }
}
