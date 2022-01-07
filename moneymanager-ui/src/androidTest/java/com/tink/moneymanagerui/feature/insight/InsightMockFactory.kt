package com.tink.moneymanagerui.feature.insight

import com.tink.model.budget.BudgetPeriod
import com.tink.model.insights.*
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.relations.CommonTransactionsOverview
import com.tink.model.relations.LargestExpense
import com.tink.model.relations.TransactionSummary
import com.tink.model.time.YearMonth
import com.tink.model.time.YearWeek
import com.tink.moneymanagerui.budgets.creation.specification.EXACT_NUMBER_ZERO
import com.tink.moneymanagerui.insights.enrichment.*
import org.threeten.bp.Instant

object InsightMockFactory {
    
    val allTypes = listOf(
        InsightType.ACCOUNT_BALANCE_LOW,
        InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL,
        InsightType.BUDGET_CLOSE_NEGATIVE,
        InsightType.BUDGET_CLOSE_POSITIVE,
        InsightType.BUDGET_OVERSPENT,
        InsightType.BUDGET_SUCCESS,
        InsightType.BUDGET_SUGGEST_CREATE_FIRST,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY,
        InsightType.BUDGET_SUMMARY_ACHIEVED,
        InsightType.BUDGET_SUMMARY_OVERSPENT,
        InsightType.CREDIT_CARD_LIMIT_CLOSE,
        InsightType.CREDIT_CARD_LIMIT_REACHED,
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.LEFT_TO_SPEND_NEGATIVE,
        InsightType.LEFT_TO_SPEND_NEGATIVE_BEGINNING_MONTH,
        InsightType.LEFT_TO_SPEND_NEGATIVE_MID_MONTH,
        InsightType.LEFT_TO_SPEND_NEGATIVE_SUMMARY,
        InsightType.LEFT_TO_SPEND_POSITIVE_BEGINNING_MONTH,
        InsightType.LEFT_TO_SPEND_POSITIVE_FINAL_WEEK,
        InsightType.LEFT_TO_SPEND_POSITIVE_MID_MONTH,
        InsightType.LEFT_TO_SPEND_POSITIVE_SUMMARY_SAVINGS_ACCOUNT,
        InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.NEW_INCOME_TRANSACTION,
        InsightType.SINGLE_UNCATEGORIZED_TRANSACTION,
        InsightType.SPENDING_BY_CATEGORY_INCREASED,
        InsightType.SPENDING_BY_PRIMARY_CATEGORY_INCREASED,
        InsightType.SUGGEST_SET_UP_SAVINGS_ACCOUNT,
        InsightType.UNKNOWN,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
        InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS)

    val defaultActions = listOf(
        InsightAction(
            label = "action1",
            actionType = InsightAction.Type.VIEW_BUDGET,
            data = InsightAction.Data.Acknowledge)
    )
    
    fun getInsightsForTypes(types: List<InsightType> = allTypes): List<Insight> {
        return types.map { type ->
            return@map when (type) {
                /*
                InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL -> {
                    createRefreshCredentialsInsight()
                }
                 */
                InsightType.BUDGET_SUMMARY_ACHIEVED -> {
                    createBudgetSummaryAchievedInsight()
                }
                InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY,
                InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY -> {
                    val details = BudgetCreateSuggestionViewDetails(
                        savePercentage = "10",
                        savePerYearAmount = Amount(EXACT_NUMBER_ZERO, "SEK")
                    )
                    createInsight(id = type.name,
                        type = type,
                        data = InsightData.BudgetSuggestCreateFirstData,
                        details = details)
                }
                InsightType.LARGE_EXPENSE -> {
                    createLargeExpenseInsight()
                }
                InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS -> {
                    createMonthlySummaryExpenseTransactions()
                }
                InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY -> {
                    createWeeklySummaryExpenseByDay()
                }
                InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS -> {
                    createWeeklySummaryExpenseTransactions()
                }
                else -> {
                    createInsight(id = type.name,
                        type = type,
                        data = InsightData.BudgetSuggestCreateFirstData)
                }
            }
        }
    }

    /* TODO: Uncomment when Core is updated with InsightAction.Type.REFRESH_CREDENTIAL
    private fun createRefreshCredentialsInsight(): Insight {
        val actions = listOf(
            InsightAction(
                label = "Refresh label",
                actionType = InsightAction.Type.REFRESH_CREDENTIAL,
                data = InsightAction.Data.RefreshCredential("1234")),
            InsightAction(
                label = "Dismiss label",
                actionType = InsightAction.Type.DISMISS,
                data = InsightAction.Data.Dismiss)
        )

        val now = DateTime.now().toInstant().millis
        val insightData = InsightData.AggregationRefreshPsd2CredentialData(
            id = "123",
            credential = InsightData.Credential(
                id = "444",
                provider = InsightData.ProviderSlim(
                    name = "ND1",
                    displayName = "Revolut"
                )
            ),
            sessionExpiryDate = now
        )

        return createInsight(id = InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL.name,
            type = InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL,
            actions = actions,
            data = insightData)
    }
     */

