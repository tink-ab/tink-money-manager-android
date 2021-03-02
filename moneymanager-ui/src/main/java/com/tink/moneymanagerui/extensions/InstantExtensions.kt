package com.tink.moneymanagerui.extensions

import org.threeten.bp.Instant
import se.tink.commons.extensions.toDateTime

fun Instant.minusMonths(months: Int) = toDateTime().minusMonths(months).getInstant()