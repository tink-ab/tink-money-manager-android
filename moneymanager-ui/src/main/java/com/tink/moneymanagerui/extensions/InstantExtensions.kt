package com.tink.moneymanagerui.extensions

import se.tink.commons.extensions.toDateTime
import java.time.Instant

fun Instant.minusMonths(months: Int) = toDateTime().minusMonths(months.toLong()).toInstant()
