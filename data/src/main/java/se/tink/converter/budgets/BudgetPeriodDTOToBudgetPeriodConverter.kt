package se.tink.converter.budgets

import com.google.protobuf.Timestamp
import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.BudgetPeriod
import se.tink.core.models.misc.Amount
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.modelConverter.AbstractConverter

class BudgetPeriodDTOToBudgetPeriodConverter(
    val modelConverter: ModelConverter
) : AbstractConverter<BudgetPeriodDTO, BudgetPeriod>() {

    override fun convert(source: BudgetPeriodDTO): BudgetPeriod {
        return with(source) {
            BudgetPeriod(
                start = startOrDefault(),
                end = endOrDefault(),
                budgetAmount = budgetAmountOrDefault(),
                spentAmount = spentAmountOrDefault()
            )
        }
    }

    private fun BudgetPeriodDTO?.startOrDefault(): DateTime {
        val timestamp = this?.run { start.takeIf { hasStart() } } ?: Timestamp.getDefaultInstance()
        return modelConverter.map(timestamp, DateTime::class.java)
    }

    private fun BudgetPeriodDTO?.endOrDefault(): DateTime {
        val timestamp = this?.run { end.takeIf { hasEnd() } } ?: Timestamp.getDefaultInstance()
        return modelConverter.map(timestamp, DateTime::class.java)
    }

    private fun BudgetPeriodDTO?.budgetAmountOrDefault(): Amount {
        val amountDTO = this?.run { budgetAmount.takeIf { hasBudgetAmount() } }
                ?: CurrencyDenominatedAmount.getDefaultInstance()
        return modelConverter.map(amountDTO, Amount::class.java)
    }

    private fun BudgetPeriodDTO?.spentAmountOrDefault(): Amount {
        val amountDTO = this?.run { spentAmount.takeIf { hasSpentAmount() } }
                ?: CurrencyDenominatedAmount.getDefaultInstance()
        return modelConverter.map(amountDTO, Amount::class.java)
    }
}