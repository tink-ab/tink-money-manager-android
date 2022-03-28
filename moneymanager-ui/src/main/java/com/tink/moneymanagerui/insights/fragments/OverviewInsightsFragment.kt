package com.tink.moneymanagerui.insights.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.insights.CurrentInsightsViewModel
import com.tink.moneymanagerui.insights.InsightsViewModel
import com.tink.moneymanagerui.insights.di.InsightsViewModelFactory
import com.tink.moneymanagerui.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.tink_fragment_overview_insights.view.*
import se.tink.commons.extensions.getColorFromAttr
import javax.inject.Inject

internal class OverviewInsightsFragment : BaseFragment() {

    @Inject
    lateinit var localViewModelFactory: InsightsViewModelFactory

    lateinit var viewModel: InsightsViewModel

    override fun getLayoutId() = R.layout.tink_fragment_overview_insights
    override fun needsLoginToBeAuthorized() = true
    override fun doNotRecreateView(): Boolean = false
    override fun viewReadyAfterLayout(): Boolean = false
    override fun hasToolbar(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, localViewModelFactory)[CurrentInsightsViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        viewModel.refresh()
        viewModel.insights.observe(
            viewLifecycleOwner,
            Observer { insightsList ->
                view.insightsCount.text = insightsList.size.toString()
            }
        )

        viewModel.loading.observe(
            viewLifecycleOwner,
            Observer { isLoading ->
                view.insightsProgressBar.visibleIf { isLoading }
            }
        )

        EspressoIdlingResource.increment()
        viewModel.hasItems.observe(
            viewLifecycleOwner,
            Observer { hasItems ->
                view.apply {
                    insightsCountBackground.visibleIf { hasItems }
                    insightsCount.visibleIf { hasItems }
                    spacer.visibleIf { hasItems }
                    insightsCardTitle.text = if (hasItems) {
                        getString(R.string.tink_insights_overview_card_new_events_title)
                    } else {
                        getString(R.string.tink_insights_overview_card_archived_events_title)
                    }
                    if (hasItems) {
                        // new events UI
                        insightsCard.setCardBackgroundColor(ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_colorAccent)))
                        insightsCard.setOnClickListener { fragmentCoordinator.replace(InsightsFragment.newInstance()) }
                        insightsCardTitle.setTextColor(context.getColorFromAttr(R.attr.tink_colorOnAccent))
                        insightsCardButton.setTextColor(context.getColorFromAttr(R.attr.tink_colorOnAccent))
                        insightsCardButton.setOnClickListener { fragmentCoordinator.replace(InsightsFragment.newInstance()) }
                    } else {
                        // archived events UI
                        insightsCard.setCardBackgroundColor(ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_cardBackgroundColor)))
                        insightsCard.setOnClickListener {
                            fragmentCoordinator.replace(ArchivedInsightsFragment.newInstance())
                        }
                        insightsCardTitle.setTextColor(context.getColorFromAttr(R.attr.tink_colorPrimary))
                        insightsCardButton.setTextColor(context.getColorFromAttr(R.attr.tink_colorPrimary))
                        insightsCardButton.setOnClickListener {
                            fragmentCoordinator.replace(ArchivedInsightsFragment.newInstance())
                        }
                    }
                    insightsCard.visibility = View.VISIBLE
                    EspressoIdlingResource.decrement()
                }
            }
        )
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.ACTIONABLE_INSIGHTS
}
