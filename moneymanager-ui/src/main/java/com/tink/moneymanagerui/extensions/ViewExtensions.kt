package com.tink.moneymanagerui.extensions

import android.view.View

internal fun View.visibleIf(elseVisibility: Int = View.GONE, isVisible: () -> Boolean) {
    visibility = when {
        isVisible() -> View.VISIBLE
        else -> elseVisibility
    }
}
