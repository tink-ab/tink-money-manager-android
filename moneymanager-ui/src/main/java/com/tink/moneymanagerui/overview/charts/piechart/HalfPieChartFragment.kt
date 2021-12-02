package com.tink.moneymanagerui.overview.charts.piechart

import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.tracking.ScreenEvent
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.MoneyManagerFeatureType
import kotlinx.android.synthetic.main.tink_fragment_half_pie_chart.view.*
import com.tink.moneymanagerui.charts.transitions.ChangePositionTransition
import com.tink.moneymanagerui.charts.transitions.PieChartLabelTransition
import com.tink.moneymanagerui.charts.transitions.PieChartSegmentTransition
import com.tink.moneymanagerui.databinding.TinkHalfPieChartItemBinding
import com.tink.moneymanagerui.overview.charts.ChartDetailsViewModel
import com.tink.moneymanagerui.overview.charts.ChartItem
import com.tink.moneymanagerui.overview.charts.ChartType
import com.tink.moneymanagerui.overview.charts.DetailsChartModel
import com.tink.moneymanagerui.overview.charts.PieChartDetailsViewModel
import com.tink.moneymanagerui.overview.charts.StatisticItem
import com.tink.moneymanagerui.overview.charts.TransactionsItem
import com.tink.moneymanagerui.theme.getTabPieChartThemeForType
import com.tink.moneymanagerui.transaction.TransactionsListFragment
import com.tink.moneymanagerui.transaction.TransactionsListMetaData
import kotlinx.android.synthetic.main.tink_fragment_half_pie_chart.*
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.getColorFromAttr
import javax.inject.Inject

private const val TYPE_ARG = "type"

internal class HalfPieChartFragment : BaseFragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(
            requireParentFragment(),
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
    private val ownTheme by lazy { getTabPieChartThemeForType(type) }

    @Inject
    lateinit var amountFormatter: AmountFormatter

    override fun getLayoutId() = R.layout.tink_fragment_half_pie_chart
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRACKING_ERROR
    override fun doNotRecreateView() = false
    override fun shouldTrackScreen() = false
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        viewModel.apply {
            getStatistic(requireContext(), type).observe(
                viewLifecycle,
                Observer { it?.let { updateModel(it) } })
        }
    }

    private fun updateModel(model: DetailsChartModel) {
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
                model.currency,
                startFrom = startAngle,
                fullSweep = fullSweep,
                onClick = ::onItemClicked
            )
        }

        val halfPieChartItems = model.data.items.map { item ->
            HalfChartItem(
                item.name,
                amountFormatter.format(item.amount.toDouble(), model.currency, useSymbol = true),
                View.OnClickListener { onItemClicked(item) })
        }
        bindItems(view.itemsList, halfPieChartItems, HalfChartItemTheme(requireContext().getColorFromAttr(ownTheme.chartItemColor)))
        pieChart.transitionName = getString(R.string.tink_pie_chart_transition, model.title)
        halfPieChartTotalAmount.transitionName = getString(R.string.tink_amount_transition, model.title)
        halfPieChartTotalAmount.text = amountFormatter.format(model.amount.toDouble(), model.currency, useSymbol = true, useRounding = true)

        view.rootView.post { onViewReady() }
    }

    private fun onItemClicked(item: ChartItem) {
        when (item) {
            is StatisticItem -> chartViewModel.setCategoryId(item.category.id)
            is TransactionsItem -> showTransactions(item)
        }
    }

    private fun showTransactions(item: TransactionsItem) {
        if (item.ids.isEmpty()) {
            throw NoSuchElementException("List of transactions is empty.")
        } else {
            val metaData = TransactionsListMetaData(
                title = item.name,
                transactionIds = item.ids
            )
            fragmentCoordinator.add(
                fragment = TransactionsListFragment.newInstance(
                    metaData,
                    MoneyManagerFeatureType.STATISTICS
                ),
                addToBackStack = true,
                animation = FragmentAnimationFlags.SLIDE_UP
            )
        }
    }

    private fun changeTransition() = TransitionSet().apply {
        addTransition(TransitionSet().apply {
            addTransition(PieChartLabelTransition())
            addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
            addTransition(Fade().apply { addTarget(R.id.tink_back_segment) })
            // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//            addTransition(TextAmountTransition(CurrencyUtils.minusSign) {
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

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.STATISTICS

    companion object {
        fun newInstance(type: ChartType) = HalfPieChartFragment().apply {
            arguments = Bundle().apply { putSerializable(TYPE_ARG, type) }
        }
    }
}

internal data class HalfChartItem(val title: String, val amount: String, val onClick: View.OnClickListener)

internal class HalfChartItemTheme(@ColorInt val textColor: Int)

internal fun bindItems(
    view: ViewGroup,
    items: List<HalfChartItem>,
    halfChartItemTheme: HalfChartItemTheme
) {
    with(view) {
        removeAllViews()
        val inflater = LayoutInflater.from(view.context)
        for (item in items) {
            TinkHalfPieChartItemBinding.inflate(inflater, view, true).apply {
                root.setOnClickListener {
                    item.onClick.onClick(root)
                }
                root.transitionName = view.context.getString(R.string.tink_transition_chart_item, item.title)
                label.text = item.title
                label.setTextColor(halfChartItemTheme.textColor)
                amount.text = item.amount
                amount.transitionName = view.context.getString(R.string.tink_transition_chart_item_amount, item.title)
            }
        }
    }
}