package com.tink.moneymanagerui.statistics.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup
import android.widget.TextView

private const val PROPERTY_NAME_TEXT_SIZE = "tink:transition:textSize"
private val TRANSITION_PROPERTIES = arrayOf(PROPERTY_NAME_TEXT_SIZE)

internal class TextScaleTransition : Transition() {

    override fun getTransitionProperties(): Array<String> {
        return TRANSITION_PROPERTIES
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        if (transitionValues.view is TextView) {
            val textView = transitionValues.view as TextView
            transitionValues.values[PROPERTY_NAME_TEXT_SIZE] = textView.textSize
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }

        val startSize = startValues.values[PROPERTY_NAME_TEXT_SIZE] as Float
        val endSize = endValues.values[PROPERTY_NAME_TEXT_SIZE] as Float

        if (startSize == endSize) {
            return null
        }

        val view = endValues.view as TextView

        val startScale = startSize / endSize
        val endScale = 1f

        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale),
                ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale)
            )
        }
    }
}
