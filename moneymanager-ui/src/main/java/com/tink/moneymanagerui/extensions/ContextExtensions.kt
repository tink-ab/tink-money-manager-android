@file:JvmName("ContextUtils")

package com.tink.moneymanagerui.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import com.tink.moneymanagerui.R

internal sealed class Attribute(@AttrRes val id: Int)

internal class ColorAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Int = Color.BLACK,
                          val setValue: (Int) -> Unit) : Attribute(id)

internal class DimensionAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Float = 0f,
                          val setValue: (Float) -> Unit) : Attribute(id)

internal class TypefaceAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Int = R.font.tink_font_regular,
                          val setValue: (Typeface?) -> Unit) : Attribute(id)

internal class BooleanAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Boolean = false,
                          val setValue: (Boolean) -> Unit) : Attribute(id)

internal class FloatAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Float = 0f,
                          val setValue: (Float) -> Unit) : Attribute(id)


internal fun Context.fetchThemeAttributesFromStyle(@StyleRes themeId: Int, attrs: List<Attribute>) {
    val sortedAttrs = attrs.sortedBy { it.id }
    val typedArray = obtainStyledAttributes(themeId, sortedAttrs.map { it.id }.toIntArray())
    try {
        for ((idx, attr) in sortedAttrs.withIndex()) {
            when (attr) {
                is ColorAttribute -> attr.setValue(typedArray.getColor(idx, attr.defVal))
                is DimensionAttribute -> attr.setValue(typedArray.getDimension(idx, attr.defVal))
                is TypefaceAttribute -> attr.setValue(ResourcesCompat.getFont(this, typedArray.getResourceId(idx, attr.defVal)))
                is BooleanAttribute -> attr.setValue(typedArray.getBoolean(idx, attr.defVal))
                is FloatAttribute -> attr.setValue(typedArray.getFloat(idx, attr.defVal))
            }
        }
    } finally {
        typedArray.recycle()
    }
}