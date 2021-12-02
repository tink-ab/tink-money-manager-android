package com.tink.moneymanagerui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.overview.charts.calculateStatistic
import com.tink.moneymanagerui.overview.charts.getPeriodString
import com.tink.moneymanagerui.charts.ColorGenerator
import com.tink.moneymanagerui.charts.DefaultColorGenerator
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.mapDistinct
import se.tink.android.categories.CategoryRepository
import com.tink.moneymanagerui.repository.StatisticsRepository
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.whenNonNull
import com.tink.model.category.CategoryTree
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.theme.resolveColorForFeature
import se.tink.android.repository.user.UserRepository
import se.tink.utils.DateUtils
import javax.inject.Inject

private class OverviewData(val statistics:  List<Statistics>, val period: Period, val categories: CategoryTree, val currency: String)

internal class OverviewChartViewModel @Inject constructor(
    private val dateUtils: DateUtils,
    statisticRepository: StatisticsRepository,
    categoryRepository: CategoryRepository,
    userRepository: UserRepository,
    private val amountFormatter: AmountFormatter,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val statistics = statisticRepository.statistics
    private val period = statisticRepository.currentPeriod
    private val categories = categoryRepository.categories

    private val userProfile = userRepository.userProfile

    private val data = MediatorLiveData<OverviewData>().apply {
        fun update() = whenNonNull(
            statistics.value,
            period.value,
            categories.value,
            userProfile.value?.currency
        ) { stat, period, categories, currency ->
            value = OverviewData(stat, period, categories, currency)
        }

        addSource(statistics) { update() }
        addSource(period) { update() }
        addSource(categories) { update() }
        addSource(userProfile) { update() }
    }

    val isDoneLoading: LiveData<Boolean> = Transformations.map(data) { it != null }

    val expenses: LiveData<OverviewChartModel> = mapDistinct(data) {
        val data: List<Float> =
            calculateStatistic(
                it.statistics.filter { s -> s.type == Statistics.Type.EXPENSES_BY_CATEGORY },
                it.categories.expenses.children,
                it.period
            ).items.map { it.amount }
        val color = context.resolveColorForFeature(R.attr.tink_expensesColor, MoneyManagerFeatureType.STATISTICS)
        val period = getPeriodString(dateUtils, it.period, context)
        OverviewChartModel(
            context,
            R.string.tink_expenses_title,
            data.sum(),
            it.currency,
            amountFormatter,
            period,
            color,
            DefaultColorGenerator,
            ArrayList(data)
        )
    }

    val income: LiveData<OverviewChartModel> = mapDistinct(data) {
        val data: List<Float> =
            calculateStatistic(
                it.statistics.filter { s -> s.type == Statistics.Type.INCOME_BY_CATEGORY },
                it.categories.income.children,
                it.period
            ).items.map { it.amount }
        val color = context.resolveColorForFeature(R.attr.tink_incomeColor, MoneyManagerFeatureType.STATISTICS)
        val periodString =
            getPeriodString(dateUtils, it.period, context)
        OverviewChartModel(
            context,
            R.string.tink_income_title,
            data.sum(),
            it.currency,
            amountFormatter,
            periodString,
            color,
            DefaultColorGenerator,
            ArrayList(data)
        )
    }
}

internal data class OverviewChartModel(
    val title: String,
    val amountRaw: Float,
    val amount: String,
    val currency: String,
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
        currency: String,
        amountFormatter: AmountFormatter,
        period: String,
        @ColorInt color: Int,
        colorGenerator: ColorGenerator,
        data: ArrayList<Float>
    ) : this(
        context.getString(title),
        amount,
        getAmountStringForOverviewPieChart(amountFormatter, amount.toDouble(), currency, context),
        currency,
        period,
        color,
        colorGenerator,
        data
    )
}

internal fun getAmountStringForOverviewPieChart(
    amountFormatter: AmountFormatter,
    amount: Double,
    currency: String,
    context: Context
): String =
    amountFormatter.format(
        amount,
        currency,
        useSymbol = context.resources.getBoolean(R.bool.tink_config_overview_show_currency_symbol_in_chart),
        useRounding = true
    )
