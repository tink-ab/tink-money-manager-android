package com.tink.pfmui.overview.charts

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.category.Category
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.pfmui.R
import com.tink.pfmui.charts.ColorGenerator
import com.tink.pfmui.charts.DefaultColorGenerator
import com.tink.pfmui.charts.extensions.sumByFloat
import com.tink.pfmui.repository.StatisticsRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.mapDistinct
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.commons.extensions.floatValue
import se.tink.commons.extensions.getColorFromAttr
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils
import javax.inject.Inject
import kotlin.math.absoluteValue

private data class SourceData(val period: Period, val category: Category)

private sealed class ChartData(val source: SourceData)
private class StatisticalData(source: SourceData, val statistic: List<Statistics>) : ChartData(source)

private const val MAX_TRANSACTIONS_TO_SHOW = 6

private class TransactionsData(
    source: SourceData,
    val transactions: List<Transaction>
) : ChartData(source)


internal class PieChartDetailsViewModel @Inject constructor(
    private val dateUtils: DateUtils,
    @ApplicationScoped private val context: Context,
    statisticRepository: StatisticsRepository,
    transactionRepository: TransactionRepository
) : ViewModel() {

    private val statistics = statisticRepository.statistics
    private val period = MediatorLiveData<Period>().apply {
        addSource(statisticRepository.currentPeriod) {
            if (value == null) value = it
        }
    }
    val category = MutableLiveData<Category>()


    //TODO: Core setup - revisit
    private val periodComparator = Comparator<Period> { first, second ->
        when {
            first.start.isAfter(second.start) ->  -1
            first.start.isBefore(second.start) ->  1
            first.end.isAfter(second.end) ->  -1
            first.end.isBefore(second.end) ->  1
            else -> 0
        }
    }

    val periods: LiveData<List<Period>> = Transformations.map(statisticRepository.periods) {
        it.sortedWith(periodComparator).reversed().takeLast(12)
    }
    val selectedPeriod: LiveData<Period> get() = period
    val periodFormatter: (Period) -> String =
        { dateUtils.getMonthNameAndMaybeYearOfPeriod(it).capitalize() }

    private val sourceData = MediatorLiveData<SourceData>().apply {
        fun update() = whenNonNull(
            period.value,
            category.value
        ) { period, category ->
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
            val topLevel = src.category.parentId == null
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
                context.getString(R.string.tink_other),
                data.transactions
            )
            is StatisticalData -> {
                val src = data.source
                when (type) {
                    ChartType.EXPENSES -> calculateStatistic(
                        data.statistic.filter { it.type == "expenses-by-category" },
                        src.category.children,
                        src.period
                    )

                    ChartType.INCOME -> calculateStatistic(
                        data.statistic.filter { it.type == "income-by-category" },
                        src.category.children,
                        src.period
                    )
                }
            }
        }
    }

    private fun calculateTransactionsStatistic(
        other: String,
        transactions: List<Transaction>
    ): TransactionsItemsList {
        val items = transactions
            .groupBy { it.description }
            .entries
            .map { (name, transactions) ->
                TransactionsItem(
                    name,
                    transactions.sumByFloat { transaction ->
                        transaction
                            .dispensableAmount.value
                            .floatValue()
                            .absoluteValue
                    },
                    transactions.map { it.id }
                )
            }
            .sortedByDescending { it.amount }

        if (items.size < MAX_TRANSACTIONS_TO_SHOW) {
            return TransactionsItemsList(ArrayList(items))
        }
        return TransactionsItemsList(
            ArrayList(items.take(MAX_TRANSACTIONS_TO_SHOW - 1))
                .apply {
                    val toOther =
                        items.drop(MAX_TRANSACTIONS_TO_SHOW - 1)
                    add(
                        TransactionsItem(
                            other,
                            toOther.sumByFloat { it.amount },
                            toOther.flatMap { it.ids })
                    )
                }
        )
    }

    fun setPeriod(p: Period) = period.postValue(p)

    fun setCategory(it: Category) {
        category.value = it
    }
}

internal data class DetailsChartModel(
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
