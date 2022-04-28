package com.tink.moneymanagerui.theme

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.view.ContextThemeWrapper
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType

@ColorInt
internal fun Context.resolveColorForFeature(
    @AttrRes attrRes: Int,
    moneyManagerFeatureType: MoneyManagerFeatureType
): Int {
    val theme = FinanceOverviewFragment.featureSpecificThemes[moneyManagerFeatureType]?.let { themeRes ->
        ContextThemeWrapper(this, themeRes).theme
    } ?: theme

    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}
