package com.tink.pfmsdk.view

import androidx.lifecycle.LifecycleOwner
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tink.pfmsdk.BR

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, newHeight: Int) {
    view.layoutParams = view.layoutParams.apply { height = newHeight }
}

@BindingAdapter(value = ["layout", "items", "lifecycle", "theme"], requireAll = false)
fun bindItems(view: ViewGroup, @LayoutRes layout: Int, items: List<Any>?, lifecycleOwner: LifecycleOwner? = null, theme: Any? = null) {
    items?.let {
        with(view) {
            removeAllViews()
            val inflater = LayoutInflater.from(view.context)
            for ((index, item) in items.withIndex()) {
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layout, view, true).apply {
                    setVariable(BR.model, item)
                    setVariable(BR.theme, theme)
                    setLifecycleOwner(lifecycleOwner)
                }
            }
        }
    }
}