    private fun createLargeExpenseInsight(): Insight {
        val actions = listOf(
            InsightAction(
                label = "View transaction label",
                actionType = InsightAction.Type.VIEW_TRANSACTION,
                data = InsightAction.Data.NoData),
            InsightAction(
                label = "Dismiss label",
                actionType = InsightAction.Type.DISMISS,
                data = InsightAction.Data.Dismiss)
        )

        val insightData = InsightData.MonthlyExpenseTransactionsData(
            YearMonth(2021, 12),
            TransactionSummary(
                CommonTransactionsOverview(
                    "Common transaction description",
                    0,
                    0
                ), Amount(EXACT_NUMBER_ZERO, "SEK"),
                LargestExpense(1L, Amount(EXACT_NUMBER_ZERO, "SEK"), "...", "123")
            )
        )

        return createInsight(id = InsightType.LARGE_EXPENSE.name,
            type = InsightType.LARGE_EXPENSE,
            actions = actions,
            data = insightData)
    }

    private fun createMonthlySummaryExpenseTransactions(): Insight {
        val insightData = InsightData.MonthlyExpenseTransactionsData(
            YearMonth(2021, 12),
            TransactionSummary(
                CommonTransactionsOverview(
                    "Common transaction description",
                    0,
                    0
                ), Amount(EXACT_NUMBER_ZERO, "SEK"),
                LargestExpense(1L, Amount(EXACT_NUMBER_ZERO, "SEK"), "...", "123")
            )
        )
        return createInsight(id = InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS.name,
            type = InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS,
            data = insightData)
    }

    private fun createWeeklySummaryExpenseTransactions(): Insight {
        val insightData = InsightData.WeeklyExpenseTransactionsData(
            YearWeek(2021, 12),
            TransactionSummary(
                CommonTransactionsOverview(
                    "Common transaction description",
                    0,
                    0
                ), Amount(EXACT_NUMBER_ZERO, "SEK"),
                LargestExpense(1L, Amount(EXACT_NUMBER_ZERO, "SEK"), "...", "123")
            )
        )
        return createInsight(id = InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS.name,
            type = InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
            data = insightData)
    }

    private fun createWeeklySummaryExpenseByDay(): Insight {
        val insightData = InsightData.WeeklyExpensesByDayData(
            YearWeek(2021, 40),
            listOf()
        )
        return createInsight(id = InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY.name,
            type = InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
            data = insightData)
    }

    private fun createBudgetSummaryAchievedInsight(
    ): Insight {
        val iconType: IconTypeViewDetails = IconTypeViewDetails.Category("1")
        val summaryDetailsItem = BudgetSummaryDetailItem(
            budgetState = BudgetState.SUCCESS,
            iconTypeViewDetails = iconType
        )
        val details = BudgetSummaryViewDetails(
            listOf(summaryDetailsItem, summaryDetailsItem, summaryDetailsItem, summaryDetailsItem, summaryDetailsItem),
            "1000", "750", 0.4
        )

        val insightData = InsightData.BudgetSummaryAchievedData(
            listOf(
                InsightData.BudgetIdToPeriod(
                    "1234", BudgetPeriod(
                        start = Instant.now(),
                        end = Instant.now(),
                        spentAmount = Amount(ExactNumber(45), "SEK"),
                        budgetAmount = Amount(ExactNumber(100), "SEK")
                    )
                ),
                InsightData.BudgetIdToPeriod(
                    "1235", BudgetPeriod(
                        start = Instant.now(),
                        end = Instant.now(),
                        spentAmount = Amount(ExactNumber(2000), "SEK"),
                        budgetAmount = Amount(ExactNumber(100), "SEK")
                    )
                )
            ),
            listOf(),
            Amount(ExactNumber(1234), "SEK")
        )
        return createInsight(id = InsightType.BUDGET_SUMMARY_ACHIEVED.name,
            type = InsightType.BUDGET_SUMMARY_ACHIEVED,
            data = insightData,
            details = details)
    }

    fun createInsight(id: String,
                      type: InsightType = InsightType.UNKNOWN,
                      actions: List<InsightAction> = defaultActions,
                      data: InsightData,
                      details: Insight.ViewDetails? = null): Insight {
        return Insight(id = id,
            type = type,
            title = "Title $id",
            description = "description $id",
            created = Instant.now(),
            actions = actions,
            state = InsightState.Active,
            data = data,
            viewDetails = details
        )
    }
}