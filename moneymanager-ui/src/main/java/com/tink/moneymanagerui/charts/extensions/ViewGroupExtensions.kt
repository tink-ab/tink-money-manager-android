package com.tink.moneymanagerui.charts.extensions

import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.R

internal val ViewGroup.children: Iterator<View>
    get() = object : Iterator<View> {
        private var idx = 0

        override fun next() = getChildAt(idx++)
        override fun hasNext() = idx < childCount
    }

internal fun ViewGroup.childOrNull(idx: Int) = if (childCount > idx) getChildAt(idx) else null

private data class ClippingInfo(val clipChildren: Boolean, val clipToPadding: Boolean, var count: Int)

internal fun ViewGroup.disableClipping() {
    val info = getTag(R.id.tink_clipping_info) as? ClippingInfo ?: ClippingInfo(clipChildren, clipToPadding, 0)
    info.count++
    setTag(R.id.tink_clipping_info, info)
    clipChildren = false
    clipToPadding = false
    (parent as? ViewGroup)?.disableClipping()
}

internal fun ViewGroup.restoreClipping() {
    val info = getTag(R.id.tink_clipping_info) as? ClippingInfo ?: return
    if (--info.count == 0) {
        clipChildren = info.clipChildren
        clipToPadding = info.clipToPadding
    }
    (parent as? ViewGroup)?.restoreClipping()
}
