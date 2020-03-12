package se.tink.commons.extensions

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.ColorRes

fun ImageView.tint(color: Int) {
    imageTintList = ColorStateList.valueOf(context.getColorFromAttr(color))
}

fun ImageView.backgroundTint(color: Int) {
    backgroundTintList = ColorStateList.valueOf(context.getColorFromAttr(color))
}

fun ImageView.setImageResFromAttr(attrResId: Int) =
    context.getDrawableResIdFromAttr(attrResId).also(::setImageResource)
