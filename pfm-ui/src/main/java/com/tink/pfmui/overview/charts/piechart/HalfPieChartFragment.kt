package com.tink.pfmui.overview.charts.piechart

import com.tink.pfmui.R
import com.tink.pfmui.tracking.ScreenEvent
import android.content.Context
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.FragmentAnimationFlags
import com.tink.pfmui.databinding.FragmentHalfPieChartBinding
import kotlinx.android.synthetic.main.fragment_half_pie_chart.view.*
import com.tink.pfmui.charts.transitions.ChangePositionTransition
import com.tink.pfmui.charts.transitions.PieChartLabelTransition
import com.tink.pfmui.charts.transitions.PieChartSegmentTransition
import com.tink.pfmui.charts.transitions.TextAmountTransition
import com.tink.pfmui.overview.charts.ChartDetailsViewModel
import com.tink.pfmui.overview.charts.ChartItem
import com.tink.pfmui.overview.charts.ChartType
import com.tink.pfmui.overview.charts.DetailsChartModel
import com.tink.pfmui.overview.charts.PieChartDetailsViewModel
import com.tink.pfmui.overview.charts.StatisticItem
import com.tink.pfmui.overview.charts.TransactionsItem
import com.tink.pfmui.theme.getTabPieChartThemeForType
import com.tink.pfmui.transaction.TransactionsListFragment
import com.tink.pfmui.transaction.TransactionsListMetaData
import com.tink.pfmui.util.CurrencyUtils
import se.tink.commons.extensions.getColorFromAttr

private const val TYPE_ARG = "type"

internal class HalfPieChartFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(
            parentFragment!!,
            viewModelFactory
        )[PieChartDetailsViewModel::class.java]
    }
    private val chartViewModel by lazy {
        ViewModelProviders.of(
            rootFragment,
            viewModelFactory
        )[ChartDetailsViewModel::class.java]
    }

    private val type by lazy {
        arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES
    }
    private val ownTheme by lazy { getTabPieChartThemeForType(context!!, type) }

    override fun getLayoutId() = R.layout.fragment_half_pie_chart
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRACKING_ERROR
    override fun doNotRecreateView() = false
    override fun shouldTrackScreen() = false
    override fun getTheme(): Theme = ownTheme
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        val binding = DataBindingUtil.bind<FragmentHalfPieChartBinding>(view.root)
            ?: throw IllegalStateException("Binding is null")

        viewModel.apply {
            getStatistic(context!!, type).observe(
                viewLifecycle,
                Observer { it?.let { updateModel(binding, it) } })
        }
    }

    private fun updateModel(binding: FragmentHalfPieChartBinding, model: DetailsChartModel) {
        if (model.topLevel) return

        if (!transitionCoordinator.hasTransitionInProgress()) {
            TransitionManager.beginDelayedTransition(view as ViewGroup, changeTransition())
        }

        val startAngle = view.chartLayout.visibleAngle
        val fullSweep = 180 - 2 * startAngle
        view.pieChart.apply {
            removeAllViews()
            addBackSegment(model.title, model.color)
            addSegments(
                model.data.items,
                { it.amount },
                model.colorGenerator,
                model.color,
                startFrom = startAngle,
                fullSweep = fullSweep,
                onClick = ::onItemClicked
            )
        }

        binding.model = model
        binding.itemTheme = HalfChartItemTheme(context!!.getColorFromAttr(ownTheme.chartItemColor))
        binding.items = model.data.items.map { item ->
            HalfChartItem(
                (item as? StatisticItem)?.getName(requireContext()) ?: item.name,
                CurrencyUtils.formatAmountExactWithCurrencySymbol(item.amount.toDouble()),
                View.OnClickListener { onItemClicked(item) })
        }
        binding.totalAmount =
            CurrencyUtils.formatAmountExactWithCurrencySymbol(model.amount.toDouble())
        binding.executePendingBindings()

        binding.root.post { onViewReady() }
    }

    private fun onItemClicked(item: ChartItem) {
        when (item) {
            is StatisticItem -> chartViewModel.setCategoryId(item.category.code)
            is TransactionsItem -> showTransactions(item)
        }
    }

    private fun showTransactions(item: TransactionsItem) {
        if (item.ids.isEmpty()) {
            throw NoSuchElementException("List of transactions is empty.")
        } else {
            val metaData = TransactionsListMetaData(
                statusBarColor = theme.statusBarTheme.statusBarColor,
                backgroundColor = theme.toolbarTheme.backgroundColor,
                title = item.name,
                transactionIds = item.ids
            )
            fragmentCoordinator.add(
                fragment = TransactionsListFragment.newInstance(metaData),
                addToBackStack = true,
                animation = FragmentAnimationFlags.SLIDE_UP
            )
        }
    }

    private fun changeTransition() = TransitionSet().apply {
        addTransition(TransitionSet().apply {
            addTransition(PieChartLabelTransition())
            addTransition(PieChartSegmentTransition(R.id.transition_group_main))
            addTransition(Fade().apply { addTarget(R.id.back_segment) })
            // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//            addTransition(TextAmountTransition(CurrencyUtils.getMinusSign()) {
//                CurrencyUtils.formatAmountExactWithCurrencySymbol(it.toDouble()).apply { }
//            }.apply {
//                addTarget(R.id.amount)
//            })
        })
        addTransition(TransitionSet().apply {
            ordering = TransitionSet.ORDERING_SEQUENTIAL
            addTarget(R.id.chartItem)
            addTransition(Fade(Fade.MODE_OUT))
            addTransition(ChangePositionTransition())
            addTransition(Fade(Fade.MODE_IN))
        })
    }

    companion object {
        fun newInstance(type: ChartType) = HalfPieChartFragment().apply {
            arguments = Bundle().apply { putSerializable(TYPE_ARG, type) }
        }
    }
}

internal data class HalfChartItem(val title: String, val amount: String, val onClick: View.OnClickListener)

internal class HalfChartItemTheme(@ColorInt val textColor: Int)

private fun StatisticItem.getName(context: Context) =
    category.getNameWithDefaultChildFormat(
        context.getString(R.string.tink_category_default_child_format)
    )

