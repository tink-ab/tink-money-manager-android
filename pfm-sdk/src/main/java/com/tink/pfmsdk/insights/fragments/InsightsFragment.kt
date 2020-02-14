package com.tink.pfmsdk.insights.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.databinding.FragmentInsightsBinding
import com.tink.pfmsdk.insights.ArchivedInsightsViewModel
import com.tink.pfmsdk.insights.CurrentInsightsViewModel
import com.tink.pfmsdk.insights.InsightsAdapter
import com.tink.pfmsdk.insights.InsightsViewModel
import com.tink.pfmsdk.insights.di.InsightsViewModelFactory
import com.tink.pfmsdk.tracking.ScreenEvent
import kotlinx.android.synthetic.main.fragment_insights.*
import javax.inject.Inject

class InsightsFragment : BaseFragment() {

    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun getLayoutId(): Int = R.layout.fragment_insights
    override fun hasToolbar(): Boolean = true
    override fun getTitle(): String = getString(R.string.insights_tab_title)
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

        viewModel.insights.observe(viewLifecycleOwner, Observer {
            insightsAdapter.setData(it)
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { snackbarManager.displayError(it, context) }
        })

        recyclerView.apply {
//            layoutManager = object : LinearLayoutManager(context) {
//                override fun onLayoutCompleted(state: RecyclerView.State) {
//                    super.onLayoutCompleted(state)
//                    val canScrollUp = canScrollVertically(-1)
//                    if (!canScrollUp) showBottomNavBar()
//                }
//            }
            layoutManager = LinearLayoutManager(context)
            adapter = insightsAdapter
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }

        DataBindingUtil.bind<FragmentInsightsBinding>(inflatedView)?.also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        archiveViewModel.hasItems.observe(viewLifecycleOwner, Observer {
            showArchiveAction = it
            invalidateToolbarMenu()
            updateToolbar()
        })
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        super.onCreateToolbarMenu(toolbar)
        toolbar.navigationIcon = null
        toolbar.inflateMenu(R.menu.menu_insights)
        toolbar.menu.findItem(R.id.action_archive).isVisible = showArchiveAction
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_archive) {
            fragmentCoordinator.replace(ArchivedInsightsFragment.newInstance())
            return true
        }
        return super.onToolbarMenuItemSelected(item)
    }

//    private fun showBottomNavBar() =
//        activity?.findViewById<TinkBottomNavBar>(R.id.bottomNavigation)?.show()
}
