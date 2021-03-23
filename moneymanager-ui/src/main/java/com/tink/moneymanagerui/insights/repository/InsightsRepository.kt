package com.tink.moneymanagerui.insights.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tink.annotations.PfmScope
import com.tink.model.insights.Insight
import com.tink.service.insight.InsightService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.map
import se.tink.android.repository.TinkNetworkError
import se.tink.commons.livedata.Event
import javax.inject.Inject

@PfmScope
class InsightsRepository @Inject constructor(
    private val insightService: InsightService
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _insights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> = AutoFetchLiveData {
        scope.launch {
            val result: ErrorOrValue<List<Insight>> = try {
                val insights = insightService.listInsights()
                ErrorOrValue(value = insights)
            } catch (error: Throwable) {
                ErrorOrValue(error = TinkNetworkError(error))
            }
            it.postValue(result)
        }
    }

    val insights: LiveData<List<Insight>> = Transformations.map(_insights) {
        it?.value ?: emptyList()
    }

    private val _archivedInsights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> =
        AutoFetchLiveData {
            scope.launch {
                val result: ErrorOrValue<List<Insight>> = try {
                    val insights = insightService.listArchived()
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
