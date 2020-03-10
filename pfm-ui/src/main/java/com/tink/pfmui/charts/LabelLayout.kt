package com.tink.pfmui.charts


internal object LabelLayout {

    fun layout(labels: List<PieChartLabelView>) {
        // Initial label positions
        for (label in labels) label.centerAngle = label.anchorAngle

        val labelsCount = labels.size
        if (labelsCount < 2) return

        for (i in 0 until labelsCount) {
            val nextIdx = (i + 1) % labelsCount
            val distance = distance(labels, i, nextIdx, 1)
            val minDistance = labels[i].archSize / 2f + labels[nextIdx].archSize / 2f

            if (distance < minDistance) {
                val ccw = stepsToFit(labels, distance - minDistance, i, -1)
                val cw = stepsToFit(labels, distance - minDistance, nextIdx, 1)
                if (ccw <= cw) {
                    move(labels, distance - minDistance, i, -1)
                } else {
                    move(labels, distance - minDistance, nextIdx, 1)
                }
            }
        }
    }

    /**
     * Calculates signed angular distance.
     * Result > 0 if dst calculated in [dir] direction
     */
    private fun distance(labels: List<PieChartLabelView>, i: Int, nextIdx: Int, dir: Int): Float {
        val center = (labels[i].centerAngle + 360f) % 360
        val nextCenter = (labels[nextIdx].centerAngle + 360f) % 360
        val diff = dir * (nextCenter - center)
        return when {
            diff > 180 -> -(360 - diff)
            diff <= -180 -> 360 + diff
            else -> diff
        }
    }

    private fun stepsToFit(labels: List<PieChartLabelView>, diff: Float, idx: Int, dir: Int): Int {
        val count = labels.size
        var d = diff
        for (st in 0 until count) {
            val i = (idx + st * dir + count) % count
            val next = (idx + (st + 1) * dir + count) % count
            val distance = distance(labels, i, next, dir)
            val minDistance = labels[i].archSize / 2f + labels[next].archSize / 2f
            d += (distance - minDistance)
            if (d > 0) return st
        }
        return Int.MAX_VALUE
    }

    private fun move(labels: List<PieChartLabelView>, diff: Float, idx: Int, dir: Int) {
        val count = labels.size
        var d = diff
        for (st in 0 until count) {
            val i = (idx + st * dir + count) % count
            val next = (idx + (st + 1) * dir + count) % count
            labels[i].centerAngle += (-d * dir)
            val distance = distance(labels, i, next, dir)
            val minDistance = labels[i].archSize / 2f + labels[next].archSize / 2f
            d = (distance - minDistance)
            if (d > 0) return
        }
    }
}