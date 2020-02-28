package se.tink.converter.insights

import com.google.type.Date
import org.joda.time.DateTime
import se.tink.converter.misc.toAmount
import se.tink.core.models.relations.StatisticsByDay
import se.tink.grpc.v1.models.InsightData

fun List<InsightData.Data.WeeklyExpensesByDay.ExpenseStatisticsByDay>.toStatisticsByDayList() =
    map {
        StatisticsByDay(
            date = it.date.toDateTime(),
            totalAmount = it.expenseStatistics.totalAmount.toAmount(),
            averageAmount = it.expenseStatistics.averageAmount.toAmount()
        )
    }

private fun Date.toDateTime() = DateTime(year, month, day, 0, 0)