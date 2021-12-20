@file:JvmName("ContextUtils")

package se.tink.commons.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

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
fun Context.getDrawableResIdFromAttr(
    @AttrRes attrId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrId, typedValue, resolveRefs)
    return typedValue.resourceId
}

@JvmOverloads
fun Context.getThemeResIdFromAttr(
    @AttrRes attrId: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrId, typedValue, resolveRefs)
    return typedValue.resourceId
}
