package se.tink.commons.extensions

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import com.google.android.material.color.MaterialColors
import se.tink.commons.icons.IconResource

fun ImageView.tint(color: Int) {
    imageTintList = ColorStateList.valueOf(context.getColorFromAttr(color))
}

fun ImageView.backgroundTint(color: Int) {
    backgroundTintList = ColorStateList.valueOf(context.getColorFromAttr(color))
}

fun ImageView.setImageResFromAttr(attrResId: Int) {
    context.getDrawableResIdFromAttr(attrResId).also(::setImageResource)
}

fun ImageView.setIconRes(iconRes: IconResource) =
    when (iconRes) {
        is IconResource.Attribute -> setImageResFromAttr(iconRes.attrResId)
        is IconResource.DrawableId -> setImageResource(iconRes.drawableResId)
    }

fun ImageView.setPendingTransactionIconColor(color: Int) {
    tint(color)
    setShapeStrokeColor(color)
}

fun ImageView.setShapeStrokeColor(
    iconColor: Int,
    strokeWidth: Int = 5,
    dashWidth: Float = 5f,
    dashGap: Float = 8f
) {
    (background as GradientDrawable).apply {
        mutate()
        val color = MaterialColors.getColor(context, iconColor, Color.GRAY)
        setStroke(strokeWidth, color, dashWidth, dashGap)
    }
}
