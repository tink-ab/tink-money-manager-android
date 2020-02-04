@file:JvmName("PeriodBalances")

package com.tink.pfmsdk.util.extensions

import com.tink.pfmsdk.charts.models.PeriodBalance

/**
 * Get average amount for the list, but ignore the last one.
 *
 * This should be used when looking at the previous X periods,
 * since the last one will then always be the current one and thus be incomplete.
 */
internal fun List<PeriodBalance>.getAverageIgnoreLast(): Double {
    return dropLast(1)
        .map { it.amount }
        .average()
}