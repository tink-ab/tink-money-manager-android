package com.tink.moneymanagerui.charts.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.charts.PieChartLabelView
import com.tink.moneymanagerui.charts.extensions.doOnEnd
import com.tink.moneymanagerui.charts.extensions.get

private data class LParams(val view: PieChartLabelView, val anchorAngle: Float, val centerAngle: Float) {
    constructor(v: PieChartLabelView) : this(v, v.anchorAngle, v.centerAngle)
}

private const val PARAMS = "label_params"

internal class PieChartLabelTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        values.values[PARAMS] = LParams(values.view as? PieChartLabelView ?: return)
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        return createAnimators(sceneRoot, startValues[PARAMS], endValues[PARAMS])
    }

    private fun createAnimators(root: ViewGroup, start: LParams?, end: LParams?): Animator? {
        return when {
            start != null && end == null -> onDisappear(root, start)
            start == null && end != null -> onAppear(end)
            start != null && end != null -> onChanged(start, end)
            else -> return null
        }
    }

    private fun onAppear(end: LParams) = ObjectAnimator.ofFloat(end.view, View.ALPHA, 0f, 1f)

    private fun onChanged(start: LParams, end: LParams): Animator? {
        val animators = mutableListOf<Animator>()

        if (start.anchorAngle != end.anchorAngle)
            animators.add(ObjectAnimator.ofFloat(end.view, PieChartLabelView.ANCHOR_ANGLE, start.anchorAngle, end.anchorAngle))

        if (start.centerAngle != end.centerAngle) {
            animators.add(ObjectAnimator.ofFloat(end.view, PieChartLabelView.TRANSITION_CENTER_ANGLE, start.centerAngle, end.centerAngle))
        }

        return if (animators.isNotEmpty()) AnimatorSet().apply { playTogether(animators) } else null
    }

    private fun onDisappear(root: ViewGroup, start: LParams): Animator? {
        root.overlay.add(start.view)
        return ObjectAnimator.ofFloat(start.view, View.ALPHA, 1f, 0f).apply {
            doOnEnd { root.overlay.remove(start.view) }
        }
    }
}
