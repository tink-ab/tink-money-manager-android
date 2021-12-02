package com.tink.moneymanagerui.overview.charts.piechart

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
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
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.PieChartLabelView
import com.tink.moneymanagerui.charts.extensions.childOrNull
import com.tink.moneymanagerui.charts.transitions.PieChartLabelTransition
import com.tink.moneymanagerui.charts.transitions.PieChartSegmentTransition
import com.tink.moneymanagerui.charts.transitions.PieChartTransition
import com.tink.moneymanagerui.charts.transitions.TranslationTransition
import com.tink.moneymanagerui.databinding.TinkPieChartLabelBinding
import com.tink.moneymanagerui.overview.charts.ChartDetailsViewModel
import com.tink.moneymanagerui.overview.charts.ChartType
import com.tink.moneymanagerui.overview.charts.DetailsChartModel
import com.tink.moneymanagerui.overview.charts.PieChartDetailsViewModel
import com.tink.moneymanagerui.overview.charts.StatisticItem
import com.tink.moneymanagerui.overview.charts.StatisticItemsList
import com.tink.moneymanagerui.overview.getAmountStringForOverviewPieChart
import com.tink.moneymanagerui.theme.getTabPieChartThemeForType
import com.tink.moneymanagerui.theme.resolveColorForFeature
import com.tink.moneymanagerui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_full_pie_chart.*
import kotlinx.android.synthetic.main.tink_fragment_full_pie_chart.view.*
import se.tink.commons.categories.getIcon
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.setImageResFromAttr
import javax.inject.Inject
import kotlin.properties.Delegates

private const val TYPE_ARG = "type"

internal class FullPieChartFragment : BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(requireParentFragment(), viewModelFactory)[PieChartDetailsViewModel::class.java] }
    private val pageViewModel by lazy { ViewModelProviders.of(rootFragment, viewModelFactory)[ChartDetailsViewModel::class.java] }
    private val type by lazy { arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES }
    private val ownTheme by lazy { getTabPieChartThemeForType(type) }

    @Inject
    lateinit var amountFormatter: AmountFormatter

    override fun getLayoutId() = R.layout.tink_fragment_full_pie_chart
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRACKING_ERROR
    override fun doNotRecreateView() = false
    override fun shouldTrackScreen() = false
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        viewModel.apply {
            getStatistic(requireContext(), type).observe(viewLifecycle, Observer { it?.let { updateModel(it) } })
        }
    }

    private fun updateModel(model: DetailsChartModel) {
        if (!model.topLevel || model.data !is StatisticItemsList) return

        if (!transitionCoordinator.hasTransitionInProgress()) {
            TransitionManager.beginDelayedTransition(view as ViewGroup, changeTransition(model.data))
        }

        view.pieChart.apply {
            removeAllViews()
            addBackSegment(model.title, model.color)
            addSegments(model.data.items, { it.amount }, model.colorGenerator, model.color, model.currency, ::createLabel, onClick = ::onItemClick)
        }

        labelTitle.text = model.title
        amountText.text = getAmountStringForOverviewPieChart(amountFormatter, model.amount.toDouble(), model.currency, requireContext())
        period.text = model.period

        onViewReady()
    }

    private fun createLabel(item: StatisticItem, currency: String, startAngle: Float, sweep: Float): PieChartLabelView {
        val anchor = startAngle + sweep / 2f
        val lineWidth = resources.getDimension(R.dimen.tink_pie_chart_label_line_width)

        val iconColor = view.context.resolveColorForFeature(
            ownTheme.iconTheme.iconColorAttr,
            MoneyManagerFeatureType.STATISTICS
        )
        val circleColor = view.context.resolveColorForFeature(
            ownTheme.iconTheme.iconCircleColorAttr,
            MoneyManagerFeatureType.STATISTICS
        )
        val primaryTextColor = view.context.resolveColorForFeature(R.attr.tink_textColorPrimary, MoneyManagerFeatureType.STATISTICS)
        return PieChartLabelView(requireContext(), anchor).also { label ->
            val pieChartLabel = TinkPieChartLabelBinding.inflate(layoutInflater, label, true)

            pieChartLabel.pieChartText.text =
                amountFormatter.format(item.amount.toDouble(), currency, useSymbol = true, useRounding = true)
            pieChartLabel.pieChartText.setTextColor(primaryTextColor)
            pieChartLabel.icon.setImageResFromAttr(item.category.getIcon())
            pieChartLabel.icon.setColorFilter(iconColor)
            pieChartLabel.icon.setBackgroundColor(circleColor)
            pieChartLabel.icon.setOnClickListener { onItemClick(item) }

            label.transitionName = item.category.code
            label.isTransitionGroup = false
            label.radialPadding =
                resources.getDimension(R.dimen.tink_pie_chart_label_radial_padding)
            label.decorator = LabelDecorator(label, circleColor, lineWidth)
        }
    }

    private fun onItemClick(item: StatisticItem) = pageViewModel.setCategoryId(item.category.id)

    private fun changeTransition(data: StatisticItemsList) = TransitionSet().apply {
        addTransition(PieChartLabelTransition())
        addTransition(PieChartTransition())
        addTransition(PieChartSegmentTransition(R.id.tink_transition_group_main))
        addTransition(Fade().apply { addTarget(R.id.tink_back_segment) })
        // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//        addTransition(TextAmountTransition(CurrencyUtils.minusSign) {
//            amountFormatter.format(it.toDouble(), useSymbol = false)
//        }.apply {
//            addTarget(R.id.amount)
//        })

        val labelCategories = data.items.map { it.category.code }
        if (labelCategories.isNotEmpty()) {
            addTransition(TranslationTransition().apply {
                labelCategories.forEach {
                    addTarget(getString(R.string.tink_label_title_transition, it))
                    addTarget(getString(R.string.tink_label_icon_transition, it))
                }
            })
        }
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.STATISTICS

    companion object {
        fun newInstance(type: ChartType) = FullPieChartFragment().apply {
            arguments = Bundle().apply { putSerializable(TYPE_ARG, type) }
        }
    }
}

internal class LabelDecorator(private val view: PieChartLabelView, @ColorInt color: Int, width: Float) : PieChartLabelView.Decorator {
    private val icon = view.findViewById<View>(R.id.icon)
    private val tmp = Rect()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = color
        it.strokeWidth = width
    }

    override var visible: Boolean by Delegates.observable(true) { _, _, _ -> view.invalidate() }

    override fun draw(canvas: Canvas) {
        if (!visible) return
        val anchor = view.getAnchor()
        val directChild = view.childOrNull(0) ?: return
        tmp.set(0, 0, icon.width, icon.height)
        view.offsetDescendantRectToMyCoords(icon, tmp)
        tmp.offset(directChild.translationX.toInt(), directChild.translationY.toInt())
        tmp.offset(icon.translationX.toInt(), icon.translationY.toInt())
        canvas.drawLine(tmp.exactCenterX(), tmp.exactCenterY(), anchor.x, anchor.y, paint)
    }
}
