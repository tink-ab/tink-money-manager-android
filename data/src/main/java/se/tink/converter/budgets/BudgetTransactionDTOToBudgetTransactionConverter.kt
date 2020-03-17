package se.tink.converter.budgets
//
//import com.google.protobuf.Timestamp
//import org.joda.time.DateTime
//import se.tink.converter.ModelConverter
//import com.tink.model.budget.BudgetTransaction
//import se.tink.core.models.misc.Amount
//import se.tink.grpc.v1.models.CurrencyDenominatedAmount
//import se.tink.modelConverter.AbstractConverter
//
//class BudgetTransactionDTOToBudgetTransactionConverter(
//    val modelConverter: ModelConverter
//) : AbstractConverter<BudgetTransactionDTO, BudgetTransaction>() {
//
//    override fun convert(source: BudgetTransactionDTO): BudgetTransaction {
//        return with(source) {
//            BudgetTransaction(
//                id = id,
//                accountId = accountId,
//                amount = amountOrDefault(),
//                dispensableAmount = dispensableAmountOrDefault(),
//                categoryCode = categoryCode,
//                description = description,
//                date = dateOrDefault()
//            )
//        }
//    }
//
//    private fun BudgetTransactionDTO.amountOrDefault(): Amount {
//        val amountDTO = amount.takeIf { hasAmount() }
//            ?: CurrencyDenominatedAmount.getDefaultInstance()
//        return modelConverter.map(amountDTO, Amount::class.java)
//    }
//
//    private fun BudgetTransactionDTO.dispensableAmountOrDefault(): Amount {
//        val amountDTO = dispensableAmount.takeIf { hasDispensableAmount() }
//            ?: CurrencyDenominatedAmount.getDefaultInstance()
//        return modelConverter.map(amountDTO, Amount::class.java)
//    }
//
//    private fun BudgetTransactionDTO.dateOrDefault(): DateTime {
//        val timestamp = date.takeIf { hasDate() } ?: Timestamp.getDefaultInstance()
//        return modelConverter.map(timestamp, DateTime::class.java)
//    }
//}
