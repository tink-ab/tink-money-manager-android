package com.tink.pfmui.insights.extensions

import com.tink.pfmui.R
import com.tink.pfmui.insights.enrichment.IconTypeViewDetails
import se.tink.commons.categories.iconFromCategoryCode

internal fun IconTypeViewDetails?.getIcon(): Int =
    when (this) {
        is IconTypeViewDetails.Category -> iconFromCategoryCode(categoryCode)
        is IconTypeViewDetails.Search -> R.drawable.ic_search
        is IconTypeViewDetails.Tag -> R.drawable.ic_transaction_tag
        else -> R.drawable.ic_uncategorized
    }
