package com.tink.moneymanagerui.statistics.models

import com.tink.model.statistics.Statistics
import com.tink.model.transaction.Transaction
import com.tink.moneymanagerui.overview.charts.ChartSourceData
import com.tink.moneymanagerui.overview.charts.ChartSourceDataBase
import com.tink.moneymanagerui.overview.charts.StatisticalChartSourceData
import com.tink.moneymanagerui.overview.charts.TransactionsChartSourceData
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.network.map

internal data class ChartDetailsState(
    val sourceData: ResponseState<ChartSourceDataBase> = LoadingState,
    val statistics: ResponseState<List<Statistics>> = LoadingState,
    val transactions: ResponseState<List<Transaction>> = LoadingState
) {

    internal val overallState: ResponseState<ChartSourceData> =
        when (sourceData) {
            is SuccessState -> {
                if (sourceData.data.category.children.isNotEmpty()) {
                    statistics.map { StatisticalChartSourceData(sourceData.data, it) }
                } else {
                    transactions.map { TransactionsChartSourceData(sourceData.data, it) }
                }
            }
            is ErrorState -> ErrorState("")
            else -> LoadingState
        }
}
