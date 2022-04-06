package com.tink.moneymanagerui.overview.accounts

import android.content.Context
import android.view.View
import android.view.View.MeasureSpec
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.tink.moneymanagerui.util.ScreenUtils

internal class AccountLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {

    private val standardWidth: Int = ScreenUtils.getScreenMetrics(context).widthPixels / 2 -
        ScreenUtils.getScreenMetrics(context).widthPixels / 12

    override fun measureChildWithMargins(child: View, widthUsed: Int, heightUsed: Int) {
        val childLayoutParams = child.layoutParams as LayoutParams
        val heightMeasureSpec = calculateChildHeightMeasureSpec(childLayoutParams)
        val widthMeasureSpec = calculateChildWidthMeasureSpec(childLayoutParams)
        child.measure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun calculateChildHeightMeasureSpec(childLayoutParams: LayoutParams): Int {
        val height = height - paddingTop - paddingBottom - childLayoutParams.topMargin - childLayoutParams.bottomMargin
        return MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
    }

    private fun calculateChildWidthMeasureSpec(lp: LayoutParams): Int {
        val paddedParentWidth = width - paddingLeft - paddingRight
        val childWidth = calculateChildWidth(paddedParentWidth, lp.marginStart + lp.marginEnd)
        return MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY)
    }

    private fun calculateChildWidth(paddedParentWidth: Int, margin: Int): Int {
        return when (itemCount) {
            1 -> paddedParentWidth - margin
            2 -> paddedParentWidth / 2 - margin
            else -> standardWidth
        }
    }
}
