package se.tink.converter.budgets

import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.BudgetCreateOrUpdateDescriptor
import se.tink.core.models.budgets.OneOffPeriodicity
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.grpc.v1.rpc.CreateBudgetRequest
import se.tink.grpc.v1.rpc.UpdateBudgetRequest
import se.tink.modelConverter.AbstractConverter

class BudgetCreateOrUpdateDescriptorToCreateBudgetRequestConverter(
    val modelConverter: ModelConverter
) : AbstractConverter<BudgetCreateOrUpdateDescriptor, CreateBudgetRequest>() {
    override fun convert(source: BudgetCreateOrUpdateDescriptor): CreateBudgetRequest {
        val builder = CreateBudgetRequest.newBuilder()
        builder.apply {
            name = source.name
            amount = modelConverter.map(
                source.targetAmount,
                CurrencyDenominatedAmount::class.java
            )
            filter = modelConverter.map(source.filter, FilterDTO::class.java)

            source.description?.let {
                description = it
            }

            source.periodicity.let {
                when (it) {
                    is RecurringPeriodicity -> recurringPeriodicity =
                            modelConverter.map(it, RecurringPeriodicityDTO::class.java)
                    is OneOffPeriodicity -> oneOffPeriodicity =
                            modelConverter.map(it, OneOffPeriodicityDTO::class.java)
                }
            }
        }
        return builder.build()
    }
}

class BudgetCreateOrUpdateDescriptorToUpdateBudgetRequestConverter(
    val modelConverter: ModelConverter
) : AbstractConverter<BudgetCreateOrUpdateDescriptor, UpdateBudgetRequest>() {
    override fun convert(source: BudgetCreateOrUpdateDescriptor): UpdateBudgetRequest {
        val builder = UpdateBudgetRequest.newBuilder()
        builder.apply {
            budgetId = requireNotNull(source.id)
            name = source.name
            amount = modelConverter.map(
                source.targetAmount,
                CurrencyDenominatedAmount::class.java
            )
            filter = modelConverter.map(source.filter, FilterDTO::class.java)

            source.description?.let {
                description = it
            }

            source.periodicity.let {
                when (it) {
                    is RecurringPeriodicity -> recurringPeriodicity =
                            modelConverter.map(it, RecurringPeriodicityDTO::class.java)
                    is OneOffPeriodicity -> oneOffPeriodicity =
                            modelConverter.map(it, OneOffPeriodicityDTO::class.java)
                }
            }
        }
        return builder.build()
    }
}