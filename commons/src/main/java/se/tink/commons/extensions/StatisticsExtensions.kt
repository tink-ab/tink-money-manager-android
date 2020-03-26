package se.tink.commons.extensions

import com.tink.model.statistic.Statistic
import com.tink.model.statistic.StatisticTree
import com.tink.model.time.Period
import java.util.HashMap

fun StatisticTree.extractPeriods(): Map<String, Period> {
    val output: MutableMap<String, Period> = HashMap()
    extractPeriodsFromMap(
        balancesByAccountGroupType.children,
        output
    )
    extractPeriodsFromMap(
        balancesByAccountId.children,
        output
    )
    extractPeriodsFromMap(
        expensesByCategoryCode.children,
        output
    )
    extractPeriodsFromMap(
        incomeByCategoryCode.children,
        output
    )
    extractPeriodsFromMap(
        leftToSpendAverage.children,
        output
    )
    extractPeriodsFromMap(
        leftToSpend.children,
        output
    )
    return output
}

private fun extractPeriodsFromMap(
    input: Map<String, Statistic>?,
    output: MutableMap<String, Period>
) {
    if (input == null) {
        return
    }
    for ((_, value) in input) {
        val period = value.period
        output[period.toString()] = period
        if (value.children.isNotEmpty()) {
            extractPeriodsFromMap(
                value.children,
                output
            )
        }
    }
}
