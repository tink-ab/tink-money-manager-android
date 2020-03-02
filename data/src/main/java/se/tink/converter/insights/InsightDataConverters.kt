package se.tink.converter.insights

import se.tink.converter.ModelConverter
import se.tink.converter.date.toDateTime
import se.tink.converter.misc.toAmount
import se.tink.core.models.budgets.Budget
import se.tink.core.models.insights.InsightData
import se.tink.core.models.misc.YearWeek
import se.tink.modelConverter.AbstractConverter
import se.tink.grpc.v1.models.InsightData as InsightDataDTO

private typealias DataCase = se.tink.grpc.v1.models.InsightData.DataCase

class InsightDataConverter(private val converter: ModelConverter) :
    AbstractConverter<InsightDataDTO, InsightData>() {
    override fun convert(source: InsightDataDTO): InsightData =
        with(source) {
            when (dataCase) {
                DataCase.ACCOUNT_BALANCE_LOW -> accountBalanceLow.toCoreModel()
                DataCase.BUDGET_SUCCESS -> budgetSuccess.toCoreModel()
                DataCase.BUDGET_OVERSPENT -> budgetOverspent.toCoreModel()
                DataCase.BUDGET_SUMMARY_ACHIEVED -> budgetSummaryAchieved.toCoreModel()
                DataCase.BUDGET_SUMMARY_OVERSPENT -> budgetSummaryOverspent.toCoreModel()
                DataCase.BUDGET_CLOSE_POSITIVE -> budgetClosePositive.toCoreModel()
                DataCase.BUDGET_CLOSE_NEGATIVE -> budgetCloseNegative.toCoreModel()
                DataCase.LARGE_EXPENSE -> largeExpense.toCoreModel()
                DataCase.DOUBLE_CHARGE -> doubleCharge.toCoreModel()
                DataCase.SINGLE_UNCATEGORIZED_EXPENSE -> singleUncategorizedExpense.toCoreModel()
                DataCase.WEEKLY_EXPENSES_BY_CATEGORY -> weeklyExpensesByCategory.toCoreModel()
                DataCase.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> weeklyUncategorizedTransactions.toCoreModel()
                DataCase.WEEKLY_EXPENSES_BY_DAY, //TODO
                DataCase.LEFT_TO_SPEND_NEGATIVE, //TODO
                DataCase.MONTHLY_EXPENSES_BY_CATEGORY, //TODO
                null, DataCase.DATA_NOT_SET -> InsightData.NoData
            }
        }

    fun InsightDataDTO.Data.AccountBalanceLow.toCoreModel() =
        InsightData.AccountBalanceLowData(accountId, balance.toAmount())

    fun InsightDataDTO.Data.BudgetSuccess.toCoreModel() =
        InsightData.BudgetResultData(
            budgetId,
            converter.map(budgetPeriod, Budget.Period::class.java)
        )

    fun InsightDataDTO.Data.BudgetOverspent.toCoreModel() =
        InsightData.BudgetResultData(
            budgetId,
            converter.map(budgetPeriod, Budget.Period::class.java)
        )

    fun InsightDataDTO.Data.BudgetSummaryAchieved.toCoreModel() =
        InsightData.BudgetSummaryAchievedData(
            achievedBudgetsList.map { it.toCoreModel() },
            overspentBudgetsList.map { it.toCoreModel() },
            savedAmount.toAmount()
        )

    fun InsightDataDTO.Data.BudgetSummaryOverspent.toCoreModel() =
        InsightData.BudgetSummaryOverspentData(
            achievedBudgetsList.map { it.toCoreModel() },
            overspentBudgetsList.map { it.toCoreModel() },
            overspentAmount.toAmount()
        )

    fun InsightDataDTO.Data.BudgetCloseNegative.toCoreModel() =
        InsightData.BudgetCloseData(
            budgetId,
            converter.map(budgetPeriod, Budget.Period::class.java),
            currentTime.toDateTime()
        )

    fun InsightDataDTO.Data.BudgetClosePositive.toCoreModel() =
        InsightData.BudgetCloseData(
            budgetId,
            converter.map(budgetPeriod, Budget.Period::class.java),
            currentTime.toDateTime()
        )

    fun InsightDataDTO.Data.BudgetSummary.toCoreModel() =
        InsightData.BudgetSummary(
            budgetId,
            converter.map(budgetPeriod, Budget.Period::class.java)
        )

    fun InsightDataDTO.Data.SingleUncategorizedExpense.toCoreModel() =
        InsightData.UncategorizedTransactionData(transactionId)

    fun InsightDataDTO.Data.LargeExpense.toCoreModel() =
        InsightData.LargeExpenseData(transactionId)

    fun InsightDataDTO.Data.DoubleCharge.toCoreModel() =
        InsightData.DoubleChargeData(transactionIdsList)

    fun InsightDataDTO.Data.WeeklyExpensesByCategory.toCoreModel() =
        InsightData.WeeklyExpensesByCategoryData(
            week = converter.map(week, YearWeek::class.java),
            expenses = spendingByCategoryList.toAmountByCategoryList()
        )

    fun InsightDataDTO.Data.WeeklyUncategorizedTransactions.toCoreModel() =
        InsightData.WeeklyUncategorizedTransactionsData(
            week = converter.map(week, YearWeek::class.java),
            transactionIds = transactionIdsList
        )
}
