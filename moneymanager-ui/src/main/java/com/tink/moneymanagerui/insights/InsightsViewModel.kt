package com.tink.moneymanagerui.insights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightState
import com.tink.moneymanagerui.insights.actionhandling.ActionEventBus
import com.tink.moneymanagerui.insights.enrichment.EnrichmentDirectorFactory
import com.tink.moneymanagerui.insights.repository.InsightsRepository
import org.threeten.bp.Instant
import se.tink.android.repository.TinkNetworkError
import se.tink.commons.livedata.Event
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
),
    InsightsViewModel {
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
    sortedByDescending { (it.state as? InsightState.Archived)?.archivedDate ?: Instant.MIN }
}

class ArchivedInsightsViewModel @Inject internal constructor(
    private val repository: InsightsRepository,
    enrichmentDirectorFactory: EnrichmentDirectorFactory
) : AbstractInsightsViewModel(
    repository.archivedInsights,
    enrichmentDirectorFactory,
    sortByArchivedDescending
),
    InsightsViewModel {
    override val errors: LiveData<Event<TinkNetworkError>?> = repository.archivedInsightsErrors
    override fun refresh() = repository.refreshArchived()
}
