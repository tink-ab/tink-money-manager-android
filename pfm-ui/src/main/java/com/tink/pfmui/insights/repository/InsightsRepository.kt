package com.tink.pfmui.insights.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import se.tink.commons.livedata.Event
import se.tink.core.models.insights.Insight
import se.tink.repository.TinkNetworkError
import se.tink.repository.service.InsightService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsightsRepository @Inject constructor(
    private val insightService: InsightService
) {
    private val _insights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> = AutoFetchLiveData {
        insightService.listInsights(it.createMutationHandler())
    }

    val insights: LiveData<List<Insight>> = Transformations.map(_insights) {
        it?.value ?: emptyList()
    }

    private val _archivedInsights: AutoFetchLiveData<ErrorOrValue<List<Insight>>> =
        AutoFetchLiveData {
            insightService.listArchived(it.createMutationHandler())
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
