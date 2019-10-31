package se.tink.android.charts.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import se.tink.android.extensions.get
import se.tink.core.extensions.whenNonNull

private const val TRANSLATION_Y = "translation_y_"

class TranslationTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        values.values[TRANSLATION_Y] = values.view?.translationY
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        return whenNonNull(startValues[TRANSLATION_Y], endValues[TRANSLATION_Y], endValues?.view, ::createAnimators)
    }

    private fun createAnimators(start: Float, end: Float, view: View): Animator? {
        return if (start != end) {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, start, end)
        } else null
    }
}


