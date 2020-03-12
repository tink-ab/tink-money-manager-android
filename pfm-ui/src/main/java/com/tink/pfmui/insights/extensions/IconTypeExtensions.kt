package com.tink.pfmui.insights.extensions

import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import com.tink.pfmui.R
import com.tink.pfmui.insights.enrichment.IconTypeViewDetails
import se.tink.commons.categories.iconFromCategoryCode

internal fun IconTypeViewDetails?.getIcon(): IconResource =
    when (this) {
        is IconTypeViewDetails.Category -> IconResource.Attribute(iconFromCategoryCode(categoryCode))
        is IconTypeViewDetails.Search -> IconResource.DrawableId(R.drawable.ic_search)
        is IconTypeViewDetails.Tag -> IconResource.DrawableId(R.drawable.ic_transaction_tag)
        else -> IconResource.Attribute(R.attr.tink_icon_category_uncategorized)
    }

sealed class IconResource {
    data class Attribute(@field:AttrRes val attrResId: Int) : IconResource()
    data class DrawableId(@field:DrawableRes val drawableResId: Int) : IconResource()
}
