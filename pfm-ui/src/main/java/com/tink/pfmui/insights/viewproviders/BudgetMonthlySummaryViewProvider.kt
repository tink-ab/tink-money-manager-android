package com.tink.pfmui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import com.tink.pfmui.R
import com.tink.pfmui.insights.actionhandling.ActionHandler
import com.tink.pfmui.insights.enrichment.BudgetState
import com.tink.pfmui.insights.enrichment.BudgetSummaryDetailItem
import com.tink.pfmui.insights.enrichment.BudgetSummaryViewDetails
import com.tink.pfmui.insights.extensions.getIcon
import kotlinx.android.synthetic.main.item_insight_budget_monthly_summary.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.tint
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType
import se.tink.insights.getViewType
import javax.inject.Inject

@ContributesInsightViewProvider
class BudgetMonthlySummaryViewProvider @Inject constructor() : InsightViewProvider {

    data class BudgetMonthlySummaryDataHolder(
        val categories: List<BudgetMonthlySummaryCategory>,
        val progress: Double,
        val progressChartColor: BudgetColor,
        val spentAmountText: String,
        val totalBudgetText: String
    ) : InsightDataHolder

    data class BudgetMonthlySummaryCategory(
        @DrawableRes val icon: Int,
        val iconColor: BudgetColor
    )

    sealed class BudgetColor(@AttrRes val color: Int, @AttrRes val lightColor: Int) {
        object OverBudget : BudgetColor(R.attr.tink_warningColor, R.attr.tink_warningLightColor)
        object WithinBudget : BudgetColor(R.attr.tink_expensesColor, R.attr.tink_expensesLightColor)
    }

    override val viewType: InsightViewType = getViewType()
    override val supportedInsightTypes: List<InsightType> =
        listOf(
            InsightType.BUDGET_SUMMARY_OVERSPENT,
            InsightType.BUDGET_SUMMARY_ACHIEVED
        )

    override fun viewHolder(parent: ViewGroup, actionHandler: ActionHandler): InsightViewHolder =
        BudgetMonthlySummaryViewHolder(parent, actionHandler)

    override fun getDataHolder(insight: Insight): InsightDataHolder {

        val details = insight.viewDetails as? BudgetSummaryViewDetails

        val categories = details?.items?.map { it.toBudgetMonthlySummaryCategory() } ?: emptyList()

        val progress = details?.progress ?: 0.0

        return BudgetMonthlySummaryDataHolder(
            categories = categories,
            progress = progress,
            progressChartColor = if (progress >= 1.0) BudgetColor.OverBudget else BudgetColor.WithinBudget,
            spentAmountText = details?.spentAmount ?: "",
            totalBudgetText = "/ ${details?.targetAmount ?: ""}"
        )
    }

    class BudgetMonthlySummaryViewHolder(
        parent: ViewGroup,
        actionHandler: ActionHandler
    ) : InsightViewHolder(
        parent.inflate(R.layout.item_insight_budget_monthly_summary),
        actionHandler
    ), InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is BudgetMonthlySummaryDataHolder)

            setupCommonBottomPart(insight)
            setUpProgress(data)

            setUpCategory(
                data.categories.getOrNull(0),
                view.categoryOneIcon,
                view.categoryOneBackground
            )

            setUpCategory(
                data.categories.getOrNull(1),
                view.categoryTwoIcon,
                view.categoryTwoBackground
            )

            setUpCategory(
                data.categories.getOrNull(2),
                view.categoryThreeIcon,
                view.categoryThreeBackground,
                view.categoryThreeGroup
            )

            setUpCategory(
                data.categories.getOrNull(3),
                view.categoryFourIcon,
                view.categoryFourBackground,
                view.categoryFourGroup
            )
        }

        private fun setUpCategory(
            category: BudgetMonthlySummaryCategory?,
            icon: ImageView,
            background: ImageView,
            group: View? = null
        ) {
            if (category == null) {
                group?.visibility = View.GONE
            } else {
                group?.visibility = View.VISIBLE

                icon.setImageResource(category.icon)
                icon.tint(category.iconColor.color)
                background.backgroundTint(category.iconColor.lightColor)
            }
        }

        private fun setUpProgress(data: BudgetMonthlySummaryDataHolder) {
            val resolvedColor = view.context.getColorFromAttr(data.progressChartColor.color)
            val resolvedLightColor = view.context.getColorFromAttr(data.progressChartColor.lightColor)

            with(view.progress) {
                progress = data.progress
                setProgressArcColor(resolvedColor)
                setBackgroundRingColor(resolvedLightColor)
            }

            with(view.currentSpending) {
                text = data.spentAmountText
                setTextColor(resolvedColor)
            }

            view.totalBudget.text = data.totalBudgetText
            view.topContainerBackground.setBackgroundColor(resolvedColor)
        }
    }

    private fun BudgetSummaryDetailItem.toBudgetMonthlySummaryCategory(): BudgetMonthlySummaryCategory {
        val icon = iconTypeViewDetails.getIcon()
        val color =
            if (budgetState == BudgetState.OVERSPENT) {
                BudgetColor.OverBudget
            } else {
                BudgetColor.WithinBudget
            }
        return BudgetMonthlySummaryCategory(icon, color)
    }
}
