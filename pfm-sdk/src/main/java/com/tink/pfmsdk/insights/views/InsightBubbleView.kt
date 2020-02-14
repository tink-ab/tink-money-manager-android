package com.tink.pfmsdk.insights.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.tink.pfmsdk.R
import kotlinx.android.synthetic.main.insight_bubble.view.*
import se.tink.commons.utils.extractTextStyle

class InsightBubbleView : ConstraintLayout {

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

    init {
        LayoutInflater.from(context).inflate(R.layout.insight_bubble, this, true)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.InsightBubbleView, 0, 0
        ).apply {
            try {
                setTextStyle(
                    primaryText,
                    getResourceId(R.styleable.InsightBubbleView_primaryTextStyle, 0)
                )
                setTextStyle(
                    secondaryText,
                    getResourceId(R.styleable.InsightBubbleView_secondaryTextStyle, 0)
                )
                circle.backgroundTintList =
                    ColorStateList.valueOf(
                        getColor(R.styleable.InsightBubbleView_bubbleBackgroundTint, 0)
                    )
                val textColor = getColor(R.styleable.InsightBubbleView_textColor, 0)
                primaryText.setTextColor(textColor)
                secondaryText.setTextColor(textColor)
                setPrimaryText(getString(R.styleable.InsightBubbleView_primaryText))
                setSecondaryText(getString(R.styleable.InsightBubbleView_secondaryText))
                icon.imageTintList =
                    ColorStateList.valueOf(
                        getColor(R.styleable.InsightBubbleView_iconTint, 0)
                    )
                val iconDrawable = getDrawable(R.styleable.InsightBubbleView_icon)
                if (iconDrawable != null) {
                    icon.setImageDrawable(getDrawable(R.styleable.InsightBubbleView_icon))
                } else {
                    icon.visibility = View.GONE
                }
            } finally {
                recycle()
            }
        }
    }

    private fun TextView.setupFor(text: String?) {
        if (text != null) {
            this.text = text
            this.visibility == View.VISIBLE
        } else {
            this.visibility == View.GONE
        }
    }

    private fun setTextStyle(textView: TextView, styleId: Int) {
        if (styleId > 0) {
            extractTextStyle(context, styleId).let {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.textSize)
                textView.typeface = it.fontFamily
            }
        }
    }

    fun setIcon(@DrawableRes iconRes: Int) = icon.setImageResource(iconRes)
    fun setPrimaryText(text: String?) = primaryText.setupFor(text)
    fun setSecondaryText(text: String?) = secondaryText.setupFor(text)
}