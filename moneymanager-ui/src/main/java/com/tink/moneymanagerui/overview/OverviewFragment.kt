package com.tink.moneymanagerui.overview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.transaction
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.insights.fragments.OverviewInsightsFragment
import com.tink.moneymanagerui.overview.accounts.AccountsListFragment
import com.tink.moneymanagerui.overview.budgets.BudgetsOverviewFragment
import com.tink.moneymanagerui.overview.latesttransactions.LatestTransactionsFragment
import com.tink.moneymanagerui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_overview.*

internal class OverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.tink_fragment_overview
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.OVERVIEW
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun viewReadyAfterLayout(): Boolean = false

    private val overviewFeatures: OverviewFeatures by lazy {
        requireNotNull(arguments?.getParcelable(ARG_FEATURES))
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        for (feature in overviewFeatures.features) {
            addFeature(feature)
        }
    }

    private fun addFeature(feature: OverviewFeature) {
        when (feature) {
            is OverviewFeature.Statistics -> {
                childFragmentManager.transaction {
                    replace(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        OverviewChartFragment.newInstance(feature)
                    )
                }
            }

            is OverviewFeature.Accounts -> {
                childFragmentManager.transaction {
                    replace(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        AccountsListFragment()
                    )
                }
            }

            is OverviewFeature.LatestTransactions -> {
                childFragmentManager.transaction {
                    replace(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        LatestTransactionsFragment()
                    )
                }
            }

            is OverviewFeature.ActionableInsights -> {
                childFragmentManager.transaction {
                    replace(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        OverviewInsightsFragment()
                    )
                }
            }

            is OverviewFeature.CustomContainerView -> {
                containerIdForFeature(feature, overviewContainer, requireContext())
            }

            OverviewFeature.Budgets -> {
                childFragmentManager.transaction {
                    replace(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        BudgetsOverviewFragment()
                    )
                }
            }
        }
    }

    private fun containerIdForFeature(
        feature: OverviewFeature,
        parent: LinearLayout,
        context: Context
    ): Int {
        val containerView = FrameLayout(context)
        containerView.apply {
            when (feature) {
                is OverviewFeature.Statistics -> {
                    id = R.id.tink_overview_chart_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        context.resources.getDimensionPixelSize(R.dimen.tink_tink_overview_chart_container_height)
                    )
                }
                is OverviewFeature.Accounts -> {
                    id = R.id.tink_accounts_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                is OverviewFeature.LatestTransactions -> {
                    id = R.id.tink_latest_transactions_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                is OverviewFeature.ActionableInsights -> {
                    id = R.id.tink_overview_insights_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                is OverviewFeature.CustomContainerView -> {
                    id = feature.containerViewId
                    layoutParams = FrameLayout.LayoutParams(feature.width, feature.height)
                }
                OverviewFeature.Budgets -> {
                    id = R.id.tink_budgets_overview_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }
        parent.addView(containerView)
        return containerView.id
    }

    companion object {
        private const val ARG_FEATURES = "ARG_FEATURES"

        @JvmStatic
        fun newInstance(features: OverviewFeatures): OverviewFragment =
            OverviewFragment().apply {
                arguments = bundleOf(
                    ARG_FEATURES to features
                )
            }
    }

}