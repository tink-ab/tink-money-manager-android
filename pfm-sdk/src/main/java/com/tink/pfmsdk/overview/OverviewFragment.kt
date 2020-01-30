package com.tink.pfmsdk.overview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.transaction
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.OverviewFeature
import com.tink.pfmsdk.OverviewFeatures
import com.tink.pfmsdk.R
import com.tink.pfmsdk.overview.accounts.AccountsListFragment
import com.tink.pfmsdk.overview.latesttransactions.LatestTransactionsFragment
import com.tink.pfmsdk.tracking.ScreenEvent
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : BaseFragment() {
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
                fragmentManager?.transaction {
                    add(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        OverviewChartFragment.newInstance(feature)
                    )
                }
            }

            is OverviewFeature.Accounts -> {
                fragmentManager?.transaction {
                    add(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        AccountsListFragment()
                    )
                }
            }

            is OverviewFeature.LatestTransactions -> {
                fragmentManager?.transaction {
                    add(
                        containerIdForFeature(feature, overviewContainer, requireContext()),
                        LatestTransactionsFragment()
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
