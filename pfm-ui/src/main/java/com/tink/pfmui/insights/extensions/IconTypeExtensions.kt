package com.tink.pfmui.insights.extensions

import com.tink.pfmui.R
import com.tink.pfmui.insights.enrichment.IconTypeViewDetails
import se.tink.commons.categories.enums.CategoryType
import se.tink.commons.categories.iconFromCategoryCode
import se.tink.commons.icons.IconResource

internal fun IconTypeViewDetails?.getIcon(): IconResource =
    when (this) {
        is IconTypeViewDetails.Category -> IconResource.Attribute(iconFromCategoryCode(categoryCode))
        is IconTypeViewDetails.Search -> IconResource.DrawableId(R.drawable.tink_search)
        is IconTypeViewDetails.Tag -> IconResource.DrawableId(R.drawable.tink_transaction_tag)
        is IconTypeViewDetails.Expenses -> IconResource.Attribute(iconFromCategoryCode(CategoryType.EXPENSES.stringCode))
        else -> IconResource.Attribute(R.attr.tink_icon_category_uncategorized)
    }

