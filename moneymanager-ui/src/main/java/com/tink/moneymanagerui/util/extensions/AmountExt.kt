package com.tink.moneymanagerui.util.extensions

import com.tink.model.misc.Amount
import com.tink.moneymanagerui.util.CurrencyUtils
import com.tink.moneymanagerui.util.CurrencyUtils.CurrencyFormat


internal fun Amount.formatCurrencyRound(): String {
    return CurrencyUtils.formatCurrencyRound(this)
}

internal fun Amount.formatCurrencyExact(): String {
    return CurrencyUtils.formatCurrencyExact(this)
}

internal fun Amount.formatCurrencyExactIfNotIntegerWithSign(): String {
    return CurrencyUtils.formatCurrencyExactIfNotInteger(this, CurrencyFormat.AMOUNT_SIGN)
}

internal fun Amount.formatCurrencyExactIfNotIntegerWithoutSign(): String {
    return CurrencyUtils.formatCurrencyExactIfNotInteger(this)
}

internal fun Amount.formatCurrencyExactWithoutSignAndSymbol(): String {
    // Currently, we only support hiding the sign and showing the symbol
    return CurrencyUtils.formatCurrency(this, CurrencyFormat.EXACT)
}

internal fun Amount.formatCurrencyRoundWithoutSign(): String {
    return CurrencyUtils.formatCurrencyRoundWithoutSign(this)
}

internal fun Amount.formatCurrencyExactWithoutSign(): String {
    return CurrencyUtils.formatCurrency(this, CurrencyFormat.EXACT)
}

internal fun Amount.formatCurrencyRoundWithoutSymbol(): String {
    return CurrencyUtils.formatCurrency(
        this, CurrencyFormat.ROUND
                or CurrencyFormat.AMOUNT_SIGN
    )
}

internal fun Amount.formatCurrencyExactWithoutSymbol(): String {
    return CurrencyUtils.formatCurrency(
        this,
        CurrencyFormat.EXACT
                or CurrencyFormat.AMOUNT_SIGN
    )
}

internal fun Amount.formatCurrencyExplicitlyPositive(): String {
    return CurrencyUtils.formatCurrencyWithExplicitPositive(this)
}

internal fun Amount.formatCurrencyExactExplicitlyPositive(): String {
    return CurrencyUtils.formatCurrency(
        this,
        CurrencyFormat.EXACT
                or CurrencyFormat.AMOUNT_SIGN
                or CurrencyFormat.SYMBOL,
        true
    )
}