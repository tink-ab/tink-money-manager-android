package com.tink.moneymanagerui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.tink.moneymanagerui.util.DimensionUtils
import java.util.*

@Deprecated("Use MaterialTextView instead", ReplaceWith("com.google.android.material.textview.MaterialTextView"))
class TinkTextView : AppCompatTextView {
    private var theme: Theme? = null
    private var compundPaddingTop = 0
    private var ignoreBaseLine = false
    private var helper: BaseLineHelper? = null

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        val materialStep = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics
        )
        helper = BaseLineHelper(materialStep)
        includeFontPadding = false
    }

    fun setTheme(theme: Theme) {
        this.theme = theme
        typeface = theme.font
        setTextColor(theme.textColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.textSize)
        letterSpacing = theme.spacing
        setLineSpacing(theme.lineHeight, 0f)
        var text = text.toString()
        if (theme.toUpperCase() && text.isNotEmpty()) {
            text = text.uppercase(Locale.getDefault())
            setText(text)
        }
    }

    override fun getCompoundPaddingTop(): Int {
        return super.getCompoundPaddingTop() + compundPaddingTop
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        compundPaddingTop = 0
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ignoreBaseLine) {
            return
        }
        val oneDpExtraHeightToAvoidClipping =
            DimensionUtils.getPixelsFromDP(1f, context)
        compundPaddingTop = helper!!.getCompoundTopPaddingToPutBaselineOnGrid(baseline)
        var height = measuredHeight
        height += helper!!.putHeightOnGrid(height)
        height += oneDpExtraHeightToAvoidClipping.toInt()
        setMeasuredDimension(measuredWidth, height)
    }

    override fun setText(
        text: CharSequence,
        type: BufferType
    ) {
        var themedText = text
        if (theme != null && theme!!.toUpperCase()) {
            themedText = themedText.toString().toUpperCase()
        }
        super.setText(themedText, type)
    }

    interface Theme {
        val textColor: Int
        val font: Typeface?
        val textSize: Float
        val lineHeight: Float
        val spacing: Float
        fun toUpperCase(): Boolean
    }
}