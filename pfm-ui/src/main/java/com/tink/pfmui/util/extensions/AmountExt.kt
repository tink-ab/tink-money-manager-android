package com.tink.pfmui.util.extensions

import com.tink.pfmui.util.CurrencyUtils
import se.tink.core.models.misc.Amount

internal fun Amount.formatCurrencyRound(): String? {
    return CurrencyUtils.formatCurrencyRound(this)
}

internal fun Amount.formatCurrencyRoundWithoutSignAndSymbol(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSignAndSymbol(this)
}

internal fun Amount.formatCurrencyRoundWithoutSign(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSign(this)
}

internal fun Amount.formatCurrencyRoundWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSymbol(this)
}

internal fun Amount.formatCurrencyExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrencyWithExplicitPositive(this)
}