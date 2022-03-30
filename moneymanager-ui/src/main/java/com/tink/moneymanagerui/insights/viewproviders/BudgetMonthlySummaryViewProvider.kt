package com.tink.moneymanagerui.insights.viewproviders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightType
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.insights.actionhandling.ActionHandler
import com.tink.moneymanagerui.insights.enrichment.BudgetState
import com.tink.moneymanagerui.insights.enrichment.BudgetSummaryDetailItem
import com.tink.moneymanagerui.insights.enrichment.BudgetSummaryViewDetails
import com.tink.moneymanagerui.insights.extensions.getIcon
import com.tink.moneymanagerui.theme.resolveColorForFeature
import kotlinx.android.synthetic.main.tink_item_insight_budget_monthly_summary.view.*
import se.tink.android.annotations.ContributesInsightViewProvider
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.inflate
import se.tink.commons.extensions.setIconRes
import se.tink.commons.extensions.tint
import se.tink.commons.icons.IconResource
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
        val icon: IconResource,
        val iconColor: BudgetColor
    )

    sealed class BudgetColor(@AttrRes val color: Int, @AttrRes val lightColor: Int) {
        object OverBudget : BudgetColor(R.attr.tink_warningColor, R.attr.tink_warningLightColor)
        object WithinBudget : BudgetColor(R.attr.tink_expensesIconColor, R.attr.tink_expensesIconBackgroundColor)
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
        parent.inflate(R.layout.tink_item_insight_budget_monthly_summary),
        actionHandler
    ),
        InsightCommonBottomPart {
        override val view: View = itemView

        override fun bind(data: InsightDataHolder, insight: Insight) {
            require(data is BudgetMonthlySummaryDataHolder)

            setupCommonBottomPart(insight)
            setUpProgress(data)
            setupCategories(data.categories)
        }

        private fun setupCategories(categories: List<BudgetMonthlySummaryCategory>) {
            view.categoriesContainer.visibleIf { categories.isNotEmpty() }

            setUpCategory(
                categories.getOrNull(0),
                view.categoryOneIcon,
                view.categoryOneBackground
            )

            setUpCategory(
                categories.getOrNull(1),
                view.categoryTwoIcon,
                view.categoryTwoBackground
            )

            setUpCategory(
                categories.getOrNull(2),
                view.categoryThreeIcon,
                view.categoryThreeBackground,
                view.categoryThreeGroup
            )

            val fourOrMoreCategory = getFourOrMoreCategory(categories)
            setUpCategory(
                fourOrMoreCategory,
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
            group?.visibleIf { category != null }
            icon.visibleIf { category != null }
            background.visibleIf { category != null }
            if (category != null) {
                icon.setIconRes(category.icon)
                icon.tint(category.iconColor.color)
                background.backgroundTint(category.iconColor.lightColor)
            }
        }

        private fun getFourOrMoreCategory(categories: List<BudgetMonthlySummaryCategory>): BudgetMonthlySummaryCategory? {
            if (categories.size <= 4) {
                return categories.getOrNull(3)
            }

            val hasOverBudgetCategory = categories
                .drop(3)
                .any { category -> category.iconColor != BudgetColor.WithinBudget }

            val moreIconColor = if (hasOverBudgetCategory) {
                BudgetColor.OverBudget
            } else {
                BudgetColor.WithinBudget
            }

            return BudgetMonthlySummaryCategory(
                icon = IconResource.DrawableId(R.drawable.tink_more_horizontal),
                iconColor = moreIconColor
            )
        }

        private fun setUpProgress(data: BudgetMonthlySummaryDataHolder) {
            @ColorInt val resolvedColor = view.context.resolveColorForFeature(data.progressChartColor.color, MoneyManagerFeatureType.ACTIONABLE_INSIGHTS)

            with(view.progress) {
                progress = data.progress
                setProgressArcColor(data.progressChartColor.color, MoneyManagerFeatureType.ACTIONABLE_INSIGHTS)
                setBackgroundRingColor(data.progressChartColor.lightColor, MoneyManagerFeatureType.ACTIONABLE_INSIGHTS)
            }

            with(view.currentSpending) {
                text = data.spentAmountText
                setTextColor(resolvedColor)
            }

            view.totalBudget.text = data.totalBudgetText
            view.totalBudget.setTextColor(resolvedColor)
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
