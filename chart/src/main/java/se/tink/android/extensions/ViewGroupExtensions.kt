package se.tink.android.extensions

import android.view.View
import android.view.ViewGroup
import se.tink.android.charts.R

val ViewGroup.children: Iterator<View>
    get() = object : Iterator<View> {
        private var idx = 0

        override fun next() = getChildAt(idx++)
        override fun hasNext() = idx < childCount
    }

fun ViewGroup.childOrNull(idx: Int) = if (childCount > idx) getChildAt(idx) else null

private data class ClippingInfo(val clipChildren: Boolean, val clipToPadding: Boolean, var count: Int)

fun ViewGroup.disableClipping() {
    val info = getTag(R.id.clipping_info) as? ClippingInfo ?: ClippingInfo(clipChildren, clipToPadding, 0)
    info.count++
    setTag(R.id.clipping_info, info)
    clipChildren = false
    clipToPadding = false
    (parent as? ViewGroup)?.disableClipping()
}

fun ViewGroup.restoreClipping() {
    val info = getTag(R.id.clipping_info) as? ClippingInfo ?: return
    if (--info.count == 0) {
        clipChildren = info.clipChildren
        clipToPadding = info.clipToPadding
    }
    (parent as? ViewGroup)?.restoreClipping()
}