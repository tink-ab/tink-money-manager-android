package se.tink.converter.budgets

import se.tink.converter.misc.AmountToAmountEntityConverter
import se.tink.converter.misc.toCoreModel
import se.tink.converter.misc.toEntity
import se.tink.core.models.budgets.Budget
import se.tink.core.models.budgets.BudgetPeriod
import se.tink.core.models.budgets.BudgetSpecification
import se.tink.core.models.budgets.BudgetSummary
import se.tink.repository.cache.models.budgets.BudgetEntity
import se.tink.repository.cache.models.budgets.BudgetEntity.BudgetPeriodEntity
import se.tink.repository.cache.models.budgets.BudgetEntity.BudgetSpecificationEntity

private typealias CorePeriodUnit = Budget.Periodicity.Recurring.PeriodUnit
private typealias EntityPeriodUnit = BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit

fun BudgetSummary.toEntity(): BudgetEntity =
    BudgetEntity(
        id = budgetSpecification.id,
        budgetSpecification = budgetSpecification.toEntity(),
        budgetPeriod = budgetPeriod.toEntity()
    )

fun BudgetSpecification.toEntity(): BudgetSpecificationEntity =
    BudgetSpecificationEntity(
        id = id,
        name = name,
        description = description,
        amount = AmountToAmountEntityConverter().convert(amount),
        periodicity = BudgetEntity.PeriodicityEntity(
            (periodicity as? Budget.Periodicity.OneOff)?.toEntity(),
            (periodicity as? Budget.Periodicity.Recurring)?.toEntity()
        ),
        archived = archived,
        filter = filter.toEntity()
    )

fun Budget.Periodicity.OneOff.toEntity() = BudgetEntity.PeriodicityEntity.OneOffEntity(start, end)
fun Budget.Periodicity.Recurring.toEntity() = BudgetEntity.PeriodicityEntity.RecurringEntity(
    unit = unit.toEntity(),
    quantity = quantity
)

fun CorePeriodUnit.toEntity() = when (this) {
    CorePeriodUnit.UNKNOWN -> EntityPeriodUnit.UNKNOWN
    CorePeriodUnit.WEEK -> EntityPeriodUnit.WEEK
    CorePeriodUnit.MONTH -> EntityPeriodUnit.MONTH
    CorePeriodUnit.YEAR -> EntityPeriodUnit.YEAR
}

fun Budget.Specification.Filter.toEntity() =
    BudgetSpecificationEntity.BudgetFilterEntity(
        accounts = accounts.map { it.id },
        categories = categories.map { it.code },
        tags = tags.map { it.key },
        freeTextQuery = freeTextQuery
    )

fun BudgetPeriod.toEntity() =
    BudgetPeriodEntity(
        start = start,
        end = end,
        spentAmount = spentAmount.toEntity(), // TODO: Params
        budgetAmount = budgetAmount.toEntity()
    )

fun BudgetEntity.toCoreModel() =
    BudgetSummary(
        budgetSpecification = budgetSpecification.toCoreModel(),
        budgetPeriod = budgetPeriod.toCoreModel()
    )

fun BudgetSpecificationEntity.toCoreModel() =
    BudgetSpecification(
        id = id,
        name = name,
        description = description,
        amount = amount.toCoreModel(),
        periodicity = periodicity.toCoreModel(),
        archived = archived,
        filter = filter.toCoreModel()
    )

fun BudgetEntity.PeriodicityEntity.toCoreModel(): Budget.Periodicity =
    oneOff?.let {
        Budget.Periodicity.OneOff(
            start = it.start,
            end = it.end
        )
    } ?: recurring?.let {
        Budget.Periodicity.Recurring(
            unit = it.unit.toCoreModel(),
            quantity = it.quantity
        )
    } ?: throw IllegalStateException("PeriodicityEntity contained neither OneOff nor Recurring")

fun EntityPeriodUnit.toCoreModel() = when (this) {
    EntityPeriodUnit.UNKNOWN -> CorePeriodUnit.UNKNOWN
    EntityPeriodUnit.WEEK -> CorePeriodUnit.WEEK
    EntityPeriodUnit.MONTH -> CorePeriodUnit.MONTH
    EntityPeriodUnit.YEAR -> CorePeriodUnit.YEAR
}

fun BudgetSpecificationEntity.BudgetFilterEntity.toCoreModel() =
    Budget.Specification.Filter(
        accounts = accounts.map { Budget.Specification.Filter.Account(it) },
        categories = categories.map { Budget.Specification.Filter.Category(it) },
        tags = tags.map { Budget.Specification.Filter.Tag(it) },
        freeTextQuery = freeTextQuery
    )

fun BudgetPeriodEntity.toCoreModel() =
    BudgetPeriod(
        start = start,
        end = end,
        spentAmount = spentAmount.toCoreModel(),
        budgetAmount = budgetAmount.toCoreModel()
    )