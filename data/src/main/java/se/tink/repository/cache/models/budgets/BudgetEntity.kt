package se.tink.repository.cache.models.budgets

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.joda.time.DateTime
import se.tink.converter.typeconverters.DateTimeConverters
import se.tink.converter.typeconverters.StringListConverters
import se.tink.repository.cache.database.TableNames
import se.tink.repository.cache.models.AmountEntity

@Entity(tableName = TableNames.BUDGETS)
@TypeConverters(
    value = [
        PeriodUnitConverters::class,
        StringListConverters::class,
        DateTimeConverters::class
    ]
)
class BudgetEntity(
    @PrimaryKey var id: String,
    @Embedded(prefix = "specification_") var budgetSpecification: BudgetSpecificationEntity,
    @Embedded(prefix = "period_") var budgetPeriod: BudgetPeriodEntity
) {

    @Entity
    class BudgetPeriodEntity(
        var start: DateTime,
        var end: DateTime,
        @Embedded(prefix = "spent_amount_") var spentAmount: AmountEntity,
        @Embedded(prefix = "budget_amount_") var budgetAmount: AmountEntity
    )

    @Entity
    class BudgetSpecificationEntity(
        var id: String,
        var name: String,
        var description: String,
        @Embedded(prefix = "amount_") var amount: AmountEntity,
        @Embedded(prefix = "periodicity_") var periodicity: PeriodicityEntity,
        var archived: Boolean,
        @Embedded(prefix = "filter_") var filter: BudgetFilterEntity
    ) {

        @Entity
        class BudgetFilterEntity(
            var accounts: List<String>,
            var categories: List<String>,
            var tags: List<String>,
            var freeTextQuery: String
        )
    }

    @Entity
    class PeriodicityEntity(
        @Embedded(prefix = "one_off_") var oneOff: OneOffEntity?,
        @Embedded(prefix = "recurring_") var recurring: RecurringEntity?
    ) {

        @Entity
        class OneOffEntity(
            var start: DateTime,
            var end: DateTime
        )

        @Entity
        class RecurringEntity(
            var unit: PeriodUnit,
            var quantity: Int
        ) {
            @Entity
            enum class PeriodUnit(val dbString: String) {
                UNKNOWN("UNKNOWN"),
                WEEK("WEEK"),
                MONTH("MONTH"),
                YEAR("YEAR")
            }
        }
    }
}

class PeriodUnitConverters {
    @TypeConverter
    fun periodUnitToString(unit: BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit) =
        unit.dbString

    @TypeConverter
    fun stringToPeriodUnit(dbString: String) =
        when (dbString) {
            "WEEK" -> BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit.WEEK
            "MONTH" -> BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit.MONTH
            "YEAR" -> BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit.YEAR
            else -> BudgetEntity.PeriodicityEntity.RecurringEntity.PeriodUnit.UNKNOWN
        }
}