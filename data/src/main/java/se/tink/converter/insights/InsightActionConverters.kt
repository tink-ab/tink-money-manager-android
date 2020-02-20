package se.tink.converter.insights

import se.tink.converter.ModelConverter
import se.tink.converter.date.toDateTime
import se.tink.converter.misc.toAmount
import se.tink.core.models.insights.InsightAction
import se.tink.core.models.insights.InsightData
import se.tink.modelConverter.AbstractConverter

internal typealias InsightActionDTO = se.tink.grpc.v1.models.InsightAction
internal typealias InsightActionDataCase = se.tink.grpc.v1.models.InsightAction.DataCase

class InsightActionDTOToInsightActionConverter(val converter: ModelConverter) :
    AbstractConverter<InsightActionDTO, InsightAction>() {
    override fun convert(source: InsightActionDTO): InsightAction =
        with(source) {
            InsightAction(
                label = label,
                data = convertData()
            )
        }
}

fun InsightActionDTO.convertData(): InsightAction.Data =
    when (dataCase) {

        InsightActionDataCase.ACKNOWLEDGE -> InsightAction.Data.Acknowledge
        InsightActionDataCase.DISMISS -> InsightAction.Data.Dismiss
        InsightActionDataCase.VIEW_BUDGET ->
            InsightAction.Data.ViewBudget(
                viewBudget.budgetId,
                viewBudget.budgetPeriodStartTime.toDateTime()
            )

        InsightActionDataCase.CREATE_TRANSFER -> InsightAction.Data.CreateTransfer(
            createTransfer.sourceAccountUri,
            createTransfer.destinationAccountUri,
            createTransfer.amount.toAmount()
        )

        InsightActionDataCase.CATEGORIZE_SINGLE_EXPENSE -> InsightAction.Data.CategorizeExpense(
            categorizeSingleExpense.transactionId
        )

        InsightActionDataCase.VIEW_TRANSACTION -> InsightAction.Data.ViewTransactions(
            viewTransaction.transactionId
        )

        InsightActionDataCase.VIEW_TRANSACTIONS -> InsightAction.Data.ViewTransactions(
            viewTransactions.transactionIdsList
        )

        InsightActionDataCase.CATEGORIZE_TRANSACTIONS -> InsightAction.Data.ViewTransactions(
            categorizeTransactions.transactionIdsList
        )

        InsightActionDataCase.VIEW_TRANSACTIONS_BY_CATEGORY -> InsightAction.Data.ViewTransactionsByCategory(
            viewTransactionsByCategory.transactionIdsByCategoryList.associate {
                it.categoryId to it.transactionIds.transactionIdsList
            }
        )

        null, InsightActionDataCase.DATA_NOT_SET -> InsightAction.Data.NoData
    }
