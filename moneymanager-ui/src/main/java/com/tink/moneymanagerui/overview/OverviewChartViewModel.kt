package com.tink.moneymanagerui.overview

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.category.CategoryTree
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.ColorGenerator
import com.tink.moneymanagerui.charts.DefaultColorGenerator
import com.tink.moneymanagerui.overview.charts.calculateStatistic
import com.tink.moneymanagerui.overview.charts.getPeriodString
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.repository.StatisticsState
import com.tink.moneymanagerui.theme.resolveColorForFeature
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.android.livedata.mapDistinct
import se.tink.android.livedata.requireValue
import se.tink.android.repository.user.UserRepository
import se.tink.commons.currency.AmountFormatter
import se.tink.utils.DateUtils
import javax.inject.Inject

internal class OverviewData(val statistics:  List<Statistics>, val period: Period, val categories: CategoryTree, val currency: String)

internal class OverviewChartViewModel @Inject constructor(
    private val dateUtils: DateUtils,
    private val statisticRepository: StatisticsRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
    private val amountFormatter: AmountFormatter,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val statistics = statisticRepository.statisticsState
    private val categories = categoryRepository.categoriesState
    private val userProfile = userRepository.userProfileState
    private val period = statisticRepository.currentPeriodState

    private val data = MediatorLiveData<StatisticsState>().apply {
        value = StatisticsState()

        addSource(statistics) {
            value = requireValue.copy(statistics = it)
        }
        addSource(period) {
            value = requireValue.copy(period = it)
        }
        addSource(categories) {
            value = requireValue.copy(categories = it)
        }
        addSource(userProfile) {
            value = requireValue.copy(userProfile = it)
        }
    }

    internal val overviewState: LiveData<ResponseState<OverviewData>> = data.map {
        it.overallState
    }

    private val overviewData: LiveData<OverviewData> = MediatorLiveData<OverviewData>().apply {
        addSource(data) {
            if (it.overallState is SuccessState<OverviewData>) {
                postValue(it.overallState.data)
            }
        }
    }

    internal fun refreshData() {
        userRepository.refreshState()
        statisticRepository.refresh()
        categoryRepository.refreshState()
    }

    val expenses: LiveData<OverviewChartModel> = mapDistinct(overviewData) { overviewData ->
        val data: List<Float> =
            calculateStatistic(
                overviewData.statistics.filter { s -> s.type == Statistics.Type.EXPENSES_BY_CATEGORY },
                overviewData.categories.expenses.children,
                overviewData.period
            ).items.map { it.amount }
        val color = context.resolveColorForFeature(R.attr.tink_expensesColor, MoneyManagerFeatureType.STATISTICS)
        val period = getPeriodString(dateUtils, overviewData.period)
        OverviewChartModel(
            context,
            R.string.tink_expenses_title,
            data.sum(),
            overviewData.currency,
            amountFormatter,
            period,
            color,
            DefaultColorGenerator,
            ArrayList(data)
        )
    }

    val income: LiveData<OverviewChartModel> = mapDistinct(overviewData) {
        val data: List<Float> =
            calculateStatistic(
                it.statistics.filter { s -> s.type == Statistics.Type.INCOME_BY_CATEGORY },
                it.categories.income.children,
                it.period
            ).items.map { it.amount }
        val color = context.resolveColorForFeature(R.attr.tink_incomeColor, MoneyManagerFeatureType.STATISTICS)
        val periodString =
            getPeriodString(dateUtils, it.period)
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
        useSymbol = context.resources.getBoolean(R.bool.tink_config_overview_show_currency_symbol_in_chart)
    )
