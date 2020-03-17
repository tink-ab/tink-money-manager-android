package com.tink.pfmui.insights.enrichment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightType
import io.reactivex.subjects.PublishSubject
import se.tink.android.livedata.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class EnrichmentDirector(
    inputStream: LiveData<List<Insight>>,
    private val enrichers: Map<EnrichmentType, InsightsEnricher>
) {

    private fun findEnrichmentType(insightType: InsightType) =
        when (insightType) {
            InsightType.BUDGET_SUMMARY_OVERSPENT,
            InsightType.BUDGET_SUMMARY_ACHIEVED -> EnrichmentType.BUDGET_SUMMARY

            InsightType.BUDGET_CLOSE_NEGATIVE,
            InsightType.BUDGET_CLOSE_POSITIVE,
            InsightType.BUDGET_SUCCESS,
            InsightType.BUDGET_OVERSPENT -> EnrichmentType.BUDGET_STATE

            InsightType.SINGLE_EXPENSE_UNCATEGORIZED -> EnrichmentType.TRANSACTION

            InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY -> EnrichmentType.CATEGORY_TREE

//            InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY -> EnrichmentType.CATEGORY_TREE

            else -> EnrichmentType.NONE
        }.also {
            TODO("Core setup")
        }

    private val enrichedStreams: List<LiveData<List<Insight>>> =
        EnrichmentType.values().map { type ->
            val streamPartition =
                inputStream.map { list -> list.filter { findEnrichmentType(it.type) == type } }

            if (type == EnrichmentType.NONE) {
                streamPartition
            } else {
                requireNotNull(enrichers[type]).enrich(streamPartition)
            }
        }

    val enrichedInsights: LiveData<List<Insight>> = MediatorLiveData<List<Insight>>().apply {

        //Prevent unnecessary updates by de-bouncing the list recalculation
        val listRecalculationEvents = PublishSubject.create<Unit>().also {
            it.debounce(50, TimeUnit.MILLISECONDS)
                .subscribe {
                    postValue(enrichedStreams.flatMap { stream -> stream.value ?: emptyList() })
                }
        }
        for (stream in enrichedStreams) {
            addSource(stream) { listRecalculationEvents.onNext(Unit) }
        }
    }
}

internal class EnrichmentDirectorFactory @Inject constructor(
    private val enrichers: @JvmSuppressWildcards Map<EnrichmentType, InsightsEnricher>
) {
    fun create(insightsInput: LiveData<List<Insight>>) =
        EnrichmentDirector(insightsInput, enrichers)
}

internal enum class EnrichmentType {
    BUDGET_STATE, BUDGET_SUMMARY, TRANSACTION, CATEGORY_TREE, NONE
}
