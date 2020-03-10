package com.tink.pfmui.insights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import org.joda.time.DateTime
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightState
import com.tink.pfmui.insights.actionhandling.ActionEventBus
import com.tink.pfmui.insights.enrichment.EnrichmentDirectorFactory
import com.tink.pfmui.insights.repository.InsightsRepository
import se.tink.commons.livedata.Event
import se.tink.repository.TinkNetworkError
import javax.inject.Inject

private typealias InsightsListProcessor = List<Insight>.() -> List<Insight>

interface InsightsViewModel {
    val insights: LiveData<List<Insight>>
    val errors: LiveData<Event<TinkNetworkError>?>
    val hasItems: LiveData<Boolean>
    val showEmptyState: LiveData<Boolean>
    val loading: LiveData<Boolean>
    fun refresh()
}

private val sortByCreatedDescending: InsightsListProcessor = {
    sortedByDescending { it.created }
}

class CurrentInsightsViewModel @Inject internal constructor(
    private val repository: InsightsRepository,
    actionEventBus: ActionEventBus,
    enrichmentDirectorFactory: EnrichmentDirectorFactory
) : AbstractInsightsViewModel(
    ActionableInsightsLiveData(repository.insights, actionEventBus),
    enrichmentDirectorFactory,
    sortByCreatedDescending
), InsightsViewModel {
    override val errors: LiveData<Event<TinkNetworkError>?> = repository.insightErrors
    override fun refresh() = repository.refreshInsights()
}

private class ActionableInsightsLiveData(
    val source: LiveData<List<Insight>>,
    actionEventBus: ActionEventBus
) : MediatorLiveData<List<Insight>>() {

    init {
        addSource(source) { value = it }

        actionEventBus.subscribe {
            postValue(value?.filterNot { insight -> insight.id == it.insightId })
        }
    }
}

private val sortByArchivedDescending: InsightsListProcessor = {
    sortedByDescending { (it.state as? InsightState.Archived)?.archivedDate ?: DateTime(0) }
}

class ArchivedInsightsViewModel @Inject internal constructor(
    private val repository: InsightsRepository,
    enrichmentDirectorFactory: EnrichmentDirectorFactory
) : AbstractInsightsViewModel(
    repository.archivedInsights,
    enrichmentDirectorFactory,
    sortByArchivedDescending
), InsightsViewModel {
    override val errors: LiveData<Event<TinkNetworkError>?> = repository.archivedInsightsErrors
    override fun refresh() = repository.refreshArchived()
}
