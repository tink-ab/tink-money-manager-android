package se.tink.core.models.relations

import org.joda.time.DateTime
import com.tink.model.misc.Amount
import java.io.Serializable

data class ExpensesByDay(
    val date: DateTime,
    val totalAmount: Amount,
    val averageAmount: Amount
) : Serializable