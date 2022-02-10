package com.tink.moneymanagerui.overview.charts

import androidx.annotation.AttrRes
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
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.charts.ColorGenerator
import com.tink.moneymanagerui.charts.DefaultColorGenerator
import com.tink.moneymanagerui.charts.extensions.sumByFloat
import com.tink.moneymanagerui.overview.charts.model.ChartDetailsSourceState
import com.tink.moneymanagerui.overview.charts.model.ChartDetailsState
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.repository.TabPieChartDetailsState
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import se.tink.android.livedata.map
import se.tink.android.livedata.requireValue
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.floatValue
import se.tink.utils.DateUtils
import javax.inject.Inject
import kotlin.math.abs

internal data class ChartSourceDataBase(
    val period: Period,
    val category: Category,
    val currency: String)

internal sealed class ChartSourceData(val source: ChartSourceDataBase)

internal class StatisticalChartSourceData(baseData: ChartSourceDataBase, val statistic: List<Statistics>) : ChartSourceData(baseData)

internal class TransactionsChartSourceData(
    baseData: ChartSourceDataBase,
    val transactions: List<Transaction>
) : ChartSourceData(baseData)

private const val MAX_TRANSACTIONS_TO_SHOW = 6

internal class TabPieChartData(
    val selectedPeriod: Period,
    val periods: List<Period>,
    val category: Category,
    val userProfile: UserProfile)

internal data class DetailsChartData(
    @StringRes val title: Int,
    val amount: Float,
    val currency: String,
    val period: String,
    @AttrRes val color: Int,
    val colorGenerator: ColorGenerator,
    val topLevel: Boolean,
    val data: ChartList
)

internal class PieChartDetailsViewModel @Inject constructor(
    private val dateUtils: DateUtils,
    private val transactionRepository: TransactionRepository,
    statisticRepository: StatisticsRepository,
    userRepository: UserRepository
) : ViewModel() {

    private val category = MutableLiveData<ResponseState<Category>>(LoadingState)
    private val userProfile = userRepository.userProfileState
    private val selectedPeriod = MediatorLiveData<ResponseState<Period>>().apply {
        addSource(statisticRepository.currentPeriodState) { value = it }
    }
    private val periods: LiveData<ResponseState<List<Period>>> = Transformations.map(statisticRepository.periodsState) {
        if (it is SuccessState<List<Period>>) {
            SuccessState(it.data.sortedWith(periodComparator).reversed().takeLast(12))
        } else {
            it
        }
    }
    private val statisticsState = statisticRepository.statisticsState

    private val tabPieChartData = MediatorLiveData<TabPieChartDetailsState>().apply {
        value = TabPieChartDetailsState()

        addSource(selectedPeriod) {
            value = requireValue.copy(selectedPeriod = it)
        }
        addSource(periods) {
            value = requireValue.copy(periods = it)
        }
        addSource(category) {
            value = requireValue.copy(category = it)
        }
        addSource(userProfile) {
            value = requireValue.copy(userProfile = it)
        }
    }

    internal val tabPieChartDataState: LiveData<ResponseState<TabPieChartData>> = tabPieChartData.map {
        it.overallState
    }

    private val detailsChartSourceData = MediatorLiveData<ChartDetailsSourceState>().apply {
        value = ChartDetailsSourceState()

        addSource(selectedPeriod) {
            value = requireValue.copy(period = it)
        }
        addSource(category) {
            value = requireValue.copy(category = it)
        }
        addSource(userProfile) {
            val currency = userProfile.value?.map { userProfile ->  userProfile.currency } ?: LoadingState
            value = requireValue.copy(currency = currency)
        }
    }

    private val transactionsState: LiveData<ResponseState<List<Transaction>>> =
        Transformations.switchMap(detailsChartSourceData) { sourceDataToTransactions(it) }

    private fun sourceDataToTransactions(
        chartDetailsSourceData: ChartDetailsSourceState
    ): LiveData<ResponseState<List<Transaction>>> =
        when {
            chartDetailsSourceData.category is SuccessState && chartDetailsSourceData.period is SuccessState ->
                transactionRepository.allTransactionsForCategoryAndPeriod(
                    chartDetailsSourceData.category.data,
                    chartDetailsSourceData.period.data
                ).map { SuccessState(it) }

            else -> MutableLiveData(LoadingState)
        }

    private val detailsChartData = MediatorLiveData<ChartDetailsState>().apply {
        value = ChartDetailsState()

        addSource(detailsChartSourceData) {
            value = requireValue.copy(sourceData = it.overallState)
        }

        addSource(statisticsState) {
            value = requireValue.copy(statistics = it)
        }

        addSource(transactionsState) {
            value = requireValue.copy(transactions = it)
        }
    }

    fun getDetailsChartDataState(type: ChartType, transactionNameOther: String): LiveData<ResponseState<DetailsChartData>> =
        detailsChartData.map { data ->
            data.overallState.map { chartSourceDataToDetailsChartData(it, type, transactionNameOther) }
        }

     private fun chartSourceDataToDetailsChartData
                 (chartSourceData: ChartSourceData, type: ChartType, transactionNameOther: String): DetailsChartData {
         val data = calculateStatistic(type, chartSourceData, transactionNameOther)
         val src = chartSourceData.source
         val periodStr = getPeriodString(dateUtils, src.period)
         val topLevel = src.category.parentId == null
         return DetailsChartData(
             type.title,
             data.items.sumByFloat { it.amount },
             chartSourceData.source.currency,
             periodStr,
             type.color,
             DefaultColorGenerator,
             topLevel,
             data
         )
    }

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

    fun setSelectedPeriod(period: Period) = selectedPeriod.postValue(SuccessState(period))

    fun setCategory(it: Category) {
        category.value = SuccessState(it)
    }

    val periodFormatter: (Period) -> String =
        { dateUtils.getMonthNameAndMaybeYearOfPeriod(it).capitalize() }

    private fun calculateStatistic(type: ChartType, sourceData: ChartSourceData, otherText: String): ChartList {
        return when (sourceData) {
            is TransactionsChartSourceData -> calculateTransactionsStatistic(
                otherText,
                sourceData.transactions
            )
            is StatisticalChartSourceData -> {
                val src = sourceData.source
                when (type) {
                    ChartType.EXPENSES -> calculateStatistic(
                        sourceData.statistic.filter { it.type == Statistics.Type.EXPENSES_BY_CATEGORY },
                        src.category.children,
                        src.period
                    )

                    ChartType.INCOME -> calculateStatistic(
                        sourceData.statistic.filter { it.type == Statistics.Type.INCOME_BY_CATEGORY },
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
                        abs(transaction.amount.value.floatValue())
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
}

// TODO: Remove when method merged in core
fun <T, R> ResponseState<T>.map (f: (T) -> R) : ResponseState<R> =
    when (this) {
        is SuccessState -> SuccessState(f(data))
        is LoadingState -> LoadingState
        is ErrorState -> ErrorState(errorMessage)
    }
