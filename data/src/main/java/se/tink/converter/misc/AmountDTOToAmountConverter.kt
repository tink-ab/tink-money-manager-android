package se.tink.converter.misc

import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.modelConverter.AbstractConverter

class AmountDTOToAmountConverter : AbstractConverter<CurrencyDenominatedAmount, Amount>() {
    override fun convert(source: CurrencyDenominatedAmount): Amount = source.toAmount()
}

fun CurrencyDenominatedAmount.toAmount() =
    Amount(
        ExactNumber(value.unscaledValue, value.scale),
        currencyCode
    )
