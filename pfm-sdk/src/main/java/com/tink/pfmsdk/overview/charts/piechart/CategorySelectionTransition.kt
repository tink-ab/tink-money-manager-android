package com.tink.pfmsdk.overview.charts.piechart

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.RectEvaluator
import android.graphics.Rect
import android.transition.TransitionValues
import android.transition.Visibility
import android.view.View
import android.view.ViewGroup

internal class CategorySelectionTransition : Visibility() {

    override fun onAppear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        return animateClip(view, true)
    }

    override fun onDisappear(sceneRoot: ViewGroup?, view: View, startValues: TransitionValues?, endValues: TransitionValues?): Animator {
        return animateClip(view, false)
    }

    private fun animateClip(view: View, show: Boolean): Animator {
        val height = view.height
        val start = Rect(0, if (show) height else 0, view.width, height)
        val end = Rect(0, if (show) 0 else height, view.width, height)

        view.clipBounds = start
        view.translationY = if (show) -height.toFloat() else 0f
        return AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofObject(view, "clipBounds", RectEvaluator(), end),
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, if (show) 0f else -height.toFloat())
            )
        }
    }
}