package com.tink.pfmui.util.extensions

import com.tink.pfmui.util.CurrencyUtils
import com.tink.model.misc.Amount

internal fun Amount.formatCurrencyRound(): String? {
    return CurrencyUtils.formatCurrencyRound(this)
}

internal fun Amount.formatCurrencyExact(): String? {
    return CurrencyUtils.formatCurrencyExact(this)
}

internal fun Amount.formatCurrencyRoundWithoutSignAndSymbol(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSignAndSymbol(this)
}

internal fun Amount.formatCurrencyExactWithoutSignAndSymbol(): String? {
    return CurrencyUtils.formatCurrencyExactWithoutSignAndSymbol(this)
}

internal fun Amount.formatCurrencyRoundWithoutSign(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSign(this)
}

internal fun Amount.formatCurrencyExactWithoutSign(): String? {
    return CurrencyUtils.formatCurrencyExactWithoutSign(this)
}

internal fun Amount.formatCurrencyRoundWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrencyExactWithoutSymbol(this)
}

internal fun Amount.formatCurrencyExactWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrencyExactWithoutSymbol(this)
}

internal fun Amount.formatCurrencyExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrencyWithExplicitPositive(this)
}

internal fun Amount.formatCurrencyExactExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrencyExactWithExplicitPositive(this)
}