package com.tink.pfmsdk.view

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
internal fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) View.VISIBLE else View.GONE
}