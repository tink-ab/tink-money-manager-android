package com.tink.pfmui.insights.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.databinding.TinkFragmentInsightsBinding
import com.tink.pfmui.insights.ArchivedInsightsViewModel
import com.tink.pfmui.insights.InsightsAdapter
import com.tink.pfmui.insights.di.InsightsViewModelFactory
import com.tink.pfmui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_insights.*
import javax.inject.Inject

class ArchivedInsightsFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getLayoutId(): Int = R.layout.tink_fragment_insights
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String = getString(R.string.tink_insights_archive_title)
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

        DataBindingUtil.bind<TinkFragmentInsightsBinding>(view)?.also {
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
