package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import com.tink.moneymanagerui.insights.enrichment.TransactionViewDetails
import com.tink.moneymanagerui.insights.enrichment.TransactionsViewDetails
import com.tink.moneymanagerui.util.EspressoIdlingResource
import com.tink.moneymanagerui.util.extensions.formatCurrencyExact
import kotlinx.android.synthetic.main.tink_item_insight_single_expense_uncategorized.view.*
import org.threeten.bp.Instant
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.commons.extensions.toDateTime
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import javax.inject.Inject

@ContributesInsightViewProvider
class SingleExpenseUncategorizedViewProvider @Inject constructor(
    val dateUtils: DateUtils
) : InsightViewProvider {
    override val supportedInsightTypes: List<InsightType> =
        listOf(
            InsightType.SINGLE_UNCATEGORIZED_TRANSACTION,
            InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS
        )

    override val viewType: InsightViewType = getViewType()

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        SingleExpenseUncategorizedViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        return when(insight.data) {
            is InsightData.UncategorizedTransactionData -> {
                (insight.viewDetails as? TransactionViewDetails)?.let {
                    SingleExpenseUncategorizedDataHolder(
                        it.description,
                        dateUtils.formatDateHuman(it.date.toDateTime()), //TODO: Core setup
                        it.amount.formatCurrencyExact()
                    )
                } ?: SingleExpenseUncategorizedDataHolder("", "", "")
            }
            is InsightData.WeeklyUncategorizedTransactionsData -> {
                (insight.viewDetails as? TransactionsViewDetails)?.let {
                    SingleExpenseUncategorizedDataHolder(
                        concatDescriptions(it.descriptions),
                        getDateRangeFromInstants(it.dates),
                        it.numberOfTransactions.toString()
                    )
                } ?: SingleExpenseUncategorizedDataHolder("", "", "")
            }
            else -> {
                throw IllegalStateException("InsightData for TransactionsSummaryViewProvider must be either UncategorizedTransactionData" +
                        " or WeeklyUncategorizedTransactionsData")
            }
        }
    }

    private fun concatDescriptions(descriptions: List<String>): String {
        return descriptions.toString()
            .drop(1).dropLast(1) // Remove '[' and ']'
    }

    private fun getDateRangeFromInstants(instants: List<Instant>): String {
        if (instants.isEmpty()) return ""
        val start = instants.first().toDateTime()
        val end = instants.last().toDateTime()
        return dateUtils.formatDateRange(start, end, "UTC", true)
    }
}

class SingleExpenseUncategorizedViewHolder(
    parent: ViewGroup, actionHandler: ActionHandler
) : InsightViewHolder(
    parent.inflate(R.layout.tink_item_insight_single_expense_uncategorized),
    actionHandler
), InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {

        require(data is SingleExpenseUncategorizedDataHolder)

        EspressoIdlingResource.increment()
        with(itemView) {
            transactionDescription.text = data.description
            date.text = data.date
            amount.text = data.amount
            icon.setImageResFromAttr(R.attr.tink_icon_category_uncategorized)
        }

        setupCommonBottomPart(insight)
    }
}

class SingleExpenseUncategorizedDataHolder(
    val description: String,
    val date: String,
    val amount: String
) : InsightDataHolder

