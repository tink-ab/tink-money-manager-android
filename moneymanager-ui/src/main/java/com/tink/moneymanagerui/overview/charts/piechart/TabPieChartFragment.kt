package com.tink.moneymanagerui.overview.charts.piechart


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_pie_chart.view.*
import com.tink.moneymanagerui.overview.charts.PeriodProvider
import com.tink.moneymanagerui.overview.charts.ChartDetailsViewModel
import com.tink.moneymanagerui.overview.charts.ChartType
import com.tink.moneymanagerui.overview.charts.PieChartDetailsViewModel
import com.tink.model.category.Category
import com.tink.model.time.Period
import javax.inject.Inject

private const val TYPE_ARG = "type"

internal class TabPieChartFragment : BaseFragment(), PeriodProvider {
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[PieChartDetailsViewModel::class.java] }
    private val pageViewModel by lazy { ViewModelProviders.of(rootFragment, viewModelFactory)[ChartDetailsViewModel::class.java] }
    private val type by lazy { arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES }

    @Inject
    lateinit var navigation: PieChartNavigation

    override val period: Period? get() = viewModel.selectedPeriod.value

    override fun getLayoutId() = R.layout.tink_fragment_pie_chart
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

internal class PieChartNavigation(private val coordinator: FragmentCoordinator) {

    fun onCategoryChanged(root: View, category: Category, type: ChartType) {
        val tag: String
        val createFunc: () -> BaseFragment
        if (category.parentId == null) {
            tag = FULL_CHART_TAG
            createFunc = { FullPieChartFragment.newInstance(type) }
        } else {
            tag = HALF_CHART_TAG
            createFunc = { HalfPieChartFragment.newInstance(type) }
        }
        if (coordinator.findByTag(tag) == null) {
            val sharedElements = listOf(R.id.tink_back_segment, R.id.pieChart, R.id.amount)
                .mapNotNull { root.findViewById<View>(it) }
                .filter { it.transitionName != null }

            coordinator.replace(
                createFunc(), false, FragmentAnimationFlags.NONE,
                tag = tag,
                // TODO: Transition doesn't work in Fragment 1.3.4, uncomment and use when it's fixed, see https://issuetracker.google.com/issues/188457866
//                sharedViews = sharedElements
            )
        }
    }
}



