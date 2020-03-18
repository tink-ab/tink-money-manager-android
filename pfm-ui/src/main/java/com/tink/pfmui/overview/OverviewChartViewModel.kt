package com.tink.pfmui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.tink.pfmui.ClientDataStorage
import com.tink.pfmui.R
import com.tink.pfmui.overview.charts.calculateStatistic
import com.tink.pfmui.overview.charts.getPeriodString
import com.tink.pfmui.charts.ColorGenerator
import com.tink.pfmui.charts.DefaultColorGenerator
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.mapDistinct
import se.tink.android.categories.CategoryRepository
import com.tink.pfmui.repository.StatisticsRepository
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.getColorFromAttr
import se.tink.core.extensions.whenNonNull
import com.tink.model.category.CategoryTree
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.StatisticTree
import se.tink.utils.DateUtils
import javax.inject.Inject

private class OverviewData(val statistic: StatisticTree, val period: Period, val categories: CategoryTree)

internal class OverviewChartViewModel @Inject constructor(
    private val dataStorage: ClientDataStorage,
    private val dateUtils: DateUtils,
    statisticRepository: StatisticsRepository,
    categoryRepository: CategoryRepository,
    private val amountFormatter: AmountFormatter,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val statistics = statisticRepository.getStatistics()
    private val period = statisticRepository.currentPeriod
    private val categories = categoryRepository.categories

    private val data = MediatorLiveData<OverviewData>().apply {
        fun update() = whenNonNull(statistics.value, period.value, categories.value) { stat, period, categories ->
            value = OverviewData(stat, period, categories)
        }

        addSource(statistics) { update() }
        addSource(period) { update() }
        addSource(categories) { update() }
    }

    val loaded: LiveData<Boolean> = Transformations.map(data) { it != null }

    var lastVisitedPageInOverview
        get() = dataStorage.lastVisitedPageInOverview
        set(value) {
            dataStorage.lastVisitedPageInOverview = value
        }

    val expenses: LiveData<OverviewChartModel> = mapDistinct(data) {
        val data = calculateStatistic(
            it.statistic.expensesByCategoryCode,
            it.categories.expenses.children,
            it.period
        ).items.map { it.amount }
        //val color = ContextCompat.getColor(context, R.color.expenses)
        //val color = context.getColorFromAttr(R.attr.tink_expensesColor);
        val color = context.getColorFromAttr(attrColor = R.attr.tink_expensesColor, resolveRefs = false);
        val period = getPeriodString(dateUtils, it.period, context)
        OverviewChartModel(
            context,
            R.string.tink_expenses_title,
            data.sum(),
            amountFormatter,
            period,
            color,
            DefaultColorGenerator,
            ArrayList(data)
        )
    }

    val income: LiveData<OverviewChartModel> = mapDistinct(data) {
        val data = calculateStatistic(
            it.statistic.incomeByCategoryCode,
            it.categories.income.children,
            it.period
        ).items.map { it.amount }
        //val color = ContextCompat.getColor(context, R.color.income)
        val color = context.getColorFromAttr(R.attr.tink_incomeColor);

        val periodString =
            getPeriodString(dateUtils, it.period, context)
        OverviewChartModel(
            context,
            R.string.tink_income_title,
            data.sum(),
            amountFormatter,
            periodString,
            color,
            DefaultColorGenerator,
            ArrayList(data)
        )
    }

    val leftToSpend: LiveData<OverviewChartModel> = mapDistinct(MediatorLiveData<OverviewChartModel>().apply {
        fun update() = whenNonNull(expenses.value?.amountRaw, income.value?.amountRaw, period.value) { expenses, income, period ->
            val leftToSpend = income - expenses
            val data = ArrayList(listOf(expenses, maxOf(leftToSpend, 0f)))
            //val color = ContextCompat.getColor(context, R.color.leftToSpend)
            val color = context.getColorFromAttr(R.attr.tink_leftToSpendColor);

            val periodStr =
                getPeriodString(dateUtils, period, context, false)
            value = OverviewChartModel(
                context,
                R.string.left_to_spend_title,
                leftToSpend,
                amountFormatter,
                periodStr,
                color,
                LeftToSpendColorGenerator,
                data
            )
        }
        addSource(expenses) { update() }
        addSource(income) { update() }
        addSource(period) { update() }
    }) { it }
}

private object LeftToSpendColorGenerator : ColorGenerator {
    @ColorInt
    override fun color(@ColorInt baseColor: Int, idx: Int) = when (idx) {
        0 -> Color.TRANSPARENT
        else -> baseColor
    }
}

internal data class OverviewChartModel(
    val title: String,
    val amountRaw: Float,
    val amount: String,
    val period: String,
    @ColorInt val color: Int,
    val colorGenerator: ColorGenerator,
    // Use list with structural equals
    val data: ArrayList<Float>
) {
    constructor(
        context: Context,
        @StringRes title: Int,
        amount: Float,
        amountFormatter: AmountFormatter,
        period: String,
        @ColorInt color: Int,
        colorGenerator: ColorGenerator,
        data: ArrayList<Float>
    ) : this(
        context.getString(title),
        amount,
        getAmountStringForOverviewPieChart(amountFormatter, amount.toDouble(), context),
        period,
        color,
        colorGenerator,
        data
    )
}

internal fun getAmountStringForOverviewPieChart(
    amountFormatter: AmountFormatter,
    amount: Double,
    context: Context
): String =
    amountFormatter.format(
        amount,
        useSymbol = context.resources.getBoolean(R.bool.tink_config_overview_show_currency_symbol_in_chart)
    )
