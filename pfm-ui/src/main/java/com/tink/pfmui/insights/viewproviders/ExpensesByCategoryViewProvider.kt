package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightType
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import com.tink.pfmui.insights.enrichment.CategoryTreeViewDetails
import kotlinx.android.synthetic.main.item_insight_expenses_by_category.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.categories.iconFromCategoryCode
import se.tink.commons.currency.NewAmountFormatter
import se.tink.commons.extensions.abs
import se.tink.commons.extensions.findCategoryByCode
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class ExpensesByCategoryViewProvider @Inject constructor(
    private val amountFormatter: NewAmountFormatter
) : InsightViewProvider {
    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        ExpensesByCategoryViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {

        val categoryTree = (insight.viewDetails as CategoryTreeViewDetails).categories

        val sortedAmounts = insight.data
            .expenses()
            .sortedByDescending { it.amount.abs().value }
            .map {
                val categoryName = categoryTree.findCategoryByCode(it.categoryCode)?.name ?: ""
                ExpensesByCategory(
                    iconFromCategoryCode(it.categoryCode),
                    categoryName,
                    amountFormatter.format(it.amount.abs())
                )
            }

        val first = sortedAmounts.elementAtOrNull(0)
        val second = sortedAmounts.elementAtOrNull(1)
        val third = sortedAmounts.elementAtOrNull(2)

        return ExpensesByCategoryDataHolder(first, second, third)
    }

    override val viewType = getViewType()

    override val supportedInsightTypes = listOf(
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY
        //TODO: Missing insight type InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY
    )
}

private fun InsightData.expenses() =
    when (this) {
        is InsightData.WeeklyExpensesByCategoryData -> expenses
//  TODO: Missing insight type      is InsightData.MonthlySummaryExpensesByCategoryData -> expenses
        else -> emptyList() // This wouldn't happen and is prevented by the supportedInsightTypes property
    }

class ExpensesByCategoryViewHolder(
    parent: ViewGroup,
    actionHandler: ActionHandler
) : InsightViewHolder(parent.inflate(R.layout.item_insight_expenses_by_category), actionHandler),
    InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {
        require(data is ExpensesByCategoryDataHolder)

        setupCommonBottomPart(insight)

        view.apply {

            first.visibility =
                data.first?.let {
                    icon0.setImageResFromAttr(it.categoryIcon)
                    categoryName0.text = it.categoryName
                    amount0.text = it.amount
                    View.VISIBLE
                } ?: View.GONE


            second.visibility =
                data.second?.let {
                    icon1.setImageResFromAttr(it.categoryIcon)
                    categoryName1.text = it.categoryName
                    amount1.text = it.amount
                    View.VISIBLE
                } ?: View.GONE

            third.visibility =
                data.third?.let {
                    icon2.setImageResFromAttr(it.categoryIcon)
                    categoryName2.text = it.categoryName
                    amount2.text = it.amount
                    View.VISIBLE
                } ?: View.GONE
        }
    }
}

data class ExpensesByCategoryDataHolder(
    val first: ExpensesByCategory?,
    val second: ExpensesByCategory?,
    val third: ExpensesByCategory?
) : InsightDataHolder

data class ExpensesByCategory(
    val categoryIcon: Int,
    val categoryName: String,
    val amount: String
)