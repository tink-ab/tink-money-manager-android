package se.tink.core.models.budgets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import se.tink.core.models.misc.Amount


typealias BudgetSummary = Budget.Summary
typealias BudgetPeriod = Budget.Period
typealias BudgetTransaction = Budget.Transaction
typealias BudgetSpecification = Budget.Specification
typealias RecurringPeriodicity = Budget.Periodicity.Recurring
typealias OneOffPeriodicity = Budget.Periodicity.OneOff

class Budget {

    data class Summary(val budgetSpecification: Specification, val budgetPeriod: Period)

    data class Period(
        val start: DateTime,
        val end: DateTime,
        val spentAmount: Amount,
        val budgetAmount: Amount
    )

    data class Transaction(
        val id: String,
        val accountId: String,
        val amount: Amount,
        val dispensableAmount: Amount,
        val categoryCode: String,
        val description: String,
        val date: DateTime
    )

    data class Specification(
        val id: String,
        val name: String,
        val description: String,
        val amount: Amount,
        val periodicity: Periodicity,
        val archived: Boolean,
        val filter: Filter
    ) {

        fun updateName(name: String) = copy(name = name)
        fun updateDescription(description: String) = copy(description = description)
        fun updateAmount(amount: Amount) = copy(amount = amount)
        fun updatePeriodicity(periodicity: Periodicity) = copy(periodicity = periodicity)
        fun updateFilter(filter: Filter) = copy(filter = filter)


        @Parcelize
        data class Filter(
            val accounts: List<Account>,
            val categories: List<Category>,
            val tags: List<Tag>,
            val freeTextQuery: String
        ): Parcelable {
            @Parcelize
            data class Account(val id: String): Parcelable
            @Parcelize
            data class Category(val code: String): Parcelable
            @Parcelize
            data class Tag(val key: String): Parcelable
        }
    }

    sealed class Periodicity : Parcelable {

        @Parcelize
        data class OneOff(
            val start: DateTime,
            val end: DateTime
        ) : Periodicity()

        @Parcelize
        data class Recurring(
            val unit: PeriodUnit,
            val quantity: Int
        ) : Periodicity() {
            enum class PeriodUnit { UNKNOWN, WEEK, MONTH, YEAR }
        }
    }
}

/**
 * Descriptor for creating or updating a budget.
 * If we have an id, it means we're updating an already existing budget.
 */
data class BudgetCreateOrUpdateDescriptor(
    val id : String? = null,
    val name: String,
    val targetAmount: Amount,
    val filter: Budget.Specification.Filter,
    val description: String? = null,
    val periodicity: Budget.Periodicity
)


