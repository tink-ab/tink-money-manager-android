package com.tink.pfmsdk.util.extensions

import com.tink.pfmsdk.util.CurrencyUtils
import se.tink.core.models.misc.Amount

fun Amount.formatCurrencyRound(): String? {
    return CurrencyUtils.formatCurrencyRound(this)
}

fun Amount.formatCurrencyRoundWithoutSignAndSymbol(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSignAndSymbol(this)
}

fun Amount.formatCurrencyRoundWithoutSign(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSign(this)
}

fun Amount.formatCurrencyRoundWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSymbol(this)
}

fun Amount.formatCurrencyExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrencyWithExplicitPositive(this)
}