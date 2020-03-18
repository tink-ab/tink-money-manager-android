package se.tink.converter.insights

import com.google.type.Date
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

//fun InsightData.Data.WeeklyExpensesByDay.ExpenseStatisticsByDay.toExpensesByDay() =
//    ExpensesByDay(
//        date = date.toDateTime(),
//        totalAmount = expenseStatistics.totalAmount.toAmount(),
//        averageAmount = expenseStatistics.averageAmount.toAmount()
//    )

private fun Date.toDateTime() = DateTime(year, month, day, 0, 0).withZoneRetainFields(DateTimeZone.getDefault())