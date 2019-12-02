package com.tink.pfmsdk.overview.charts

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.pfmsdk.R
import com.tink.pfmsdk.repository.StatisticsRepository
import se.tink.android.charts.ui.ColorGenerator
import se.tink.android.charts.ui.DefaultColorGenerator
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.extensions.sumByFloat
import se.tink.android.livedata.mapDistinct
import se.tink.android.livedata.switchMap
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.extensions.getColorFromAttr
import se.tink.core.extensions.whenNonNull
import se.tink.core.models.Category
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.StatisticTree
import se.tink.core.models.transaction.Transaction
import se.tink.utils.DateUtils
import javax.inject.Inject

private data class SourceData(val period: Period, val category: Category)

private sealed class ChartData(val source: SourceData)
private class StatisticalData(source: SourceData, val statistic: StatisticTree) : ChartData(source)

private class TransactionsData(
    source: SourceData,
    val transactions: List<Transaction>
) : ChartData(source)


class PieChartDetailsViewModel @Inject constructor(
    private val dateUtils: DateUtils,
    @ApplicationScoped private val context: Context,
    statisticRepository: StatisticsRepository,
    transactionRepository: TransactionRepository
) : ViewModel() {

    private val statistics = statisticRepository.getStatistics()
    private val period = MediatorLiveData<Period>().apply {
        addSource(statisticRepository.currentPeriod) {
            if (value == null) value = it
        }
    }
    val category = MutableLiveData<Category>()

    val periods: LiveData<List<Period>> = Transformations.map(statisticRepository.periods) {
        it?.sortedDescending()?.takeLast(12)
    }
    val selectedPeriod: LiveData<Period> get() = period
    val periodFormatter: (Period) -> String =
        { dateUtils.getMonthNameAndMaybeYearOfPeriod(it).capitalize() }

    private val sourceData = MediatorLiveData<SourceData>().apply {
        fun update() = whenNonNull(period.value, category.value) { period, category ->
            value = SourceData(period, category)
        }

        addSource(period) { update() }
        addSource(category) { update() }
    }

    private val statisticData: LiveData<ChartData> =
        Transformations.switchMap(sourceData) { source ->
            if (source.category.children.isNotEmpty()) {
                Transformations.map(statistics) {
                    StatisticalData(
                        source,
                        it
                    ) as ChartData
                }
            } else {
                Transformations.map(
                    transactionRepository.allTransactionsForCategoryAndPeriod(
                        source.category,
                        source.period
                    )
                ) { transactions ->
                    TransactionsData(
                        source,
                        transactions
                    ) as ChartData
                }
            }
        }

    fun getStatistic(context: Context, type: ChartType): LiveData<DetailsChartModel> =
        mapDistinct(statisticData) {
            val data = calculateStatistic(type, it)
            val color = context.getColorFromAttr(type.color)
            val src = it.source
            val periodStr =
                getPeriodString(dateUtils, src.period, context)
            val topLevel = src.category.parent == null
            DetailsChartModel(
                context,
                type.title,
                data.items.sumByFloat { it.amount },
                periodStr,
                color,
                DefaultColorGenerator,
                topLevel,
                data
            )
        }

    private fun calculateStatistic(type: ChartType, data: ChartData): ChartList {
        return when (data) {
            is TransactionsData -> calculateTransactionsStatistic(
                context.getString(R.string.other),
                data.transactions
            )
            is StatisticalData -> {
                val src = data.source
                when (type) {
                    ChartType.EXPENSES -> calculateStatistic(
                        data.statistic.expensesByCategoryCode,
                        src.category.children,
                        src.period
                    )

                    ChartType.INCOME -> calculateStatistic(
                        data.statistic.incomeByCategoryCode,
                        src.category.children,
                        src.period
                    )

                    else -> throw IllegalArgumentException("Illegal type $type")
                }
            }
        }
    }

    fun setPeriod(p: Period) = period.postValue(p)

    fun setCategory(it: Category) {
        category.value = it
    }
}

data class DetailsChartModel(
    val title: String,
    val amount: Float,
    val period: String,
    @ColorInt val color: Int,
    val colorGenerator: ColorGenerator,
    val topLevel: Boolean,
    val data: ChartList
) {
    constructor(
        context: Context,
        @StringRes title: Int,
        amount: Float,
        period: String,
        @ColorInt color: Int,
        colorGenerator: ColorGenerator,
        topLevel: Boolean,
        data: ChartList
    ) : this(
        context.getString(title),
        amount,
        period,
        color,
        colorGenerator,
        topLevel,
        data
    )
}
