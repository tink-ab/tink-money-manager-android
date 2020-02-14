package com.tink.pfmsdk.insights.enrichment

import androidx.lifecycle.LiveData
import org.joda.time.DateTime
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightData
import se.tink.core.models.misc.Amount
import javax.inject.Inject

class TransactionEnricher @Inject constructor(
    private val transactionRepository: TransactionRepository
) : InsightsEnricher {


    override fun enrich(input: LiveData<List<Insight>>): LiveData<List<Insight>> {

        return input.switchMap { insightList ->

            val directory =
                insightList
                    .mapNotNull { insight ->
                        (insight.data as? InsightData.UncategorizedTransactionData)?.let { data ->
                            insight.id to data.transactionId
                        }
                    }
                    .toMap()

            transactionRepository.fromIdsAsLiveData(directory.values.toList())
                .map { transactionList ->
                    insightList.onEach { insight ->
                        insight.viewDetails =
                            transactionList
                                .find { it.id == directory[insight.id] }
                                ?.let {
                                    TransactionViewDetails(
                                        description = it.description,
                                        date = it.timestamp,
                                        amount = it.amount
                                    )
                                }
                    }
                }
        }
    }
}

internal class TransactionViewDetails(
    val description: String,
    val date: DateTime,
    val amount: Amount
) : Insight.ViewDetails
