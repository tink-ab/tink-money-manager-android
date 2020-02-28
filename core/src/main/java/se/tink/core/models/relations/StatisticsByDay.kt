package se.tink.core.models.relations

import org.joda.time.DateTime
import se.tink.core.models.misc.Amount
import java.io.Serializable

data class StatisticsByDay(
    val date: DateTime,
    val totalAmount: Amount,
    val averageAmount: Amount
) : Serializable