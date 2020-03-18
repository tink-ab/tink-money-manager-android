package se.tink.converter.insights

import com.tink.model.relations.AmountByCategory
import se.tink.converter.misc.toAmount
import se.tink.grpc.v1.models.InsightData

@JvmName("weeklySpendingToAmountByCategoryList")
internal fun List<InsightData.Data.WeeklyExpensesByCategory.CategorySpending>.toAmountByCategoryList() =
    map {
        AmountByCategory(
            amount = it.amount.toAmount(),
            categoryCode = it.categoryId
        )
    }

internal fun List<InsightData.CategorySpending>.toAmountByCategoryList() =
    map {
        AmountByCategory(
            amount = it.amount.toAmount(),
            categoryCode = it.categoryCode
        )
    }
