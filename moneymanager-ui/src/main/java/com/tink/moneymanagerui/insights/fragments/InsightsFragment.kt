package com.tink.moneymanagerui.insights.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.insights.ArchivedInsightsViewModel
import com.tink.moneymanagerui.insights.CurrentInsightsViewModel
import com.tink.moneymanagerui.insights.InsightsAdapter
import com.tink.moneymanagerui.insights.InsightsViewModel
import com.tink.moneymanagerui.insights.di.InsightsViewModelFactory
import com.tink.moneymanagerui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_insights.*
import javax.inject.Inject

class InsightsFragment : BaseFragment() {

    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getLayoutId(): Int = R.layout.tink_fragment_insights
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String = getString(R.string.tink_insights_tab_title)
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.EVENTS

    @Inject
    lateinit var insightsAdapter: InsightsAdapter

    @Inject
    lateinit var localViewModelFactory: InsightsViewModelFactory

    lateinit var viewModel: InsightsViewModel

    lateinit var archiveViewModel: ArchivedInsightsViewModel

    private var showArchiveAction = false

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, localViewModelFactory)[CurrentInsightsViewModel::class.java]

        archiveViewModel = ViewModelProviders.of(
            this,
            localViewModelFactory
        )[ArchivedInsightsViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.refresh()

        viewModel.insights.observe(viewLifecycleOwner, Observer {
            insightsAdapter.setData(it)
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { snackbarManager.displayError(it, context) }
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = insightsAdapter
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }

        archiveViewModel.hasItems.observe(viewLifecycleOwner, Observer {
            showArchiveAction = it
            invalidateToolbarMenu()
            updateToolbar()
        })

        viewModel.hasItems.observe(viewLifecycle, { hasInsights ->
            recyclerView.visibleIf { hasInsights }
        })
        viewModel.loading.observe(viewLifecycle, { isLoading ->
            insightsProgressBar.visibleIf { isLoading }
        })
        viewModel.showEmptyState.observe(viewLifecycle, { shouldShowEmptyState ->
            emptyStateText.visibleIf { shouldShowEmptyState }
        })

    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        super.onCreateToolbarMenu(toolbar)
        toolbar.inflateMenu(R.menu.tink_menu_insights)
        toolbar.menu.findItem(R.id.action_archive).isVisible = showArchiveAction
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_archive) {
            fragmentCoordinator.replace(ArchivedInsightsFragment.newInstance())
            return true
        }
        return super.onToolbarMenuItemSelected(item)
    }

    companion object {
        fun newInstance() = InsightsFragment()
    }
}
