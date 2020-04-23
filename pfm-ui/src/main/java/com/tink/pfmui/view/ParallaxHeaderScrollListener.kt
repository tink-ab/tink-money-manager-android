package com.tink.pfmui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class ParallaxHeaderScrollListener(
    private val headers: List<View>,
    private val headerHeight: Float
) : RecyclerView.OnScrollListener() {

    private var scrolledY = 0

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) {
            // Layout event happened, recalculate
            scrolledY = recyclerView.computeVerticalScrollOffset()
        }
        scrolledY += dy
        val alpha = (headerHeight - scrolledY * 1.66f) / headerHeight
        val topMargin = -scrolledY / 8
        for (header in headers) {
            header.translationY = topMargin.toFloat()
            header.alpha = alpha
        }
    }
}