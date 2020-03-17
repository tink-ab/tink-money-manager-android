package se.tink.core.models.insights
//
//import org.joda.time.DateTime
//import com.tink.model.budget.Budget
//import se.tink.core.models.misc.Amount
//import se.tink.core.models.misc.YearMonth
//import se.tink.core.models.misc.YearWeek
//import se.tink.core.models.relations.AmountByCategory
//import se.tink.core.models.relations.ExpensesByDay
//
//data class Insight(
//    val id: String,
//    val type: InsightType,
//    val title: String,
//    val description: String,
//    val created: DateTime,
//    val actions: List<InsightAction>,
//    val state: InsightState,
//    val data: InsightData,
//    var viewDetails: ViewDetails? = null
//) {
//    /**
//     * All subclasses should be data classes or provide a meaningful `equals()` function
//     */
//    interface ViewDetails
//}
//
//data class InsightAction(
//    val label: String,
//    val data: Data
//) {
//
//    /**
//     * All subclasses should be data classes or provide a meaningful `equals()` function
//     */
//    sealed class Data {
//
//        object NoData : Data()
//        object Acknowledge : Data()
//        object Dismiss : Data()
//
//        data class ViewBudget(val budgetId: String, val periodStartDate: DateTime) : Data()
//
//        data class CreateTransfer(
//            val sourceUri: String,
//            val destinationUri: String,
//            val amount: Amount
//        ) : Data()
//
//        data class CategorizeExpense(val transactionId: String) : Data()
//
//        data class ViewTransactions(val transactionIds: List<String>) : Data() {
//            constructor(transactionId: String) : this(listOf(transactionId))
//        }
//
//        data class ViewTransactionsByCategory(
//            val transactionsByCategory: Map<String, List<String>>
//        ) : Data()
//    }
//}
//
//enum class InsightType {
//    UNKNOWN,
//    LEFT_TO_SPEND_HIGH,
//    LEFT_TO_SPEND_LOW,
//    ACCOUNT_BALANCE_LOW,
//    INCREASE_CATEGORIZATION_LEVEL,
//    ALL_BANKS_CONNECTED,
//    EINVOICE,
//    GENERIC_FRAUD,
//    HIGHER_INCOME_THAN_CERTAIN_PERCENTILE,
//    RATE_THIS_APP,
//    RESIDENCE_DO_YOU_OWN_IT,
//    MONTHLY_SUMMARY,
//    WEEKLY_SUMMARY,
//    EINVOICE_OVERDUE,
//    BUDGET_OVERSPENT,
//    BUDGET_CLOSE_NEGATIVE,
//    BUDGET_CLOSE_POSITIVE,
//    BUDGET_STREAK,
//    BUDGET_SUCCESS,
//    BUDGET_SUMMARY_OVERSPENT,
//    BUDGET_SUMMARY_ACHIEVED,
//    SINGLE_UNCATEGORIZED_TRANSACTION,
//    LARGE_EXPENSE,
//    DOUBLE_CHARGE,
//    WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY,
//    WEEKLY_SUMMARY_EXPENSES_BY_DAY,
//    WEEKLY_UNCATEGORIZED_TRANSACTIONS,
//    MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY
//}
//
//sealed class InsightState {
//    data class Archived(val archivedDate: DateTime) : InsightState()
//    object Active : InsightState()
//}
//
///**
// * All subclasses should be data classes or provide a meaningful `equals()` function
// */
//sealed class InsightData {
//
//    object NoData : InsightData()
//
//    data class AccountBalanceLowData(
//        val accountId: String,
//        val balance: Amount
//    ) : InsightData()
//
//    data class BudgetResultData(
//        val budgetId: String,
//        val budgetPeriod: Budget.Period
//    ) : InsightData()
//
//    data class BudgetSummaryAchievedData(
//        val achievedBudgets: List<BudgetSummary>,
//        val overspentBudgets: List<BudgetSummary>,
//        val savedAmount: Amount
//    ) : InsightData()
//
//    data class BudgetSummaryOverspentData(
//        val achievedBudgets: List<BudgetSummary>,
//        val overspentBudgets: List<BudgetSummary>,
//        val overspentAmount: Amount
//    ) : InsightData()
//
//    data class BudgetCloseData(
//        val budgetId: String,
//        val budgetPeriod: Budget.Period,
//        val currentTime: DateTime
//    ) : InsightData()
//
//    data class BudgetSummary(
//        val budgetId: String,
//        val budgetPeriod: Budget.Period
//    )
//
//    data class UncategorizedTransactionData(
//        val transactionId: String
//    ) : InsightData()
//
//    data class LargeExpenseData(
//        val transactionId: String
//    ) : InsightData()
//
//    data class DoubleChargeData(
//        val transactionIds: List<String>
//    ) : InsightData()
//
//    data class WeeklyExpensesByCategoryData(
//        val week: YearWeek,
//        val expenses: List<AmountByCategory>
//    ) : InsightData()
//
//    data class WeeklyExpensesByDayData(
//        val week: YearWeek,
//        val expensesByDay: List<ExpensesByDay>
//    ) : InsightData()
//
//    data class WeeklyUncategorizedTransactionsData(
//        val week: YearWeek,
//        val transactionIds: List<String>
//    ) : InsightData()
//
//    data class MonthlySummaryExpensesByCategoryData(
//        val month: YearMonth,
//        val expenses: List<AmountByCategory>
//    ) : InsightData()
//}
