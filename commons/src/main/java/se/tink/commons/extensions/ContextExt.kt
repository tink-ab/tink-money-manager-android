@file:JvmName("ContextUtils")

package se.tink.commons.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


@JvmOverloads
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

@JvmOverloads
fun Context.getDrawableFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.resourceId
}

fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)