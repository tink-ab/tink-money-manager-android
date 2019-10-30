package se.tink.commons.extensions

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.annotation.ColorRes

fun ImageView.tint(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(context.getColorCompat(color))
}

fun ImageView.backgroundTint(@ColorRes color: Int) {
    backgroundTintList = ColorStateList.valueOf(context.getColorCompat(color))
}
