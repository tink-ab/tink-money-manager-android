package com.tink.moneymanagerui.overview.charts

import androidx.annotation.StringRes
import com.tink.moneymanagerui.R
import org.joda.time.DateTime

sealed class PeriodSelection(
    val start: DateTime,
    val end: DateTime,
    @StringRes val labelResource: Int
) {
    class TwelveMonths : PeriodSelection(
        DateTime.now().minusMonths(12),
        DateTime.now(),
        R.string.tink_selector_12_months
    )

    class SixMonths : PeriodSelection(
        DateTime.now().minusMonths(6),
        DateTime.now(),
        R.string.tink_selector_6_months
    )

    class AllTime : PeriodSelection(
        DateTime(0),
        DateTime.now(),
        R.string.tink_selector_all_time
    )
}
