package com.tink.pfmsdk.overview.charts.piechart


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.FragmentAnimationFlags
import com.tink.pfmsdk.FragmentCoordinator
import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ScreenEvent
import kotlinx.android.synthetic.main.fragment_pie_chart.view.*
import com.tink.pfmsdk.overview.charts.PeriodProvider
import com.tink.pfmsdk.overview.charts.ChartDetailsViewModel
import com.tink.pfmsdk.overview.charts.ChartType
import com.tink.pfmsdk.overview.charts.PieChartDetailsViewModel
import com.tink.pfmsdk.theme.getTabPieChartThemeForType
import se.tink.core.models.Category
import se.tink.core.models.misc.Period
import javax.inject.Inject

private const val TYPE_ARG = "type"

class TabPieChartFragment : BaseFragment(), PeriodProvider {
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[PieChartDetailsViewModel::class.java] }
    private val pageViewModel by lazy { ViewModelProviders.of(rootFragment, viewModelFactory)[ChartDetailsViewModel::class.java] }
    private val type by lazy { arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES }
    private val ownTheme by lazy { getTabPieChartThemeForType(context!!, type) }

    @Inject
    lateinit var navigation: PieChartNavigation

    override val period: Period? get() = viewModel.selectedPeriod.value

    override fun getLayoutId() = R.layout.fragment_pie_chart
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRACKING_ERROR
    override fun doNotRecreateView() = false
    override fun shouldTrackScreen() = false
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel.apply {
            periods.observe(viewLifecycle, Observer { it?.let { view.periodPicker.items = it } })
            selectedPeriod.observe(viewLifecycle, Observer { it?.let { view.periodPicker.currentItem = it } })
            category.observe(viewLifecycle, Observer { it?.let { navigation.onCategoryChanged(view, it, type) } })
        }
        pageViewModel.category.observe(viewLifecycle, Observer { it?.let { viewModel.setCategory(it) } })

        with(view.periodPicker) {
            onItemSelected = viewModel::setPeriod
            formatter = viewModel.periodFormatter
        }
    }

    override fun onChildViewReady(child: BaseFragment) = onViewReady()

    companion object {
        fun newInstance(type: ChartType) = TabPieChartFragment().apply {
            arguments = Bundle().apply { putSerializable(TYPE_ARG, type) }
        }
    }
}

private const val FULL_CHART_TAG = "FragmentFullPieChart"
private const val HALF_CHART_TAG = "FragmentHalfPieChart"

class PieChartNavigation(private val coordinator: FragmentCoordinator) {

    fun onCategoryChanged(root: View, category: Category, type: ChartType) {
        val tag: String
        val createFunc: () -> BaseFragment
        if (category.parent == null) {
            tag = FULL_CHART_TAG
            createFunc = { FullPieChartFragment.newInstance(type) }
        } else {
            tag = HALF_CHART_TAG
            createFunc = { HalfPieChartFragment.newInstance(type) }
        }
        if (coordinator.findByTag(tag) == null) {
            val sharedElements = listOf(R.id.back_segment, R.id.pieChart, R.id.amount)
                .mapNotNull { root.findViewById<View>(it) }
                .filter { it.transitionName != null }

            coordinator.replace(
                createFunc(), false, FragmentAnimationFlags.NONE,
                tag = tag,
                sharedViews = sharedElements
            )
        }
    }
}




