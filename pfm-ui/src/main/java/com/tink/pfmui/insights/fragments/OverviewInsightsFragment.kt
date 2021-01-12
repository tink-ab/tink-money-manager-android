package com.tink.pfmui.insights.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.databinding.TinkFragmentOverviewInsightsBinding
import com.tink.pfmui.insights.CurrentInsightsViewModel
import com.tink.pfmui.insights.InsightsViewModel
import com.tink.pfmui.insights.di.InsightsViewModelFactory
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
    override fun viewReadyAfterLayout(): Boolean  = false
    override fun hasToolbar(): Boolean = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, localViewModelFactory)[CurrentInsightsViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        DataBindingUtil.bind<TinkFragmentOverviewInsightsBinding>(view)?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.refresh()
        viewModel.insights.observe(viewLifecycleOwner, Observer { insightsList ->
            view.insightsCount.text = insightsList.size.toString()
        })
        viewModel.hasItems.observe(viewLifecycleOwner, Observer { hasItems ->
            view.apply {
                if (hasItems) {
                    // new events UI
                    insightsCard.setCardBackgroundColor(ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_colorAccent)))
                    insightsCardTitle.setTextColor(context.getColorFromAttr(R.attr.tink_colorOnAccent))
                    insightsCardButton.setTextColor(context.getColorFromAttr(R.attr.tink_colorOnAccent))
                    insightsCardButton.setOnClickListener { fragmentCoordinator.replace(InsightsFragment.newInstance()) }
                } else {
                    // archived events UI
                    insightsCard.setCardBackgroundColor(ColorStateList.valueOf(context.getColorFromAttr(R.attr.tink_cardBackgroundColor)))
                    insightsCardTitle.setTextColor(context.getColorFromAttr(R.attr.tink_colorPrimary))
                    insightsCardButton.setTextColor(context.getColorFromAttr(R.attr.tink_colorPrimary))
                    insightsCardButton.setOnClickListener { fragmentCoordinator.replace(ArchivedInsightsFragment.newInstance()) }
                }
                insightsCard.visibility = View.VISIBLE
            }
        })
    }
}