package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.model.relations.TransactionSummary
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import com.tink.moneymanagerui.util.extensions.formatCurrencyRound
import kotlinx.android.synthetic.main.tink_item_insight_transactions_summary.view.*
import org.joda.time.DateTime
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.inflate
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import javax.inject.Inject

@ContributesInsightViewProvider
class TransactionsSummaryViewProvider @Inject constructor(
    val dateUtils: DateUtils
) : InsightViewProvider {

    override val supportedInsightTypes: List<InsightType> =
        listOf(
          InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
          InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS
        )

    override val viewType: InsightViewType = getViewType()

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        TransactionsSummaryViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        return when (insight.data) {
            is InsightData.WeeklyExpenseTransactionsData -> {
                TransactionsSummaryDataHolder(
                    TransactionSummaryType.WEEKLY_TRANSACTION_SUMMARY,
                    (insight.data as InsightData.WeeklyExpenseTransactionsData).transactionSummary
                )
            }
            is InsightData.MonthlyExpenseTransactionsData -> {
                TransactionsSummaryDataHolder(
                    TransactionSummaryType.MONTHLY_TRANSACTION_SUMMARY,
                    (insight.data as InsightData.MonthlyExpenseTransactionsData).transactionSummary
                )
            }
            else -> {
                throw IllegalStateException("InsightData for TransactionsSummaryViewProvider must be either WeeklyExpenseTransactionsData or MonthlyExpenseTransactionsData")
            }
        }
    }

    inner class TransactionsSummaryViewHolder(
        parent: ViewGroup, actionHandler: ActionHandler
    ) : InsightViewHolder(
        parent.inflate(R.layout.tink_item_insight_transactions_summary),
        actionHandler
    ), InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {

            require(data is TransactionsSummaryDataHolder)

            setupCommonBottomPart(insight)

            with(itemView) {
                tink_insights_transactions_summary_title.text = context.getString(
                    when (data.transactionSummaryType) {
                        TransactionSummaryType.WEEKLY_TRANSACTION_SUMMARY -> R.string.tink_insights_weekly_summary_expense_transactions_title
                        TransactionSummaryType.MONTHLY_TRANSACTION_SUMMARY -> R.string.tink_insights_monthly_summary_expense_transactions_title
                    }
                )

                val totalExpenses = data.transactionSummary.totalExpenses.formatCurrencyRound()
                total_expenses_text.text = context.getString(R.string.tink_insights_total_expenses_text, totalExpenses)

                transactions_last_month_text.text = context.getString(
                    when (data.transactionSummaryType) {
                        TransactionSummaryType.WEEKLY_TRANSACTION_SUMMARY -> R.string.tink_insights_total_weekly_transactions_text
                        TransactionSummaryType.MONTHLY_TRANSACTION_SUMMARY -> R.string.tink_insights_total_monthly_transactions_text
                    }, data.transactionSummary.commonTransactionsOverview.totalNumberOfTransactions
                )
                most_common_transaction_last_month_text.text = context.getString(R.string.tink_insights_most_common_transaction_text,
                    data.transactionSummary.commonTransactionsOverview.mostCommonTransactionDescription,
                    data.transactionSummary.commonTransactionsOverview.mostCommonTransactionCount
                )

                val largestExpense = data.transactionSummary.largestExpense.amount.formatCurrencyRound()
                largest_transaction_text.text = context.getString(R.string.tink_insights_largest_transaction_text, largestExpense)
                largest_transaction_recipient_text.text = context.getString(R.string.tink_insights_largest_transaction_recipient_text,
                    data.transactionSummary.largestExpense.description,
                    dateUtils.getMonthWithDayOfMonth(DateTime(data.transactionSummary.largestExpense.date))
                )
            }
        }
    }
}

class TransactionsSummaryDataHolder(
    val transactionSummaryType: TransactionSummaryType,
    val transactionSummary: TransactionSummary
) : InsightDataHolder

enum class TransactionSummaryType {
    WEEKLY_TRANSACTION_SUMMARY,
    MONTHLY_TRANSACTION_SUMMARY
}