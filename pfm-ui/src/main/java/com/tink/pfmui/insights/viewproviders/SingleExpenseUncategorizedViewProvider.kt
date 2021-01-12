package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightType
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import com.tink.pfmui.insights.enrichment.TransactionViewDetails
import kotlinx.android.synthetic.main.tink_item_insight_single_expense_uncategorized.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.commons.extensions.toDateTime
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import se.tink.utils.DateUtils
import javax.inject.Inject

@ContributesInsightViewProvider
class SingleExpenseUncategorizedViewProvider @Inject constructor(
    val dateUtils: DateUtils,
    private val amountFormatter: AmountFormatter
) : InsightViewProvider {
    override val supportedInsightTypes: List<InsightType> =
        listOf(
          InsightType.SINGLE_EXPENSE_UNCATEGORIZED
        )

    override val viewType: InsightViewType = getViewType()

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        SingleExpenseUncategorizedViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {
        return (insight.viewDetails as? TransactionViewDetails)?.let {
            SingleExpenseUncategorizedDataHolder(
                it.description,
                dateUtils.formatDateHuman(it.date.toDateTime()), //TODO: Core setup
                amountFormatter.format(it.amount)
            )
        } ?: SingleExpenseUncategorizedDataHolder("", "", "")
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
