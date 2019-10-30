package se.tink.converter.budgets

import com.google.protobuf.Timestamp
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import se.tink.converter.ModelConverterImpl
import se.tink.core.models.budgets.Budget.Periodicity.Recurring.PeriodUnit
import se.tink.core.models.budgets.Budget.Specification.Filter
import se.tink.core.models.budgets.BudgetCreateOrUpdateDescriptor
import se.tink.core.models.budgets.BudgetSpecification
import se.tink.core.models.budgets.BudgetTransaction
import se.tink.core.models.budgets.OneOffPeriodicity
import se.tink.core.models.budgets.RecurringPeriodicity
import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.grpc.v1.rpc.CreateBudgetRequest
import se.tink.grpc.v1.models.ExactNumber as ExactNumberDTO

internal class BudgetConverterTest {

    private val converter = ModelConverterImpl()
    private val amountDTOTen = CurrencyDenominatedAmount.newBuilder()
        .setValue(ExactNumberDTO.newBuilder().setUnscaledValue(10))
        .setCurrencyCode("SEK").build()
    private val amountTen = Amount(ExactNumber.TEN, "SEK")

    @Test
    fun `simple creation request mapping does not map null fields`() {
        val input = BudgetCreateOrUpdateDescriptor(
            name = "test budget name",
            targetAmount = Amount(ExactNumber.TEN, "SEK"),
            filter = Filter(
                accounts = listOf(), tags = listOf(), categories = listOf(), freeTextQuery = ""
            ),
            periodicity = OneOffPeriodicity(
                DateTime.now().minusMonths(2),
                DateTime.now().plusMonths(2)
            )
        )
        val result = converter.map(input, CreateBudgetRequest::class.java)
        assertThat(result.description).isNullOrEmpty()
    }

    @Test
    fun `complex creation request mapping does map all fields`() {
        val input = BudgetCreateOrUpdateDescriptor(
            name = "test budget name",
            targetAmount = Amount(ExactNumber.TEN, "SEK"),
            filter = Filter(
                accounts = listOf(), tags = listOf(), categories = listOf(), freeTextQuery = ""
            ),
            periodicity = RecurringPeriodicity(
                unit = PeriodUnit.MONTH,
                quantity = 4
            ),
            description = "test description"
        )
        val result = converter.map(input, CreateBudgetRequest::class.java)
        assertThat(result.name).isEqualTo("test budget name")
        assertThat(result.hasAmount()).isTrue()
        assertThat(result.hasFilter()).isTrue()
        assertThat(result.hasRecurringPeriodicity()).isTrue()
        assertThat(result.hasOneOffPeriodicity()).isFalse()
        assertThat(result.description).isEqualTo("test description")
    }

    @Test
    fun `creation request periodicities are mutually exclusive`() {
        val input = BudgetCreateOrUpdateDescriptor(
            name = "test budget name",
            targetAmount = Amount(ExactNumber.TEN, "SEK"),
            filter = Filter(listOf(), listOf(), listOf(), ""),
            periodicity = RecurringPeriodicity(
                unit = PeriodUnit.MONTH,
                quantity = 4
            ),
            description = "test description"
        )
        val result = converter.map(input, CreateBudgetRequest::class.java)
        assertThat(result.hasRecurringPeriodicity()).isTrue()
        assertThat(result.hasOneOffPeriodicity()).isFalse()
        val input2 = input.copy(
            periodicity = OneOffPeriodicity(
                DateTime.now().minusMonths(2),
                DateTime.now().plusMonths(2)
            )
        )
        val result2 = converter.map(input2, CreateBudgetRequest::class.java)
        assertThat(result2.hasRecurringPeriodicity()).isFalse()
        assertThat(result2.hasOneOffPeriodicity()).isTrue()
    }

    @Test
    fun `filter to filterDTO mapping yields the correct lists`() {
        val input = Filter(
            listOf(Filter.Account("123")),
            listOf(Filter.Category("expenses:restaurant")),
            listOf(Filter.Tag("vacation")),
            "free query"
        )
        val result = converter.map(input, FilterDTO::class.java)
        assertThat(result.accountsList.size).isEqualTo(1)
        assertThat(result.accountsList[0].id).isEqualTo("123")
        assertThat(result.categoriesList.size).isEqualTo(1)
        assertThat(result.categoriesList[0].code).isEqualTo("expenses:restaurant")
        assertThat(result.tagsList.size).isEqualTo(1)
        assertThat(result.tagsList[0].key).isEqualTo("vacation")
    }

    @Test
    fun `default budgetTransaction conversion does not crash`() {
        converter.map(BudgetTransactionDTO.getDefaultInstance(), BudgetTransaction::class.java)
    }

    @Test
    fun `budgetTransactionDTO to budgetTransaction conversion is valid`() {
        val input = BudgetTransactionDTO.newBuilder()
            .setId("testId123")
            .setAccountId("testAccount123")
            .setAmount(amountDTOTen)
            .setDispensableAmount(amountDTOTen)
            .setCategoryCode("expenses:restaurant")
            .setDate(Timestamp.newBuilder().setSeconds(5).build())
            .setDescription("test description")
            .build()
        val result = converter.map(input, BudgetTransaction::class.java)
        assertThat(result.id).isEqualTo("testId123")
        assertThat(result.accountId).isEqualTo("testAccount123")
        assertThat(result.amount).isEqualTo(amountTen)
        assertThat(result.dispensableAmount).isEqualTo(amountTen)
        assertThat(result.categoryCode).isEqualTo("expenses:restaurant")
        assertThat(result.description).isEqualTo("test description")
        assertThat(result.date.secondOfMinute).isEqualTo(5)
    }

    @Test
    fun `budget specification DTO without periodicity throws error when converting`() {
        val input = BudgetSpecificationDTO.getDefaultInstance()
        Assertions.assertThrows(IllegalStateException::class.java) {
            converter.map(input, BudgetSpecification::class.java)
        }
    }

    @Test
    fun `valid specification DTO mapping does not crash`() {
        val builderWithoutPeriodicity = BudgetSpecificationDTO.newBuilder().apply {
            amount = CurrencyDenominatedAmount.newBuilder().apply {
                value = ExactNumberDTO.getDefaultInstance()
                currencyCode = "SEK"
            }.build()
            filter = FilterDTO.newBuilder().setFreeTextQuery("some query").build()
        }

        val input0 = builderWithoutPeriodicity.apply {
            oneOffPeriodicity = OneOffPeriodicityDTO.newBuilder().apply {
                start = Timestamp.getDefaultInstance()
                end = Timestamp.getDefaultInstance()
            }.build()
        }.build()

        converter.map(input0, BudgetSpecification::class.java)

        val input1 = builderWithoutPeriodicity.apply {
            recurringPeriodicity = RecurringPeriodicityDTO.newBuilder().apply {
                periodUnit = PeriodUnitDTO.PERIOD_UNIT_MONTH
                periodUnitValue = 1
            }.build()
        }.build()

        converter.map(input1, BudgetSpecification::class.java)
    }
}
