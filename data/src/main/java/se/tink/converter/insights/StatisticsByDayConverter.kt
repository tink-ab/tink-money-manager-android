package se.tink.converter.insights

import com.google.type.Date
import org.joda.time.DateTime
import se.tink.converter.misc.toAmount
import se.tink.core.models.relations.ExpensesByDay
import se.tink.grpc.v1.models.InsightData

fun InsightData.Data.WeeklyExpensesByDay.ExpenseStatisticsByDay.toExpensesByDay() =
    ExpensesByDay(
        date = date.toDateTime(),
        totalAmount = expenseStatistics.totalAmount.toAmount(),
        averageAmount = expenseStatistics.averageAmount.toAmount()
    )

private fun Date.toDateTime() = DateTime(year, month, day, 0, 0)