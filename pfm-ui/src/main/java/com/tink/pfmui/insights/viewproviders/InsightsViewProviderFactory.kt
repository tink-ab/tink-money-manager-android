package com.tink.pfmui.insights.viewproviders

import se.tink.core.models.insights.InsightType
import se.tink.insights.InsightViewType
import javax.inject.Inject


class InsightViewProviderFactory @Inject constructor(
    private val supportedProviders: Set<@JvmSuppressWildcards InsightViewProvider>
) {

    fun provider(insightType: InsightType): InsightViewProvider? =
        supportedProviders.find { it.supportedInsightTypes.contains(insightType) }

    fun provider(viewType: InsightViewType): InsightViewProvider? =
        supportedProviders.find { it.viewType == viewType }

}
