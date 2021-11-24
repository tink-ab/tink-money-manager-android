package com.tink.moneymanagerui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

internal class AlphaHeaderScrollListener(
    private val headers: List<View>,
) : RecyclerView.OnScrollListener() {

    private var scrolledY = 0
    private val scrolledGoneThreshold = 300

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)
        scrolledY += dy
        val alpha = max(0, scrolledGoneThreshold - scrolledY) / scrolledGoneThreshold.toFloat()
        headers.forEach { header ->
            header.alpha = alpha
        }
    }
}