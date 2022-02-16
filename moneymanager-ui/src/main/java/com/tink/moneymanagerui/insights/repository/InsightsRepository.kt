package com.tink.moneymanagerui.insights.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightAction
import com.tink.model.insights.InsightData
import com.tink.model.insights.InsightState
import com.tink.model.insights.InsightType
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.relations.ExpensesByDay
import com.tink.model.time.YearWeek
import com.tink.moneymanagerui.insights.viewproviders.InsightViewProviderFactory
import com.tink.service.insight.InsightService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.map
import se.tink.android.repository.TinkNetworkError
import se.tink.commons.livedata.Event
import javax.inject.Inject

class InsightsRepository @Inject constructor(
    private val insightService: InsightService,
    private val dispatcher: DispatcherProvider,
    private val insightProviderFactory: InsightViewProviderFactory
) {
    private val _insights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> = AutoFetchLiveData {
        CoroutineScope(dispatcher.io()).launch {
            val result: ErrorOrValue<List<Insight>> = try {
                val insights = insightService.listInsights()
                    .filter { insightProviderFactory.provider(it.type) != null }
                ErrorOrValue(value = insights)
            } catch (error: Throwable) {
                ErrorOrValue(error = TinkNetworkError(error))
            }
            it.postValue(result)
        }
    }

    val insights: LiveData<List<Insight>> = Transformations.map(_insights) {
        listOf(
            Insight(
                "1",
                InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
                "Weekly summary",
                "My weekly summary",
                Instant.now(),
                listOf(
                    InsightAction(
                        "View details",
                        InsightAction.Type.ACKNOWLEDGE,
                        InsightAction.Data.Acknowledge
                    ),
                    InsightAction(
                        "Archive",
                        InsightAction.Type.ACKNOWLEDGE,
                        InsightAction.Data.Acknowledge
                    )
                ),
                InsightState.Active,
                InsightData.WeeklyExpensesByDayData(
                    YearWeek(2022, 6),
                    listOf(
                        ExpensesByDay(
                            LocalDate.now(),
                            Amount(ExactNumber(10, 0), "SEK"),
                            Amount(ExactNumber(20, 0), "SEK")
                        ),
                        ExpensesByDay(
                            LocalDate.now(),
                            Amount(ExactNumber(20, 0), "SEK"),
                            Amount(ExactNumber(40, 0), "SEK")
                        ),
                        ExpensesByDay(
                            LocalDate.now(),
                            Amount(ExactNumber(30, 0), "SEK"),
                            Amount(ExactNumber(60, 0), "SEK")
                        ),
                        ExpensesByDay(
                            LocalDate.now(),
                            Amount(ExactNumber(40, 0), "SEK"),
                            Amount(ExactNumber(80, 0), "SEK")
                        ),
                        ExpensesByDay(
                            LocalDate.now(),
                            Amount(ExactNumber(50, 0), "SEK"),
                            Amount(ExactNumber(100, 0), "SEK")
                        ),
                    )
                )
            )
        )
//        it?.value ?: emptyList()
    }

    private val _archivedInsights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                val result: ErrorOrValue<List<Insight>> = try {
                    val insights = insightService.listArchived()
                        .filter { insightProviderFactory.provider(it.type) != null }
                    ErrorOrValue(value = insights)
                } catch (error: Throwable) {
                    ErrorOrValue(error = TinkNetworkError(error))
                }
                it.postValue(result)
            }
        }

    val archivedInsights: LiveData<List<Insight>> = Transformations.map(_archivedInsights) {
        it?.value ?: emptyList()
    }

    val insightErrors: LiveData<Event<TinkNetworkError>?> = _insights.map { errorOrValue ->
        errorOrValue?.error?.let { Event(it) }
    }

    val archivedInsightsErrors: LiveData<Event<TinkNetworkError>?> =
        _archivedInsights.map { errorOrValue ->
            errorOrValue?.error?.let { Event(it) }
        }

    fun refreshInsights() = _insights.update()
    fun refreshArchived() = _archivedInsights.update()
}
