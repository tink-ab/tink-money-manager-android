package com.tink.pfmsdk.insights.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.databinding.FragmentOverviewInsightsBinding
import com.tink.pfmsdk.insights.CurrentInsightsViewModel
import com.tink.pfmsdk.insights.InsightsViewModel
import com.tink.pfmsdk.insights.di.InsightsViewModelFactory
import kotlinx.android.synthetic.main.fragment_overview_insights.view.*
import javax.inject.Inject

internal class OverviewInsightsFragment : BaseFragment() {

    @Inject
    lateinit var localViewModelFactory: InsightsViewModelFactory

    lateinit var viewModel: InsightsViewModel

    override fun getLayoutId() = R.layout.fragment_overview_insights
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
        DataBindingUtil.bind<FragmentOverviewInsightsBinding>(inflatedView)?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        viewModel.insights.observe(viewLifecycleOwner, Observer { insightsList ->
            view.insightsCount.text = insightsList.size.toString()
        })
        view.insightsCardButton.setOnClickListener { fragmentCoordinator.replace(InsightsFragment()) }
    }
}