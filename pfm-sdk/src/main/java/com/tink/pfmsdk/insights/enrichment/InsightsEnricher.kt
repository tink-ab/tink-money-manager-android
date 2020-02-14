package com.tink.pfmsdk.insights.enrichment

import androidx.lifecycle.LiveData
import se.tink.core.models.insights.Insight

internal interface InsightsEnricher {
    fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>>
}
