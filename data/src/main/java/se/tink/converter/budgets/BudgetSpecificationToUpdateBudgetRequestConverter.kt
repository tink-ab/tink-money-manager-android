package se.tink.converter.budgets

import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.BudgetSpecification
import se.tink.core.models.budgets.OneOffPeriodicity
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.grpc.v1.rpc.UpdateBudgetRequest
import se.tink.modelConverter.AbstractConverter


class BudgetSpecificationToUpdateBudgetRequestConverter(
    val modelConverter: ModelConverter
) : AbstractConverter<BudgetSpecification, UpdateBudgetRequest>() {

    override fun convert(source: BudgetSpecification): UpdateBudgetRequest {
        return UpdateBudgetRequest.newBuilder().apply {
            budgetId = source.id
            name = source.name
            description = source.description
            amount = modelConverter.map(source.amount, CurrencyDenominatedAmount::class.java)
            filter = modelConverter.map(source.filter, FilterDTO::class.java)
            source.periodicity.let {
                when (it) {
                    is RecurringPeriodicity -> recurringPeriodicity =
                            modelConverter.map(it, RecurringPeriodicityDTO::class.java)
                    is OneOffPeriodicity -> oneOffPeriodicity =
                            modelConverter.map(it, OneOffPeriodicityDTO::class.java)
                }
            }
        }.build()
    }

    override fun getSourceClass(): Class<BudgetSpecification> = BudgetSpecification::class.java
    override fun getDestinationClass(): Class<UpdateBudgetRequest> = UpdateBudgetRequest::class.java
}
