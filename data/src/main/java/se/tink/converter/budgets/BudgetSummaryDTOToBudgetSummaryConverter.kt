package se.tink.converter.budgets
//
//import se.tink.converter.ModelConverter
//import com.tink.model.budget.BudgetPeriod
//import com.tink.model.budget.BudgetSpecification
//import com.tink.model.budget.BudgetSummary
//import se.tink.modelConverter.AbstractConverter
//
//class BudgetSummaryDTOToBudgetSummaryConverter(
//    val converter: ModelConverter
//) : AbstractConverter<BudgetSummaryDTO, BudgetSummary>() {
//
//    override fun convert(source: BudgetSummaryDTO): BudgetSummary {
//        return BudgetSummary(
//            budgetSpecification = budgetSpecificationOrDefault(source),
//            budgetPeriod = budgetPeriodOrDefault(source)
//        )
//    }
//
//    private fun budgetSpecificationOrDefault(source: BudgetSummaryDTO): BudgetSpecification {
//        val specification = source.run { budgetSpecification.takeIf { hasBudgetSpecification() } }
//            ?: BudgetSpecificationDTO.getDefaultInstance()
//        return converter.map(specification, BudgetSpecification::class.java)
//    }
//
//    private fun budgetPeriodOrDefault(source: BudgetSummaryDTO): BudgetPeriod {
//        val period = source.run { currentBudgetPeriod.takeIf { hasCurrentBudgetPeriod() } }
//            ?: BudgetPeriodDTO.getDefaultInstance()
//        return converter.map(period, BudgetPeriod::class.java)
//    }
//}