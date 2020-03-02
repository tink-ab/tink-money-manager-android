package se.tink.converter.insights

import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.models.insights.Insight
import se.tink.core.models.insights.InsightAction
import se.tink.core.models.insights.InsightData
import se.tink.core.models.insights.InsightState
import se.tink.core.models.insights.InsightType
import se.tink.modelConverter.AbstractConverter

internal typealias InsightDTO = se.tink.grpc.v1.models.Insight
internal typealias ArchivedInsightDTO = se.tink.grpc.v1.models.ArchivedInsight

object InsightConverters {
    fun forConverter(converter: ModelConverter): List<AbstractConverter<*, *>> {
        return listOf(
            InsightDTOToInsightConverter(converter),
            InsightActionDTOToInsightActionConverter(converter),
            PerformedInsightActionToSelectInsightActionRequestConverter(converter),
            ArchivedInsightDTOToInsightConverter(converter),
            InsightDataConverter(converter)
        )
    }
}

class InsightDTOToInsightConverter(val converter: ModelConverter) :
    AbstractConverter<InsightDTO, Insight>() {

    override fun convert(source: InsightDTO): Insight =
        with(source) {
            Insight(
                id = id,
                type = convertType(type),
                title = title,
                description = description,
                created = converter.map(createdTime, DateTime::class.java),
                actions = converter
                    .map(
                        actionsList,
                        InsightAction::class.java
                    )
                    .filterNot { it.data == InsightAction.Data.NoData },
                state = InsightState.Active,
                data = converter.map(data, InsightData::class.java)
            )
        }
}

class ArchivedInsightDTOToInsightConverter(val converter: ModelConverter) :
    AbstractConverter<ArchivedInsightDTO, Insight>() {
    override fun convert(source: ArchivedInsightDTO): Insight = with(source) {
        Insight(
            id = id,
            type = convertType(type),
            title = title,
            description = description,
            created = converter.map(createdTime, DateTime::class.java),
            actions = emptyList(),
            state = InsightState.Archived(
                archivedDate = converter.map(archivedTime, DateTime::class.java)
            ),
            data = converter.map(data, InsightData::class.java)
        )
    }
}

private fun convertType(type: String): InsightType =
    when (type) {
        "LEFT_TO_SPEND_HIGH" -> InsightType.LEFT_TO_SPEND_HIGH
        "LEFT_TO_SPEND_LOW" -> InsightType.LEFT_TO_SPEND_LOW
        "ACCOUNT_BALANCE_LOW" -> InsightType.ACCOUNT_BALANCE_LOW
        "INCREASE_CATEGORIZATION_LEVEL" -> InsightType.INCREASE_CATEGORIZATION_LEVEL
        "ALL_BANKS_CONNECTED" -> InsightType.ALL_BANKS_CONNECTED
        "EINVOICE" -> InsightType.EINVOICE
        "GENERIC_FRAUD" -> InsightType.GENERIC_FRAUD
        "HIGHER_INCOME_THAN_CERTAIN_PERCENTILE" -> InsightType.HIGHER_INCOME_THAN_CERTAIN_PERCENTILE
        "RATE_THIS_APP" -> InsightType.RATE_THIS_APP
        "RESIDENCE_DO_YOU_OWN_IT" -> InsightType.RESIDENCE_DO_YOU_OWN_IT
        "MONTHLY_SUMMARY" -> InsightType.MONTHLY_SUMMARY
        "WEEKLY_SUMMARY" -> InsightType.WEEKLY_SUMMARY
        "EINVOICE_OVERDUE" -> InsightType.EINVOICE_OVERDUE
        "BUDGET_OVERSPENT" -> InsightType.BUDGET_OVERSPENT
        "BUDGET_CLOSE_NEGATIVE" -> InsightType.BUDGET_CLOSE_NEGATIVE
        "BUDGET_CLOSE_POSITIVE" -> InsightType.BUDGET_CLOSE_POSITIVE
        "BUDGET_STREAK" -> InsightType.BUDGET_STREAK
        "BUDGET_SUCCESS" -> InsightType.BUDGET_SUCCESS
        "BUDGET_SUMMARY_OVERSPENT" -> InsightType.BUDGET_SUMMARY_OVERSPENT
        "BUDGET_SUMMARY_ACHIEVED" -> InsightType.BUDGET_SUMMARY_ACHIEVED
        "SINGLE_UNCATEGORIZED_EXPENSE" -> InsightType.SINGLE_EXPENSE_UNCATEGORIZED
        "LARGE_EXPENSE" -> InsightType.LARGE_EXPENSE
        "DOUBLE_CHARGE" -> InsightType.DOUBLE_CHARGE
        "WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY" -> InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY
        "WEEKLY_UNCATEGORIZED_TRANSACTIONS" -> InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS
        "MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY" -> InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY
        else -> InsightType.UNKNOWN
    }
