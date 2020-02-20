package se.tink.converter.insights

import se.tink.converter.misc.toAmount
import se.tink.core.models.relations.AmountByCategory
import se.tink.grpc.v1.models.InsightData

fun List<InsightData.Data.WeeklyExpensesByCategory.CategorySpending>.toAmountByCategoryList() =
    map {
        AmountByCategory(
            amount = it.amount.toAmount(),
            categoryCode = it.categoryId
        )
    }
