package com.tink.pfmsdk.insights.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.databinding.FragmentInsightsBinding
import com.tink.pfmsdk.insights.ArchivedInsightsViewModel
import com.tink.pfmsdk.insights.InsightsAdapter
import com.tink.pfmsdk.insights.di.InsightsViewModelFactory
import com.tink.pfmsdk.tracking.ScreenEvent
import kotlinx.android.synthetic.main.fragment_insights.*
import javax.inject.Inject

class ArchivedInsightsFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getLayoutId(): Int = R.layout.fragment_insights
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String = getString(R.string.insights_archive_title)
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.EVENTS_ARCHIVE

    @Inject
    lateinit var insightsAdapter: InsightsAdapter

    @Inject
    lateinit var localViewModelFactory: InsightsViewModelFactory

    lateinit var viewModel: ArchivedInsightsViewModel

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            localViewModelFactory
        )[ArchivedInsightsViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.insights.observe(viewLifecycleOwner, Observer {
            insightsAdapter.setData(it)
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer {event ->
            event?.getContentIfNotHandled()?.let { snackbarManager.displayError(it, context) }
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = insightsAdapter
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }

        DataBindingUtil.bind<FragmentInsightsBinding>(inflatedView)?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun authorizedOnStart() {
        super.authorizedOnStart()
        viewModel.refresh()
    }

    companion object {
        fun newInstance() = ArchivedInsightsFragment()
    }
}
