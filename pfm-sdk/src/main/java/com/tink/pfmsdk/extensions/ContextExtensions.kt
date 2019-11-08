@file:JvmName("ContextUtils")

package com.tink.pfmsdk.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import com.tink.pfmsdk.R

sealed class Attribute(@AttrRes val id: Int)

class ColorAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Int = Color.BLACK,
                          val setValue: (Int) -> Unit) : Attribute(id)

class DimensionAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Float = 0f,
                          val setValue: (Float) -> Unit) : Attribute(id)

class TypefaceAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Int = R.font.font_regular,
                          val setValue: (Typeface?) -> Unit) : Attribute(id)

class BooleanAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Boolean = false,
                          val setValue: (Boolean) -> Unit) : Attribute(id)

class FloatAttribute
@JvmOverloads constructor(@AttrRes id: Int,
                          val defVal: Float = 0f,
                          val setValue: (Float) -> Unit) : Attribute(id)


fun Context.fetchThemeAttributesFromStyle(@StyleRes themeId: Int, attrs: List<Attribute>) {
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