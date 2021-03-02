package com.tink.moneymanagerui.insights.enrichment

import androidx.lifecycle.LiveData
import com.tink.model.insights.Insight

internal interface InsightsEnricher {
    fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>>
}
