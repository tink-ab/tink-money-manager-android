package com.tink.pfmui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.tink_item_picker.view.*
import se.tink.commons.extensions.getColorFromAttr
import kotlin.properties.Delegates

internal typealias ItemSelectListener<T> = (T) -> Unit

internal abstract class ItemPicker<T : Any> : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyAttributes(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        applyAttributes(attrs)
    }

    init {
        // It's safe to use `this` in this case
        @Suppress("LeakingThis")
        View.inflate(context, R.layout.tink_item_picker, this)
        iconLeft.setOnClickListener { nextPeriod(-1) }
        iconRight.setOnClickListener { nextPeriod(1) }
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.TinkPeriodPickerStyle, 0, 0
        ).apply {
            try {
                val activePickerColor =
                    getColor(
                        R.styleable.TinkPeriodPickerStyle_tink_period_picker_active_color,
                        context.getColorFromAttr(R.attr.tink_colorAccent)
                    )
                iconLeft.imageTintList = ColorStateList.valueOf(activePickerColor)
                iconRight.imageTintList = ColorStateList.valueOf(activePickerColor)

                disabledAlpha = getFloat(R.styleable.TinkPeriodPickerStyle_tink_period_picker_disabled_alpha, 0.2f)
            } finally {
                recycle()
            }
        }
    }

    var currentItem: T? by Delegates.observable<T?>(null) { _, _, _ -> onItemChanged() }

    lateinit var formatter: (T) -> String
    var items: List<T> by Delegates.observable(emptyList()) { _, _, _ -> updateArrows() }
    var onItemSelected: ItemSelectListener<T>? = null

    private var disabledAlpha: Float = 0.2f

    private fun onItemChanged() {
        title.text = currentItem?.let { formatter(it) } ?: ""
        updateArrows()
    }

    fun setText(text: CharSequence?) {
        text?.let {
            title.text = it.toString().capitalize()
        }
    }

    fun setShowButtons(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        iconLeft.visibility = visibility
        iconRight.visibility = visibility
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

    fun setPreviousButtonEnabled(enabled: Boolean) {
        iconLeft.isEnabled = enabled
        iconLeft.alpha = if (enabled) 1.0f else disabledAlpha
    }

    fun setNextButtonEnabled(enabled: Boolean) {
        iconRight.isEnabled = enabled
        iconRight.alpha = if (enabled) 1.0f else disabledAlpha
    }

    private fun updateArrows() {
        val idx = items.indexOf(currentItem)
        iconLeft.isEnabled = idx > 0
        iconLeft.alpha = if (iconLeft.isEnabled) 1.0f else disabledAlpha
        iconRight.isEnabled = idx < items.size - 1
        iconRight.alpha = if (iconRight.isEnabled) 1.0f else disabledAlpha
    }
}
