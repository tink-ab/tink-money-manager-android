package se.tink.converter.budgets
//
//import se.tink.converter.ModelConverter
//import com.tink.model.budget.Budget
//import com.tink.model.budget.Budget.Specification.Filter
//import com.tink.model.budget.BudgetSpecification
//import com.tink.model.budget.OneOffPeriodicity
//import com.tink.model.budget.RecurringPeriodicity
//import se.tink.core.models.misc.Amount
//import se.tink.grpc.v1.models.CurrencyDenominatedAmount
//import se.tink.modelConverter.AbstractConverter
//
//class BudgetSpecificationDTOToBudgetSpecificationConverter(
//    val modelConverter: ModelConverter
//) : AbstractConverter<BudgetSpecificationDTO, BudgetSpecification>() {
//
//    override fun convert(source: BudgetSpecificationDTO): BudgetSpecification {
//        return with(source) {
//            BudgetSpecification(
//                id = this.id,
//                name = this.name,
//                description = this.description,
//                amount = amountOrDefault(),
//                archived = this.archived,
//                filter = filterOrDefault(),
//                periodicity = periodicityOrDefault()
//            )
//        }
//    }
//
//    private fun BudgetSpecificationDTO.amountOrDefault(): Amount {
//        val amountDTO = amount.takeIf { hasAmount() }
//            ?: CurrencyDenominatedAmount.getDefaultInstance()
//        return modelConverter.map(amountDTO, Amount::class.java)
//    }
//
//    private fun BudgetSpecificationDTO.filterOrDefault(): Filter {
//        val filterDTO = filter.takeIf { hasFilter() }
//            ?: FilterDTO.getDefaultInstance()
//        return modelConverter.map(filterDTO, Filter::class.java)
//    }
//
//    private fun BudgetSpecificationDTO.periodicityOrDefault(): Budget.Periodicity {
//        return this.run {
//            when {
//                hasOneOffPeriodicity() -> modelConverter.map(
//                    oneOffPeriodicity,
//                    OneOffPeriodicity::class.java
//                )
//                hasRecurringPeriodicity() -> modelConverter.map(
//                    recurringPeriodicity,
//                    RecurringPeriodicity::class.java
//                )
//                else -> null
//            }
//        } ?: error("Invalid periodicity for $this")
//    }
//}