package com.tink.pfmui.overview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.transaction
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.OverviewFeature
import com.tink.pfmui.OverviewFeatures
import com.tink.pfmui.R
import com.tink.pfmui.insights.fragments.OverviewInsightsFragment
import com.tink.pfmui.overview.accounts.AccountsListFragment
import com.tink.pfmui.overview.latesttransactions.LatestTransactionsFragment
import com.tink.pfmui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.fragment_overview.*

internal class OverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_overview
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.OVERVIEW
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun viewReadyAfterLayout(): Boolean = false

    private val overviewFeatures: OverviewFeatures by lazy {
        requireNotNull(arguments?.getParcelable<OverviewFeatures>(ARG_FEATURES))
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
                    add(
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
                    id = R.id.overview_chart_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        context.resources.getDimensionPixelSize(R.dimen.overview_chart_container_height)
                    )
                }
                is OverviewFeature.Accounts -> {
                    id = R.id.accounts_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                is OverviewFeature.LatestTransactions -> {
                    id = R.id.latest_transactions_container
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                is OverviewFeature.ActionableInsights -> {
                    id = R.id.overview_insights_container
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
