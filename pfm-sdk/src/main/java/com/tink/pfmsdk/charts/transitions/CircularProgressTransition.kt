package com.tink.pfmsdk.charts.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup
import com.tink.pfmsdk.charts.CircularProgressChart
import com.tink.pfmsdk.charts.extensions.get
import se.tink.core.extensions.whenNonNull


private const val VALUE = "value"

class CircularProgressTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        val view = values.view as? CircularProgressChart ?: return
        values.values[VALUE] = view.progress
    }

    override fun createAnimator(sceneRoot: ViewGroup?, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        return whenNonNull(endValues?.view as? CircularProgressChart, startValues[VALUE], endValues[VALUE]) {
            view: CircularProgressChart, start: Double, end: Double ->
            ObjectAnimator.ofObject(view, "progress", TypeEvaluator<Double> { fraction, startValue, endValue ->
                startValue * (1 - fraction) + endValue * fraction
            }, start, end)
        }
    }
}
