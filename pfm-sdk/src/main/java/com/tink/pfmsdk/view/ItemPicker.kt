package com.tink.pfmsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.tink.pfmsdk.R
import kotlinx.android.synthetic.main.item_picker.view.*
import kotlin.properties.Delegates

typealias ItemSelectListener<T> = (T) -> Unit

abstract class ItemPicker<T : Any> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        // It's safe to use `this` in this case
        @Suppress("LeakingThis")
        View.inflate(context, R.layout.item_picker, this)
        iconLeft.setOnClickListener { nextPeriod(-1) }
        iconRight.setOnClickListener { nextPeriod(1) }
    }

    var currentItem: T? by Delegates.observable<T?>(null) { _, _, _ -> onItemChanged() }

    lateinit var formatter: (T) -> String
    var items: List<T> by Delegates.observable(emptyList()) { _, _, _ -> updateArrows() }
    var onItemSelected: ItemSelectListener<T>? = null

    private fun onItemChanged() {
        title.text = currentItem?.let { formatter(it) } ?: ""
        updateArrows()
    }

    private fun nextPeriod(dir: Int) {
        if (items.isEmpty()) return
        val idx = items.indexOf(currentItem)
        var nextIdx = if (idx == -1) 0 else idx + dir
        nextIdx = minOf(maxOf(nextIdx, 0), items.size - 1)
        currentItem = items[nextIdx].also {
            onItemSelected?.invoke(it)
        }
    }

    private fun updateArrows() {
        val idx = items.indexOf(currentItem)
        iconLeft.isEnabled = idx > 0
        iconRight.isEnabled = idx < items.size - 1
    }
}
