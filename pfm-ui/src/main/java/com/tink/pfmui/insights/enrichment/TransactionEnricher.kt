package com.tink.pfmui.insights.enrichment

import androidx.lifecycle.LiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.misc.Amount
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
import se.tink.android.repository.transaction.TransactionRepository
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
                                        date = it.date,
                                        amount = it.amount
                                    )
                                }
                    }
                }
        }
    }
}

@Parcelize
internal data class TransactionViewDetails(
    val description: String,
    val date: Instant,
    val amount: Amount
) : Insight.ViewDetails
