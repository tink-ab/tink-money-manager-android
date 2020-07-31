package com.tink.pfmui.util.extensions

import com.tink.model.misc.Amount
import com.tink.pfmui.util.CurrencyUtils
import com.tink.pfmui.util.CurrencyUtils.CurrencyFormat


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
    return CurrencyUtils.formatCurrency(this)
}

internal fun Amount.formatCurrencyRoundWithoutSign(): String? {
    return CurrencyUtils.formatCurrencyRoundWithoutSign(this)
}

internal fun Amount.formatCurrencyExactWithoutSign(): String? {
    return CurrencyUtils.formatCurrency(this, CurrencyFormat.EXACT)
}

internal fun Amount.formatCurrencyRoundWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrency(
        this, CurrencyFormat.ROUND
                or CurrencyFormat.AMOUNT_SIGN
    )
}

internal fun Amount.formatCurrencyExactWithoutSymbol(): String? {
    return CurrencyUtils.formatCurrency(
        this,
        CurrencyFormat.EXACT
                or CurrencyFormat.AMOUNT_SIGN
    )
}

internal fun Amount.formatCurrencyExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrencyWithExplicitPositive(this)
}

internal fun Amount.formatCurrencyExactExplicitlyPositive(): String? {
    return CurrencyUtils.formatCurrency(
        this,
        CurrencyFormat.EXACT
                or CurrencyFormat.AMOUNT_SIGN
                or CurrencyFormat.SYMBOL,
        true
    )
}