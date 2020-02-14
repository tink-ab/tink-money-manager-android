package se.tink.core.models.relations

import se.tink.core.models.misc.Amount
import java.io.Serializable

data class AmountByCategory(val categoryCode: String, val amount: Amount) : Serializable
