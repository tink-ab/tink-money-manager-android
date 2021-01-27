package com.tink.pfmui.view

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun setInvisible(view: View, invisible: Boolean) {
    view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}