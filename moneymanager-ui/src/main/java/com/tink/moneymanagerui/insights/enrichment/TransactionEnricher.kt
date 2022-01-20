package com.tink.moneymanagerui.insights.enrichment

import androidx.lifecycle.LiveData
import com.tink.model.insights.Insight
import com.tink.model.insights.InsightData
import com.tink.model.misc.Amount
import com.tink.model.transaction.Transaction
import kotlinx.android.parcel.Parcelize
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
            val transactionIds = getTransactionIdsFromInsights(insightList)

            transactionRepository.fromIdsAsLiveData(transactionIds)
                .map { transactionList ->
                    insightList.onEach { insight ->
                        when (val data = insight.data) {
                            is InsightData.UncategorizedTransactionData -> {
                                addUncategorizedTransactionsData(transactionList, data, insight)
                            }
                            is InsightData.WeeklyUncategorizedTransactionsData -> {
                                addWeeklyUncategorizedTransactionsData(transactionList, data, insight)
                            }
                            else -> {}
                        }
                    }
                }
        }
    }

    private fun getTransactionIdsFromInsights(insightList: List<Insight>): List<String> {
        val transactionIds = insightList.flatMap { insight ->
            return@flatMap when (val data = insight.data) {
                is InsightData.UncategorizedTransactionData ->
                    listOf(data.transactionId)

                is InsightData.WeeklyUncategorizedTransactionsData -> {
                    data.transactionIds
                }
                else -> null
            }
        }
        return transactionIds
    }

    private fun addUncategorizedTransactionsData(
        transactionList: List<Transaction>,
        data: InsightData.UncategorizedTransactionData,
        insight: Insight
    ) {
        val transaction = transactionList.first { it.id == data.transactionId }
        insight.viewDetails = TransactionViewDetails(
            description = transaction.description,
            date = transaction.date,
            amount = transaction.amount
        )
    }

    private fun addWeeklyUncategorizedTransactionsData(
        transactionList: List<Transaction>,
        data: InsightData.WeeklyUncategorizedTransactionsData,
        insight: Insight
    ) {
        val transactions = transactionList.filter { transaction -> data.transactionIds.contains(transaction.id) }

        val descriptions = transactions.map { it.description }

        val instants = transactions.map { it.date }

        val numberOfTransactions = transactions.size

        insight.viewDetails = TransactionsViewDetails(
            descriptions, instants, numberOfTransactions
        )
    }
}

@Parcelize
internal data class TransactionViewDetails(
    val description: String,
    val date: Instant,
    val amount: Amount
) : Insight.ViewDetails

@Parcelize
internal data class TransactionsViewDetails(
    val descriptions: List<String>,
    val dates: List<Instant>,
    val numberOfTransactions: Int
) : Insight.ViewDetails
