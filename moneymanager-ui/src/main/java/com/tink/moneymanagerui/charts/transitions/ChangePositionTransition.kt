package com.tink.moneymanagerui.charts.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.charts.extensions.get
import se.tink.commons.extensions.whenNonNull

private const val PARAMS = "view_center"

private val View.boundsOnScreen
    get() = Rect(0, 0, width, height).also {
        (rootView as ViewGroup).offsetDescendantRectToMyCoords(this, it)
    }

internal class ChangePositionTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        values.values[PARAMS] = values.view?.boundsOnScreen
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        val view = endValues?.view
        return whenNonNull(
            startValues[PARAMS],
            endValues[PARAMS],
            view,
            ::createAnimators
        )
    }

    private fun createAnimators(start: Rect, end: Rect, view: View): Animator? {
        (view.rootView as ViewGroup).apply {
            offsetRectIntoDescendantCoords(view, start)
            offsetRectIntoDescendantCoords(view, end)
        }
        if (start == end) return null
        view.translationX = start.exactCenterX() - end.exactCenterX()
        view.translationY = start.exactCenterY() - end.exactCenterY()
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f),
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f)
            )
        }
    }
}

