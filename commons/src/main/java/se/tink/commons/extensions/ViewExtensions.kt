package se.tink.commons.extensions

import android.view.View

fun View.onAttachedToWindow(action: () -> Unit) {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {}
        override fun onViewAttachedToWindow(v: View?) = action()
    })
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
