package com.tink.pfmsdk

sealed class OverviewFeature {
    class Charts(val chartTypes: Set<ChartType>) : OverviewFeature()
    object LatestTransactions : OverviewFeature()
}

enum class ChartType {
    EXPENSES,
    INCOME
}

internal object FeatureSet {
    private val features = mutableSetOf<OverviewFeature>()

    fun initialize(featureSet: Set<OverviewFeature>) {
        features.addAll(featureSet)
    }

    fun showCharts(): Boolean = features.any { it is OverviewFeature.Charts }

    fun showLatestTransactions(): Boolean =
        features.any { it is OverviewFeature.LatestTransactions }

    fun charts() =
        features
            .firstOrNull { it is OverviewFeature.Charts }
            ?.let { (it as OverviewFeature.Charts).chartTypes }
            ?: emptySet()

    val allFeatures =
        setOf(
            OverviewFeature.Charts(setOf(ChartType.EXPENSES, ChartType.INCOME)),
            OverviewFeature.LatestTransactions
        )
}