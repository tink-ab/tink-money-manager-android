package com.tink.pfmsdk.charts.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.transition.Transition
import android.transition.TransitionValues
import android.util.Property
import android.view.ViewGroup
import android.widget.TextView
import com.tink.pfmsdk.charts.extensions.get
import se.tink.core.extensions.whenNonNull

private const val VALUE = "value"
private const val STEP_COUNT = 100
private const val MINUS = "-"

typealias TextFormatter = (value: Float) -> String

class TextAmountTransition(
        private val negativeNumberSignFromFormatter:Char,
        private val formatter: TextFormatter
) : Transition() {

    private fun CharSequence.parseInt() = try {
        Integer.parseInt(this
                .replace(negativeNumberSignFromFormatter.toString().toRegex(), MINUS)
                .replace(Regex("[^\\d$MINUS]"), "")
        )
    } catch (e: NumberFormatException) {
        0
    }

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        val view = values.view as? TextView ?: return
        values.values[VALUE] = view.text
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        val textView = endValues?.view as? TextView
        return whenNonNull(startValues[VALUE], endValues[VALUE], textView) { start: CharSequence, end: CharSequence, view ->
            whenNonNull(start.parseInt(), end.parseInt()) { startInt: Int, endInt: Int ->
                if (startInt == endInt) return null
                    createAnimation(start, startInt.toFloat(), endInt.toFloat(), view)
            }
        }
    }

    private fun createAnimation(startText: CharSequence, start: Float, end: Float, view: TextView): Animator? {
        val steps = mutableListOf<String>()
        val step = (end - start) / STEP_COUNT
        var last: String? = null
        for (i in 0 until STEP_COUNT) {
            val value = formatter(if (i == STEP_COUNT - 1) end else start + step * i)
            if (value != last) {
                steps.add(value)
                last = value
            }
        }
        if (steps.size <= 1) return null
        view.text = startText
        val evaluatorStep = 1f / steps.size
        return ObjectAnimator.ofObject(view, TEXT_PROPERTY, TypeEvaluator<CharSequence> { fraction, _, _ ->
            val idx = minOf((fraction / evaluatorStep).toInt(), steps.size - 1)
            steps[idx]
        }, steps.last())
    }
}

private val TEXT_PROPERTY = object : Property<TextView, CharSequence>(CharSequence::class.java, "text") {
    override fun get(item: TextView) = item.text
    override fun set(item: TextView, value: CharSequence) {
        if (item.text.toString() != value.toString()) {
            item.text = value
        }
    }
}

