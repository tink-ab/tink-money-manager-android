package com.tink.moneymanagerui.overview.charts

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.transaction.TransactionsListFragment
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import com.tink.moneymanagerui.util.FontUtils
import com.tink.moneymanagerui.view.CustomTypefaceSpan
import kotlinx.android.synthetic.main.tink_fragment_chart_details_pager.view.*
import se.tink.commons.extensions.onAttachedToWindow
import se.tink.commons.extensions.visible
import com.tink.model.time.Period
import com.tink.moneymanagerui.FragmentAnimationFlags

private const val PAGE_COUNT = 2
private const val PAGE_MONTH = 0
private const val PAGE_OVER_TIME = 1

private val PAGE_TITLES = listOf(R.string.tink_selector_monthly, R.string.tink_selector_over_time)
private const val TYPE_ARG = "type"

internal class ChartDetailsPagerFragment : BaseFragment(), CategorySelectionListener {
    private val type by lazy { arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES }
    private val adapter by lazy { ChartPagerAdapter(requireContext(), childFragmentManager, type) }
    private val viewModel by lazy {
        ViewModelProviders.of(rootFragment, viewModelFactory)[ChartDetailsViewModel::class.java].also {
            it.setType(type.type)
        }
    }

    override fun getLayoutId() = R.layout.tink_fragment_chart_details_pager
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = type.screenEvent
    override fun doNotRecreateView(): Boolean = false
    override fun hasToolbar(): Boolean = true
    override fun getTitle() = if (type.showCategoryPicker) "" else null
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        with(view) {
            // Causes pager population that fixes wrong clipping somehow
            pager.onAttachedToWindow {
                pager.adapter = adapter
                pager.offscreenPageLimit = PAGE_COUNT
                tabs.setupWithViewPager(pager)
            }
            category.visible = type.showCategoryPicker
        }
        if (type.showCategoryPicker) {
            viewModel.category.observe(
                viewLifecycle,
                Observer { view.category.text = viewModel.getCategoryTitle(resources, it, type) }
            )
        }
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.tink_details_options_menu)
        toolbar.menu?.getItem(0)?.let {  menuItem ->
            val spannableTitle = SpannableString(menuItem.title)
            spannableTitle.setSpan(
                CustomTypefaceSpan(FontUtils.getTypeface(R.font.tink_font_bold, context)),
                0,
                spannableTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            menuItem.title = spannableTitle
        }
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tink_action_transactions -> {
                showTransactions()
                true
            }
            R.id.tink_menu_close -> {
                fragmentCoordinator.popBackStack()
                true
            }
            else -> super.onToolbarMenuItemSelected(item)
        }
    }

    override fun onBackPressed(): Boolean {
        if (adapter.onBackPressed()) {
            return true
        }
        val parentId = viewModel.category.value?.parentId
        if (type.showCategoryPicker && parentId != null) {
            viewModel.setCategoryId(parentId)
            return true
        }
        adapter.prepareToExit(this)
        return false
    }

    override fun onChildViewReady(child: BaseFragment) {
        if (adapter.currentFragment === child) {
            onViewReady()
        }
    }

    fun showCategorySelector() {
        val fragment = CategorySelectionFragment.newInstance(
            type.type,
            viewModel.category.value?.code,
            CategorySelectionFragment.Options(dropdownToolbarAppearance = false)
        )
        fragment.setTargetFragment(this, 0)
        fragmentCoordinator.replace(
            fragment, true, FragmentAnimationFlags.SLIDE_UP
        )
    }

    override fun onCategorySelected(updatedCategoryId: String) = viewModel.setCategoryId(updatedCategoryId)

    private fun showTransactions() {
        val metaData = TransactionsListMetaData(
                isLeftToSpend = false,
                period = adapter.currentPagePeriod,
                categoryId = viewModel.category.value?.id,
                title = viewModel.category.value?.name ?: getString(R.string.tink_transactions_list_toolbar_title)
        )
        val fragment = TransactionsListFragment.newInstance(metaData)
        fragmentCoordinator.replace(fragment, true, FragmentAnimationFlags.SLIDE_UP)
    }

    companion object {
        @JvmStatic
        fun newInstance(type: ChartType) = ChartDetailsPagerFragment().apply {
            arguments = Bundle().apply { putSerializable(TYPE_ARG, type) }
        }
    }

    private inner class ChartPagerAdapter(private val context: Context, fm: FragmentManager, private val type: ChartType) : FragmentPagerAdapter(fm) {
        var currentFragment: BaseFragment? = null

        val currentPagePeriod: Period? get() = (currentFragment as? PeriodProvider)?.period

        override fun getItem(position: Int): Fragment {
            return when (position) {
                PAGE_MONTH -> type.create1MonthFragment()
                PAGE_OVER_TIME -> type.createOverTimeFragment()
                else -> throw IllegalArgumentException("Position is out of bound $position")
            }
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
            super.setPrimaryItem(container, position, obj)
            currentFragment = obj as? BaseFragment
        }

        fun onBackPressed(): Boolean = currentFragment?.onBackPressed() ?: false
        fun prepareToExit(fragment: BaseFragment) = (currentFragment as? TransitionAwareFragment)?.prepareToExit(fragment)

        override fun getCount() = PAGE_COUNT
        override fun getPageTitle(position: Int): String = context.getString(PAGE_TITLES[position])

    }
}

internal interface TransitionAwareFragment {
    fun prepareToExit(fragment: BaseFragment)
}

internal interface PeriodProvider {
    val period: Period?
}