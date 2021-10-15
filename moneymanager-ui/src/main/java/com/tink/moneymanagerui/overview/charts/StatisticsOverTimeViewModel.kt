package com.tink.moneymanagerui.overview.charts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tink.model.category.Category
import com.tink.model.statistics.Statistics
import com.tink.model.time.MonthPeriod
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.charts.models.PeriodBalance
import com.tink.moneymanagerui.extensions.getHalfwayPoint
import com.tink.moneymanagerui.extensions.getAbbreviatedMonthName
import com.tink.moneymanagerui.extensions.getEndOfMonth
import com.tink.moneymanagerui.extensions.getStartOfMonth
import com.tink.moneymanagerui.extensions.toPeriodIdentifier
import com.tink.moneymanagerui.repository.StatisticsRepository
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.map
import se.tink.android.repository.user.UserRepository
import se.tink.commons.categories.iconColor
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.recursiveIdList
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils
import javax.inject.Inject
import kotlin.math.abs

//TODO: Remove `toDateTime()` extension calls

internal class StatisticsOverTimeViewModel @Inject constructor(
    statisticsRepository: StatisticsRepository,
    userRepository: UserRepository,
    private val dateUtils: DateUtils,
    private val amountFormatter: AmountFormatter,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val periodSelection = MutableLiveData<PeriodSelection>().apply {
        value = PeriodSelection.SixMonths()
    }

    private val category = MutableLiveData<Category>()

    private val statistics = statisticsRepository.statistics

    private val userProfile = userRepository.userProfile

    private val allPeriodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

        fun update() {
            whenNonNull(
                statistics.value,
                category.value
            ) { statisticsList, category ->

                val statistics = when (category.type) {
                    Category.Type.INCOME -> statisticsList.filter { it.type == Statistics.Type.INCOME_BY_CATEGORY }
                    Category.Type.EXPENSE -> statisticsList.filter { it.type == Statistics.Type.EXPENSES_BY_CATEGORY }
                    else -> return
                }

                val balances = statistics
                    .filter { category.recursiveIdList.contains(it.identifier) }
                    .groupBy { it.period }
                    .map { (period, values) ->
                        PeriodBalance(
                            period,
                            values.sumByDouble { abs(it.value.value.doubleValue()) }
                        )
                    }
                    .addEmptyDataForMissingMonths()
                    .sortedByDescending { it.period?.end }
                postValue(balances)
            }
        }

        addSource(category) { update() }
        addSource(statistics) { update() }
    }

    private fun List<PeriodBalance>.addEmptyDataForMissingMonths(): List<PeriodBalance> {
        val minimumStartTime = DateTime.now().minusMonths(11)
        val backfillStartTime = minByOrNull { it.period?.start ?: Instant.MAX }
            ?.period
            ?.start
            ?.toDateTime()
            ?.takeIf { startTime ->
                startTime < minimumStartTime
            } ?: minimumStartTime
        val existingPeriodIdentifiers = map { it.period?.identifier.orEmpty() }

        val missingMonthsPeriodBalances = generateSequence(backfillStartTime) { date ->
            date.plusMonths(1)
        }.takeWhile { iteratedDate ->
            iteratedDate.isBeforeNow
        }.filter { iteratedDate ->
            !existingPeriodIdentifiers.contains(iteratedDate.toPeriodIdentifier())
        }.map { iteratedDate ->
            PeriodBalance(
                MonthPeriod(
                    iteratedDate.monthOfYear,
                    iteratedDate.year,
                    iteratedDate.toPeriodIdentifier(),
                    iteratedDate.getStartOfMonth(),
                    iteratedDate.getEndOfMonth()
                ), 0.0
            )
        }

        return this.plus(missingMonthsPeriodBalances)
    }

    private val periodBalances = MediatorLiveData<List<PeriodBalance>>().apply {

        fun update() {

            val allBalances = allPeriodBalances.value ?: return
            val periodSelection = periodSelection.value ?: return

            val filteredBalances = allBalances.filter {
                it.period?.start?.toDateTime()?.isBefore(periodSelection.end) == true
                        && it.period?.start?.toDateTime()?.isAfter(periodSelection.start) == true
            }

            postValue(filteredBalances)
        }
        addSource(allPeriodBalances) { update() }
        addSource(periodSelection) { update() }
    }

    val barChartItems = MediatorLiveData<BarChartItems>().apply {
        fun update() {
            whenNonNull(
                periodBalances.value,
                userProfile.value?.currency
            ) { list, currency ->
                val maxValue = run {
                    val computedMax = list.map { it.amount }.maxOrNull() ?: 0.0
                    if (computedMax <= 0) 1.0 else computedMax
                }

                val average = list.map { it.amount }.average() / maxValue

                val items = list.map {
                    val periodLabel = it.period?.let { period ->
                        period.getHalfwayPoint().getAbbreviatedMonthName()
                    }

                    val amountLabel = amountFormatter.format(it.amount, currency, useSymbol = true, useRounding = true)
                    val factor = it.amount / maxValue
                    val color = category.value?.iconColor() ?: 0

                    BarChartItem(amountLabel, periodLabel, factor.toFloat(), color)
                }

                postValue(BarChartItems(items, average.toFloat()))
            }
        }
        addSource(periodBalances) { update() }
        addSource(userProfile) { update() }
    }

    val average = MediatorLiveData<String>().apply {
        fun update() {
            whenNonNull(
                periodBalances.value,
                userProfile.value?.currency
            ) { periodBalances, currency ->
                val averageAmount = periodBalances.ifEmpty { null }?.map { it.amount }?.average() ?: 0.0
                val averageString = amountFormatter.format(averageAmount, currency, useSymbol = true)
                postValue(
                    context.resources.getString(
                        R.string.tink_expenses_header_description_average,
                        averageString
                    )
                )
            }
        }
        addSource(periodBalances) { update() }
        addSource(userProfile) { update() }
    }

    val sum = MediatorLiveData<String>().apply {
        fun update() {
            whenNonNull(
                periodBalances.value,
                userProfile.value?.currency
            ) { periodBalances, currency ->
                val sumAmount = periodBalances.map { it.amount }.sum()
                postValue(amountFormatter.format(sumAmount, currency, useSymbol = true))
            }
        }
        addSource(periodBalances) { update() }
        addSource(userProfile) { update() }
    }

    val periodSelectionButtonText: LiveData<String> =
        periodSelection.map { context.getString(it.labelResource) }

    fun selectPeriod(periodSelection: PeriodSelection) {
        this.periodSelection.value = periodSelection
    }

    fun selectCategory(category: Category) {
        this.category.value = category
    }
}
