package com.tink.pfmsdk.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.google.common.base.Strings
import com.tink.pfmsdk.util.DimensionUtils

internal class TinkTextView : AppCompatTextView {
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
        // TODO: PFMSDK: Resource IDs cannot be used for switch expressions in a library module
//        if (attrs != null) {
//            setCustomAttributes(context, attrs);
//        }
    }

    // TODO: PFMSDK: Resource IDs cannot be used for switch expressions in a library module
//    private void setCustomAttributes(Context context, AttributeSet attrs) {
//		@SuppressLint("CustomViewStyleable")
//		TypedArray arrayAttributes = context
//			.obtainStyledAttributes(attrs, R.styleable.TinkWidgetStyle);
//
//		try {
//			int n = arrayAttributes.getIndexCount();
//			for (int i = 0; i < n; i++) {
//				int attr = arrayAttributes.getIndex(i);
//				switch (attr) {
//					case R.styleable.TinkWidgetStyle_ignore_baseLine:
//						ignoreBaseLine = true;
//						break;
//					case R.styleable.TinkWidgetStyle_lineHeight:
//						float dimension = arrayAttributes.getDimension(attr, 0);
//						setLineSpacing(dimension, 0);
//						break;
//				}
//			}
//		} finally {
//			arrayAttributes.recycle();
//		}
//	}

    fun setTheme(theme: Theme) {
        this.theme = theme
        typeface = theme.font
        setTextColor(theme.textColor)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.textSize)
        letterSpacing = theme.spacing
        setLineSpacing(theme.lineHeight, 0f)
        var text = text.toString()
        if (theme.toUpperCase() && !Strings.isNullOrEmpty(text)) {
            text = text.toUpperCase()
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

    fun setIgnoreBaseline(ignoreBaseline: Boolean) {
        ignoreBaseLine = ignoreBaseline
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