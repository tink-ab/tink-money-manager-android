package se.tink.commons.icons

import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes

sealed class IconResource {
    data class Attribute(@field:AttrRes val attrResId: Int) : IconResource()
    data class DrawableId(@field:DrawableRes val drawableResId: Int) : IconResource()
}
