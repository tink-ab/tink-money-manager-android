package se.tink.converter.misc

import se.tink.core.models.misc.Amount
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.modelConverter.AbstractConverter

class AmountDTOToAmountConverter : AbstractConverter<CurrencyDenominatedAmount, Amount>() {
    override fun convert(source: CurrencyDenominatedAmount): Amount = source.toAmount()
}

fun CurrencyDenominatedAmount.toAmount() =
    Amount(
        value.toCoreModel(),
        currencyCode
    )
