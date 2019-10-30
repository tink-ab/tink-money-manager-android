package se.tink.commons.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

private const val textSizeAttr = android.R.attr.textSize
private const val textColorAttr = android.R.attr.textColor
private const val fontFamilyAttr = android.R.attr.fontFamily

private val textLabelAttributes = arrayOf(
    textSizeAttr,
    textColorAttr,
    fontFamilyAttr
)
    .toIntArray().apply { sort() }
//Framework bug, values have to be sorted: http://stackoverflow.com/questions/19034597/get-multiple-style-attributes-with-obtainstyledattributes?lq=1

private fun index(id: Int) = textLabelAttributes.indexOf(id)

fun extractTextStyle(context: Context, styleId: Int): TextStyle {
    return context.theme.obtainStyledAttributes(styleId, textLabelAttributes).run {
        try {
            TextStyle(
                textSize = getDimension(
                    index(textSizeAttr),
                    0f
                ),
                textColor = getColor(
                    index(textColorAttr),
                    Color.CYAN
                ),
                fontFamily = ResourcesCompat.getFont(
                    context,
                    getResourceId(
                        index(fontFamilyAttr),
                        0
                    )
                )
            )
        } finally {
            recycle()
        }
    }
}

data class TextStyle(
    val textSize: Float,
    val textColor: Int,
    val fontFamily: Typeface?
)


