package com.tink.pfmsdk.charts.extensions

import android.animation.Animator
import android.transition.Transition
import android.transition.TransitionValues

@Suppress("UNCHECKED_CAST")
internal operator fun <T> TransitionValues?.get(key: String): T? = this?.values?.get(key) as? T

//TODO: Use ktx when released
internal fun Animator.doOnStart(action: () -> Unit) = addListener(object : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) = action()
})

internal fun Animator.doOnEnd(action: () -> Unit) = addListener(object : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) = action()
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
})

//TODO: Use ktx when released
internal fun Transition.doOnStart(action: () -> Unit) = addListener(object : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) {}
    override fun onTransitionResume(transition: Transition?) {}
    override fun onTransitionPause(transition: Transition?) {}
    override fun onTransitionCancel(transition: Transition?) {}
    override fun onTransitionStart(transition: Transition?) = action()
})

internal fun Transition.doOnEnd(action: () -> Unit) = addListener(object : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) = action()
    override fun onTransitionResume(transition: Transition?) {}
    override fun onTransitionPause(transition: Transition?) {}
    override fun onTransitionCancel(transition: Transition?) {}
    override fun onTransitionStart(transition: Transition?) {}
})