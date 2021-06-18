package com.tink.moneymanagerui.insights.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.databinding.TinkFragmentInsightsBinding
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.insights.ArchivedInsightsViewModel
import com.tink.moneymanagerui.insights.InsightsAdapter
import com.tink.moneymanagerui.insights.di.InsightsViewModelFactory
import com.tink.moneymanagerui.tracking.ScreenEvent
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

        viewModel.refresh()

        viewModel.insights.observe(viewLifecycleOwner, Observer { insightsList ->
            insightsAdapter.setData(insightsList)
            emptyState.visibleIf { insightsList?.isNullOrEmpty() == true }
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
