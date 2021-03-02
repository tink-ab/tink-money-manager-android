package com.tink.moneymanagerui.budgets.creation.specification

import com.tink.model.misc.Amount
import com.tink.model.time.Period

internal data class AmountForPeriod(val period: Period, val amount: Amount)