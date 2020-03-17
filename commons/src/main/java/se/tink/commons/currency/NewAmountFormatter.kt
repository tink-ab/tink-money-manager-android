package se.tink.commons.currency

import com.tink.model.misc.Amount
import javax.inject.Inject


class NewAmountFormatter @Inject constructor(){

    fun format(
        amount: Amount,
        useSymbol: Boolean = true,
        useSign: Boolean = true,
        explicitlyPositive: Boolean = false
    ): String = TODO("Amount formatting implementation for new amount")

}