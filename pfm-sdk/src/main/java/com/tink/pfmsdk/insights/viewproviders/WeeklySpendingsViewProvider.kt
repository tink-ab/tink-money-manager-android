package com.tink.pfmsdk.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import com.tink.pfmsdk.R
import com.tink.pfmsdk.insights.actionhandling.ActionHandler
import com.tink.pfmsdk.insights.enrichment.CategoryTreeViewDetails
import kotlinx.android.synthetic.main.item_insight_weekly_spendings.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.categories.iconFromCategoryCode
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.inflate
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightData
import se.tink.core.models.insights.InsightType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class WeeklySpendingsViewProvider @Inject constructor(
    private val amountFormatter: AmountFormatter
) : InsightViewProvider {
    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        WeeklySpendingsInsightViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {

        val categoryTree = (insight.viewDetails as CategoryTreeViewDetails).categories

        val sortedAmounts = (insight.data as InsightData.WeeklyExpensesByCategoryData)
            .expenses
            .sortedByDescending { it.amount.abs().value }
            .map {
                val categoryName = categoryTree.findCategoryByCode(it.categoryCode)?.name ?: ""
                WeeklySpending(
                    iconFromCategoryCode(it.categoryCode),
                    categoryName,
                    amountFormatter.format(it.amount.abs())
                )
            }

        val first = sortedAmounts.elementAtOrNull(0)
        val second = sortedAmounts.elementAtOrNull(1)
        val third = sortedAmounts.elementAtOrNull(2)

        return WeeklySpendingsDataHolder(first, second, third)
    }

    override val viewType = getViewType()

    override val supportedInsightTypes = listOf(InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY)
}

class WeeklySpendingsInsightViewHolder(
    parent: ViewGroup,
    actionHandler: ActionHandler
) : InsightViewHolder(parent.inflate(R.layout.item_insight_weekly_spendings), actionHandler),
    InsightCommonBottomPart {
    override val view: View = itemView

    override fun bind(data: InsightDataHolder, insight: Insight) {
        require(data is WeeklySpendingsDataHolder)

        setupCommonBottomPart(insight)

        view.apply {

            first.visibility =
                data.first?.let {
                    icon0.setImageResource(it.categoryIcon)
                    categoryName0.text = it.categoryName
                    amount0.text = it.amount
                    View.VISIBLE
                } ?: View.GONE


            second.visibility =
                data.second?.let {
                    icon1.setImageResource(it.categoryIcon)
                    categoryName1.text = it.categoryName
                    amount1.text = it.amount
                    View.VISIBLE
                } ?: View.GONE

            third.visibility =
                data.third?.let {
                    icon2.setImageResource(it.categoryIcon)
                    categoryName2.text = it.categoryName
                    amount2.text = it.amount
                    View.VISIBLE
                } ?: View.GONE
        }
    }
}

data class WeeklySpendingsDataHolder(
    val first: WeeklySpending?,
    val second: WeeklySpending?,
    val third: WeeklySpending?
) : InsightDataHolder

data class WeeklySpending(
    val categoryIcon: Int,
    val categoryName: String,
    val amount: String
)