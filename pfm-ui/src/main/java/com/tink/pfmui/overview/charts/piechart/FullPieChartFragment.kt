package com.tink.pfmui.overview.charts.piechart


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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.charts.PieChartLabelView
import com.tink.pfmui.charts.extensions.childOrNull
import com.tink.pfmui.charts.transitions.PieChartLabelTransition
import com.tink.pfmui.charts.transitions.PieChartSegmentTransition
import com.tink.pfmui.charts.transitions.PieChartTransition
import com.tink.pfmui.charts.transitions.TextAmountTransition
import com.tink.pfmui.charts.transitions.TranslationTransition
import com.tink.pfmui.databinding.FragmentFullPieChartBinding
import com.tink.pfmui.databinding.PieChartLabelBinding
import com.tink.pfmui.overview.charts.ChartDetailsViewModel
import com.tink.pfmui.overview.charts.ChartType
import com.tink.pfmui.overview.charts.DetailsChartModel
import com.tink.pfmui.overview.charts.PieChartDetailsViewModel
import com.tink.pfmui.overview.charts.StatisticItem
import com.tink.pfmui.overview.charts.StatisticItemsList
import com.tink.pfmui.overview.getAmountStringForOverviewPieChart
import com.tink.pfmui.theme.getTabPieChartThemeForType
import com.tink.pfmui.tracking.ScreenEvent
import com.tink.pfmui.util.CurrencyUtils
import kotlinx.android.synthetic.main.fragment_full_pie_chart.view.*
import se.tink.commons.categories.getIcon
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.backgroundTint
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.setImageResFromAttr
import se.tink.commons.extensions.tint
import javax.inject.Inject
import kotlin.properties.Delegates

private const val TYPE_ARG = "type"

internal class FullPieChartFragment : BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(parentFragment!!, viewModelFactory)[PieChartDetailsViewModel::class.java] }
    private val pageViewModel by lazy { ViewModelProviders.of(rootFragment, viewModelFactory)[ChartDetailsViewModel::class.java] }
    private val type by lazy { arguments?.getSerializable(TYPE_ARG) as? ChartType ?: ChartType.EXPENSES }
    private val ownTheme by lazy { getTabPieChartThemeForType(context!!, type) }

    @Inject
    lateinit var amountFormatter: AmountFormatter

    override fun getLayoutId() = R.layout.fragment_full_pie_chart
    override fun needsLoginToBeAuthorized() = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.TRACKING_ERROR
    override fun doNotRecreateView() = false
    override fun shouldTrackScreen() = false
    override fun getTheme(): Theme = ownTheme
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        val binding = DataBindingUtil.bind<FragmentFullPieChartBinding>(view.root) ?: throw IllegalStateException("Binding is null")

        viewModel.apply {
            getStatistic(context!!, type).observe(viewLifecycle, Observer { it?.let { updateModel(binding, it) } })
        }
    }

    private fun updateModel(binding: FragmentFullPieChartBinding, model: DetailsChartModel) {
        if (!model.topLevel || model.data !is StatisticItemsList) return

        if (!transitionCoordinator.hasTransitionInProgress()) {
            TransitionManager.beginDelayedTransition(view as ViewGroup, changeTransition(model.data))
        }

        view.pieChart.apply {
            removeAllViews()
            addBackSegment(model.title, model.color)
            addSegments(model.data.items, { it.amount }, model.colorGenerator, model.color, ::createLabel, onClick = ::onItemClick)
        }
        binding.model = model
        binding.totalAmount = getAmountStringForOverviewPieChart(amountFormatter, model.amount.toDouble(), context!!)
        binding.executePendingBindings()

        binding.root.post { onViewReady() }
    }

    private fun createLabel(item: StatisticItem, startAngle: Float, sweep: Float): PieChartLabelView {
        val anchor = startAngle + sweep / 2f
        val lineWidth = resources.getDimension(R.dimen.pie_chart_label_line_width)
        val iconColor = ownTheme.iconTheme.iconColorAttr
        val circleColorRes = ownTheme.iconTheme.iconCircleColorAttr
        val circleColor = requireContext().getColorFromAttr(circleColorRes)
        return PieChartLabelView(context!!, anchor).also { label ->
            DataBindingUtil.inflate<PieChartLabelBinding>(LayoutInflater.from(context), R.layout.pie_chart_label, label, true).apply {
                title.text = amountFormatter.format(item.amount.toDouble(), useSymbol = true)
                type = item.category.code
                icon.setImageResFromAttr(item.category.getIcon())
                icon.tint(iconColor)
                icon.backgroundTint(circleColorRes)
                icon.setOnClickListener { onItemClick(item) }
                label.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> placeLabelTitle(label, this) }
            }
            label.transitionName = item.category.code
            label.isTransitionGroup = false
            label.radialPadding = resources.getDimension(R.dimen.pie_chart_label_radial_padding)
            label.decorator = LabelDecorator(label, circleColor, lineWidth)
        }
    }

    private fun onItemClick(item: StatisticItem) = pageViewModel.setCategoryId(item.category.code)

    private fun placeLabelTitle(label: PieChartLabelView, binding: PieChartLabelBinding) {
        val iconOnTop = (label.centerAngle - 90f + 360f) % 360 < 180
        with(binding) {
            icon.translationY = if (iconOnTop) -title.height.toFloat() else 0f
            title.translationY = if (iconOnTop) icon.height.toFloat() else 0f
        }
    }

    private fun changeTransition(data: StatisticItemsList) = TransitionSet().apply {
        addTransition(PieChartLabelTransition())
        addTransition(PieChartTransition())
        addTransition(PieChartSegmentTransition(R.id.transition_group_main))
        addTransition(Fade().apply { addTarget(R.id.back_segment) })
        // TODO: Fix this once we have figured out how to do amount transitions for floating point numbers
//        addTransition(TextAmountTransition(CurrencyUtils.getMinusSign()) {
//            amountFormatter.format(it.toDouble(), useSymbol = false)
//        }.apply {
//            addTarget(R.id.amount)
//        })

        val labelCategories = data.items.map { it.category.code }
        if (labelCategories.isNotEmpty()) {
            addTransition(TranslationTransition().apply {
                labelCategories.forEach {
                    addTarget(getString(R.string.label_title_transition, it))
                    addTarget(getString(R.string.label_icon_transition, it))
                }
            })
        }
    }

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



