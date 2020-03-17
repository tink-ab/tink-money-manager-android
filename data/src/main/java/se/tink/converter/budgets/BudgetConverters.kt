package se.tink.converter.budgets

//import se.tink.converter.ModelConverter
//import se.tink.modelConverter.AbstractConverter


//class BudgetConverters private constructor(
//    private val converters: List<AbstractConverter<out Any, out Any>>
//) : List<AbstractConverter<out Any, out Any>> by converters {
//
//    companion object {
//        @JvmStatic
//        fun forConverter(modelConverter: ModelConverter) = BudgetConverters(
//            mutableListOf(
//                BudgetSummaryDTOToBudgetSummaryConverter(modelConverter),
//                BudgetPeriodDTOToBudgetPeriodConverter(modelConverter),
//                BudgetSpecificationDTOToBudgetSpecificationConverter(modelConverter),
//                FilterDTOToFilterConverter(),
//                BudgetCreateOrUpdateDescriptorToCreateBudgetRequestConverter(modelConverter),
//                BudgetCreateOrUpdateDescriptorToUpdateBudgetRequestConverter(modelConverter),
//                FilterToFilterDTOConverter(),
//                RecurringPeriodicityToRecurringPeriodicityDTOConverter(),
//                BudgetSpecificationToUpdateBudgetRequestConverter(modelConverter),
//                BudgetTransactionDTOToBudgetTransactionConverter(modelConverter),
//                RecurringPeriodicityDTOToPeriodicityConverter(),
//                RecurringPeriodicityToRecurringPeriodicityDTOConverter(),
//                OneOffPeriodicityDTOToPeriodicityConverter(modelConverter),
//                OneOffPeriodicityToOneOffPeriodicityDTOCoverter(modelConverter)
//            )
//        )
//    }
//
//}