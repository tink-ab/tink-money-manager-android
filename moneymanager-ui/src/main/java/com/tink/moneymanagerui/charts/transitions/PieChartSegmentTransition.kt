package com.tink.moneymanagerui.charts.transitions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.charts.PieChartView
import com.tink.moneymanagerui.charts.extensions.addNonNull
import com.tink.moneymanagerui.charts.extensions.disableClipping
import com.tink.moneymanagerui.charts.extensions.doOnEnd
import com.tink.moneymanagerui.charts.extensions.get
import se.tink.commons.extensions.whenNonNull

private data class SegmentInfo(val view: PieChartView.PieChartSegmentView?, val startAngle: Float, val angle: Float, val color: Int) {
    constructor(v: PieChartView.PieChartSegmentView) : this(v, v.startAngle, v.angle, v.color)
}

private const val SEGMENTS = "segments_info"

internal class PieChartSegmentTransition(private val group: Int? = null, private val transitions: Int = TRANSITION_ALL) : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) = captureValues(transitionValues)
    override fun captureEndValues(transitionValues: TransitionValues) = captureValues(transitionValues)

    private fun captureValues(values: TransitionValues) {
        val chartView = values.view as? PieChartView ?: return
        values.values[SEGMENTS] = chartView.segments.filter { group == null || group == it.transitionGroup }.map { SegmentInfo(it) }.toList()
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        val view = endValues?.view as? PieChartView
        return whenNonNull(
            startValues[SEGMENTS],
            endValues[SEGMENTS],
            view,
            ::createAnimators
        )?.also {
            it.doOnEnd { view?.clearOverlay() }
        }
    }

    private fun createAnimators(start: List<SegmentInfo>, end: List<SegmentInfo>, view: PieChartView): Animator? {
        fun minAngle(segments: List<SegmentInfo>) = segments.minByOrNull { it.startAngle }?.startAngle ?: 0f
        fun maxAngle(segments: List<SegmentInfo>) = segments.maxByOrNull { it.startAngle + it.angle }?.let { it.startAngle + it.angle } ?: 360f

        val count = maxOf(start.size, end.size)
        val animators = mutableListOf<Animator>()
        val startEmpty = start.isEmpty()
        val endEmpty = end.isEmpty()
        val maxAngle = minOf(maxAngle(start), maxAngle(end))
        val minAngle = maxOf(minAngle(start), minAngle(end))
        for (i in 0 until count)
            animators.addNonNull(createAnimators(start.getOrNull(i), end.getOrNull(i), startEmpty, endEmpty, view, minAngle, maxAngle))
        return if (animators.isNotEmpty()) AnimatorSet().apply { playTogether(animators) } else null
    }

    private fun createAnimators(
        start: SegmentInfo?,
        end: SegmentInfo?,
        startEmpty: Boolean,
        endEmpty: Boolean,
        root: PieChartView,
        minAngle: Float,
        maxAngle: Float
    ): Animator? {
        val startNonNull = start ?: end?.let { getStart(it, startEmpty, minAngle, maxAngle) } ?: return null
        val endNonNull = end ?: start?.let { getEnd(root, it, endEmpty, minAngle, maxAngle) } ?: return null

        return createSegmentAnimator(startNonNull, endNonNull)
    }

    private fun getStart(end: SegmentInfo, startEmpty: Boolean, minAngle: Float, maxAngle: Float): SegmentInfo {
        val startAngle = if (startEmpty) minAngle else maxAngle
        return SegmentInfo(null, startAngle, 0f, end.color)
    }

    private fun getEnd(parent: PieChartView, start: SegmentInfo, endEmpty: Boolean, minAngle: Float, maxAngle: Float): SegmentInfo {
        val startAngle = if (endEmpty) minAngle else maxAngle
        val endView = start.view?.let { st ->
            parent.createSegment().also {
                it.color = st.color
                it.startAngle = startAngle
                it.angle = 0f
                it.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
        } ?: throw IllegalArgumentException("End view must not be null")

        parent.layoutAndAddToOverlay(endView)
        (endView.parent as? ViewGroup)?.disableClipping()
        return SegmentInfo(endView)
    }

    private fun createSegmentAnimator(s: SegmentInfo, e: SegmentInfo): Animator? {
        val view = e.view ?: return null
        val animators = mutableListOf<Animator>()
        if (s.angle != e.angle && transitionEnabled(TRANSITION_ANGLE)) {
            view.angle = s.angle
            animators.add(ObjectAnimator.ofFloat(view, PieChartView.ANGLE, s.angle, e.angle))
        }

        if (s.startAngle != e.startAngle && transitionEnabled(TRANSITION_ANGLE)) {
            view.startAngle = s.startAngle
            animators.add(ObjectAnimator.ofFloat(view, PieChartView.START_ANGLE, s.startAngle, e.startAngle))
        }

        if (s.color != e.color && transitionEnabled(TRANSITION_COLOR)) {
            view.color = s.color
            // Clear color state list to prevent unexpected color change with state change during animation
            val colorStateList = view.colorStateList
            view.colorStateList = null
            animators.add(ObjectAnimator.ofArgb(view, PieChartView.COLOR, s.color, e.color).apply {
                doOnEnd {
                    view.colorStateList = colorStateList
                }
            })
        }

        return if (!animators.isEmpty()) AnimatorSet().apply { playTogether(animators) } else null
    }

    private fun transitionEnabled(transition: Int): Boolean = transitions and transition == transition

    companion object {
        const val TRANSITION_ANGLE = 0x1
        const val TRANSITION_COLOR = 0x2
        const val TRANSITION_ALL = 0xFFFFFFF
    }
}


