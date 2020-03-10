package com.tink.pfmui.insights

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import se.tink.android.livedata.map
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightType
import com.tink.pfmui.insights.enrichment.EnrichmentDirectorFactory

abstract class AbstractInsightsViewModel internal constructor(
    insightsSource: LiveData<List<Insight>>,
    enrichmentDirectorFactory: EnrichmentDirectorFactory,
    val postProcessor: List<Insight>.() -> List<Insight>
) : ViewModel() {

    val insights: LiveData<List<Insight>> =
        enrichmentDirectorFactory
            .create(insightsSource)
            .enrichedInsights
            .map { insights ->
                insights
                    .filterNot { it.type == InsightType.UNKNOWN }
                    .postProcessor()
            }

    val hasItems: LiveData<Boolean> = insights.map { it.isNotEmpty() }

    val showEmptyState: LiveData<Boolean> = insights.map { it.isEmpty() }

    val loading: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = true
        addSource(insights) { value = false }
    }
}
