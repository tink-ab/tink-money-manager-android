package com.tink.moneymanagerui.statistics.overtime

import androidx.annotation.StringRes
import com.tink.moneymanagerui.R
import java.time.LocalDateTime

sealed class PeriodSelection(
    val start: LocalDateTime,
    val end: LocalDateTime,
    @StringRes val labelResource: Int
) {
    class TwelveMonths : PeriodSelection(
        LocalDateTime.now().minusMonths(12),
        LocalDateTime.now(),
        R.string.tink_selector_12_months
    )

    class SixMonths : PeriodSelection(
        LocalDateTime.now().minusMonths(6),
        LocalDateTime.now(),
        R.string.tink_selector_6_months
    )

    class AllTime : PeriodSelection(
        LocalDateTime.MIN,
        LocalDateTime.now(),
        R.string.tink_selector_all_time
    )
}
