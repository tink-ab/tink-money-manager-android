package se.tink.core.models.relations

import com.tink.model.misc.Amount
import java.io.Serializable

data class AmountByCategory(val categoryCode: String, val amount: Amount) : Serializable
