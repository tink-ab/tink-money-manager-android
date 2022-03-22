package com.tink.moneymanagerui.budgets.creation.specification

import android.content.Context
import android.text.method.DigitsKeyListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tink.model.budget.Budget
import com.tink.model.budget.BudgetCreateOrUpdateDescriptor
import com.tink.model.budget.BudgetSpecification
import com.tink.model.budget.OneOffPeriodicity
import com.tink.model.category.Category
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.statistics.Statistics
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.budgets.creation.BudgetCreationDataHolder
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.CUSTOM
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.MONTH
import com.tink.moneymanagerui.configuration.SuitableLocaleFinder
import com.tink.moneymanagerui.extensions.minusMonths
import com.tink.moneymanagerui.extensions.withUtcTimeRetainZone
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.util.FormattedNumberTextWatcher
import com.tink.moneymanagerui.util.extensions.formatCurrencyRound
import com.tink.service.handler.ResultHandler
import org.joda.time.DateTime
import org.threeten.bp.Instant
import se.tink.android.categories.CategoryRepository
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createResultHandler
import se.tink.android.repository.TinkNetworkError
import se.tink.android.repository.budget.BudgetsRepository
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.average
import se.tink.commons.extensions.divide
import se.tink.commons.extensions.doubleValue
import se.tink.commons.extensions.findCategoryByCode
import se.tink.commons.extensions.multiply
import se.tink.commons.extensions.recursiveIdList
import se.tink.commons.extensions.sumOrNull
import se.tink.commons.extensions.toDateTime
import se.tink.commons.extensions.whenNonNull
import se.tink.utils.DateUtils
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.math.abs

internal class BudgetCreationSpecificationViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val budgetsRepository: BudgetsRepository,
    private val dataHolder: BudgetCreationDataHolder,
    private val dateUtils: DateUtils,
    suitableLocaleFinder: SuitableLocaleFinder,
    statisticsRepository: StatisticsRepository,
    userRepository: UserRepository,
    @ApplicationScoped context: Context
) : ViewModel() {

    private val userProfile = userRepository.userProfile

    val amountTextWatcher: LiveData<FormattedNumberTextWatcher> = MediatorLiveData<FormattedNumberTextWatcher>().apply {
        fun update() {
            userProfile.value?.currency
                ?.let { currency ->
                    value = FormattedNumberTextWatcher(suitableLocaleFinder.findLocale(), currency)
                }
        }
        addSource(userProfile) { update() }
    }

    val amountInputKeyListener: LiveData<DigitsKeyListener> =
        Transformations.map(amountTextWatcher) {
            DigitsKeyListener.getInstance(it.allowedCharacters())
        }

    val amountString = MediatorLiveData<String>().also { liveData ->
        liveData.value = ""
        amountTextWatcher.value?.let { amountTextWatcher ->
            dataHolder.amount.addSource(
                Transformations.map(liveData) { amountTextWatcher.getAmountFromText(it) }
            ) {
                amount?.let { dataHolder.amount.postValue(it) }
            }
        }
        liveData.addSource(amountTextWatcher) { amountTextWatcher ->
            dataHolder.amount.addSource(
                Transformations.map(liveData) { amountTextWatcher.getAmountFromText(it) }
            ) { amount ->
                amount?.let { dataHolder.amount.postValue(it) }
            }
        }
    }

    fun updateBudgetName(newName: String) {
        dataHolder.name.postValue(newName)
    }

    fun updateBudgetAmount(amountString: String) {
        amountTextWatcher.value?.let { amountTextWatcher ->
            amountTextWatcher.getAmountFromText(amountString)?.let { amount ->
                dataHolder.amount.postValue(amount)
            }
        }
    }

    val periodValue = MutableLiveData<PeriodValue>().apply {
        value = dataHolder.periodicity.value?.toPeriodValue() ?: MONTH
    }
    val periodValueText: LiveData<String> =
        Transformations.map(periodValue) { it.toChoiceString(context) }

    val showPeriodDateField: LiveData<Boolean> = Transformations.map(periodValue) { it == CUSTOM }

    val periodStartValue = MutableLiveData<DateTime>().apply {
        (dataHolder.periodicity.value as? OneOffPeriodicity)
            ?.start
            ?.let {
                value = it.toDateTime()
            }
    }
    val periodEndValue = MutableLiveData<DateTime>().apply {
        (dataHolder.periodicity.value as? OneOffPeriodicity)
            ?.end
            ?.let {
                value = it.toDateTime()
            }
    }

    val periodStartValueFormatted: LiveData<DateTime> =
        Transformations.map(periodStartValue) {
            // We get UTC time from server, so we need to set it back to pre-fill proper values.
            it.withUtcTimeRetainZone()
        }

    val periodEndValueFormatted: LiveData<DateTime> =
        Transformations.map(periodEndValue) {
            // We get UTC time from server, so we need to set it back to pre-fill proper values.
            it.withUtcTimeRetainZone()
        }

    val periodStartText: LiveData<String> =
        Transformations.map(periodStartValueFormatted) {
            dateUtils.getDateWithYear(it)
        }
    val periodEndText: LiveData<String> =
        Transformations.map(periodEndValueFormatted) {
            dateUtils.getDateWithYear(it)
        }

    private val periodicity = MediatorLiveData<Budget.Periodicity>().apply {
        fun update() {
            periodValue.value?.let { periodValue ->
                if (periodValue == CUSTOM) {
                    value = null
                    whenNonNull(
                        periodStartValue.value,
                        periodEndValue.value
                    ) { periodStart, periodEnd ->
                        value = Budget.Periodicity.OneOff(
                            Instant.ofEpochMilli(periodStart.millis),
                            Instant.ofEpochMilli(periodEnd.millis)
                        )
                    }
                } else {
                    value = Budget.Periodicity.Recurring(periodValue.toPeriodUnit())
                }
            }
        }
        addSource(periodStartValue) { update() }
        addSource(periodEndValue) { update() }
        addSource(periodValue) { update() }
    }.also { liveData ->
        dataHolder.periodicity.addSource(liveData) { periodicity ->
            dataHolder.periodicity.postValue(periodicity)
        }
    }

    private val statistics = statisticsRepository.statistics

    private val allPeriodTotalAmountForCategory = MediatorLiveData<List<AmountForPeriodListToCategory>>().apply {
        fun update() {
            whenNonNull(
                statistics.value,
                dataHolder.selectedFilter.value?.categories,
                userProfile.value?.currency
            ) { statisticsList, selectedCategories, currency ->
                val categoryToAmountForPeriodList = selectedCategories.map { selectedCategory ->
                    val amountForPeriodList =
                        categoryRepository.categories.value
                            ?.findCategoryByCode(selectedCategory.code)
                            ?.let { category ->
                                val statistics = when (category.type) {
                                    Category.Type.INCOME -> statisticsList.filter { it.type == Statistics.Type.INCOME_BY_CATEGORY }
                                    Category.Type.EXPENSE -> statisticsList.filter { it.type == Statistics.Type.EXPENSES_BY_CATEGORY }
                                    else -> emptyList()
                                }

                                statistics
                                    .filter { category.recursiveIdList.contains(it.identifier) }
                                    .groupBy { it.period }
                                    .map { (period, values) ->
                                        val totalAmount = Amount(
                                            ExactNumber(
                                                values.sumOf {
                                                    abs(it.value.value.doubleValue())
                                                }
                                            ),
                                            currency
                                        )
                                        AmountForPeriod(
                                            period,
                                            totalAmount
                                        )
                                    }
                                    .sortedByDescending { it.period.end }
                            }
                            ?: emptyList()
                    AmountForPeriodListToCategory(selectedCategory, amountForPeriodList)
                }
                postValue(categoryToAmountForPeriodList)
            }
        }
        addSource(dataHolder.selectedFilter) { update() }
        addSource(statistics) { update() }
        addSource(userProfile) { update() }
    }

    private val averageForCategoryList = MediatorLiveData<List<Amount>>().apply {
        fun update() {
            whenNonNull(
                allPeriodTotalAmountForCategory.value,
                userProfile.value?.currency
            ) { periodBalancesList, currency ->
                val averageAmounts =
                    periodBalancesList
                        .map { (_, amountForPeriodList) ->
                            val balancesForLastTwelveMonths = amountForPeriodList.filter {
                                it.period.start in Instant.now().minusMonths(12)..Instant.now()
                            }
                            balancesForLastTwelveMonths.takeIf { it.isNotEmpty() }
                                ?.map { it.amount }
                                ?.average()
                                ?: Amount(EXACT_NUMBER_ZERO, currency)
                        }
                postValue(averageAmounts)
            }
        }
        addSource(allPeriodTotalAmountForCategory) { update() }
        addSource(userProfile) { update() }
    }

    val averageAmountText: LiveData<String> = MediatorLiveData<String>().apply {
        fun update() {
            whenNonNull(
                averageForCategoryList.value,
                periodValue.value
            ) { averageForCategoryList, periodValue ->
                averageForCategoryList
                    .map {
                        periodValue.getAverageAmount(it)
                    }
                    .sumOrNull()
                    ?.formatCurrencyRound()
                    ?.let { formattedAverageValue ->
                        value = context.getString(
                            R.string.tink_budget_edit_field_amount_average_for_period,
                            formattedAverageValue,
                            periodValue.getAveragePeriodString(context)
                        )
                    }
            }
        }
        addSource(averageForCategoryList) { update() }
        addSource(periodValue) { update() }
    }

    val showAverageAmount = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(averageAmountText) {
            value = !it.isNullOrEmpty()
        }
    }

    val defaultBudgetName: LiveData<String?> = MediatorLiveData<String>().apply {
        fun update() {
            val categoryTree = categoryRepository.categories.value
            val filter = dataHolder.selectedFilter.value

            filter?.categories
                ?.mapNotNull { categoryTree?.findCategoryByCode(it.code)?.name }
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    postValue(it.joinToString(separator = ", "))
                    return // Return function early if we found a name here.
                }

            filter?.tags?.firstOrNull()
                ?.let {
                    postValue("#${it.key}")
                    return
                }

            filter?.freeTextQuery?.let {
                postValue(it)
                return
            }
        }
        addSource(categoryRepository.categories) { update() }
        addSource(dataHolder.selectedFilter) { update() }
    }

    val selectedCategories: LiveData<List<String>> = MediatorLiveData<List<String>>().apply {
        fun update() {
            val categoryTree = categoryRepository.categories.value
            val filter = dataHolder.selectedFilter.value

            filter?.categories
                ?.mapNotNull { categoryTree?.findCategoryByCode(it.code)?.name }
                ?.also { postValue(it) }
        }
        addSource(categoryRepository.categories) { update() }
        addSource(dataHolder.selectedFilter) { update() }
    }

    val areAllRequiredValuesPresent: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        postValue(false)
        fun update() {
            whenNonNull(
                dataHolder.name.value.takeIf { !it.isNullOrBlank() },
                dataHolder.amount.value?.takeIf {
                    it.value.unscaledValue > 0
                },
                dataHolder.selectedFilter.value,
                dataHolder.periodicity.value
            ) { _, _, _, _ ->
                postValue(true)
            } ?: postValue(false)
        }

        addSource(dataHolder.amount) {
            update()
        }
        addSource(dataHolder.selectedFilter) {
            update()
        }
        addSource(dataHolder.name) {
            update()
        }
        addSource(dataHolder.periodicity) {
            update()
        }
    }

    private val _createdBudgetSpecification = MutableLiveData<BudgetSpecification>()
    val createdBudgetSpecification: LiveData<BudgetSpecification> = _createdBudgetSpecification

    private val _createdBudgetError = MutableLiveData<TinkNetworkError>()
    val createdBudgetError: LiveData<TinkNetworkError> = _createdBudgetError

    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    private val _budgetDeleteResult = MutableLiveData<ErrorOrValue<Unit>>()
    val budgetDeleteResult = MediatorLiveData<ErrorOrValue<Unit>>().apply {
        fun update() {
            _loading.postValue(false)
            value = _budgetDeleteResult.value
        }
        addSource(_budgetDeleteResult) { update() }
    }

    fun onCreateBudgetButtonClicked() {
        whenNonNull(
            dataHolder.name.value,
            dataHolder.amount.value,
            dataHolder.selectedFilter.value,
            dataHolder.periodicity.value
        ) { name, amount, filter, periodicity ->
            BudgetCreateOrUpdateDescriptor(
                id = dataHolder.id.value,
                name = name,
                targetAmount = amount,
                filter = filter,
                periodicity = periodicity
            )
        }?.let {
            if (_loading.value == true) {
                return // Belt and suspenders. The button shouldn't be clickable at this point.
            }

            _loading.value = true
            budgetsRepository.createOrUpdateBudget(
                it,
                ResultHandler(
                    { budgetSpecification ->
                        _createdBudgetSpecification.postValue(budgetSpecification)
                        _loading.postValue(false)
                    },
                    { throwable ->
                        _createdBudgetError.postValue(TinkNetworkError(throwable))
                        _loading.postValue(false)
                    }
                )
            )
        }
    }

    val amount get() = dataHolder.amount.value
    val budgetName get() = dataHolder.name.value
    val isEditing get() = dataHolder.id.value != null

    fun deleteBudget() =
        dataHolder.id.value?.let {
            _loading.postValue(true)
            budgetsRepository.archiveBudget(
                it,
                _budgetDeleteResult.createResultHandler()
            )
        }
}

fun Budget.Periodicity.toPeriodValue(): PeriodValue =
    when (this) {
        is Budget.Periodicity.OneOff -> CUSTOM
        is Budget.Periodicity.Recurring -> when (unit) {
            Budget.Periodicity.Recurring.PeriodUnit.WEEK -> PeriodValue.WEEK
            Budget.Periodicity.Recurring.PeriodUnit.MONTH -> MONTH
            Budget.Periodicity.Recurring.PeriodUnit.YEAR -> PeriodValue.YEAR
            Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN -> CUSTOM
        }
    }

enum class PeriodValue {
    WEEK, MONTH, YEAR, CUSTOM;

    fun toChoiceString(context: Context): String =
        when (this) {
            WEEK -> context.getString(R.string.tink_budget_create_field_period_weekly)
            MONTH -> context.getString(R.string.tink_budget_create_field_period_monthly)
            YEAR -> context.getString(R.string.tink_budget_create_field_period_yearly)
            CUSTOM -> context.getString(R.string.tink_budget_create_field_period_custom)
        }

    fun toPeriodUnit() =
        when (this) {
            WEEK -> Budget.Periodicity.Recurring.PeriodUnit.WEEK
            MONTH -> Budget.Periodicity.Recurring.PeriodUnit.MONTH
            YEAR -> Budget.Periodicity.Recurring.PeriodUnit.YEAR
            CUSTOM -> Budget.Periodicity.Recurring.PeriodUnit.UNKNOWN
        }

    fun getAveragePeriodString(context: Context): String =
        when (this) {
            WEEK -> context.getString(R.string.tink_budget_period_week)
            YEAR -> context.getString(R.string.tink_budget_period_year)
            MONTH,
            CUSTOM -> context.getString(R.string.tink_budget_period_month)
        }

    fun getAverageAmount(monthlyAverage: Amount) =
        when (this) {
            WEEK -> Amount(
                monthlyAverage.value.divide(ExactNumber(BigDecimal("4.3"))), // a month is approximately 4.3 weeks.
                monthlyAverage.currencyCode
            )

            YEAR -> Amount(
                monthlyAverage.value.multiply(ExactNumber(BigDecimal(12))),
                monthlyAverage.currencyCode
            )

            MONTH,
            CUSTOM -> monthlyAverage
        }
}

internal val EXACT_NUMBER_ZERO = ExactNumber(0, 0)

private data class AmountForPeriodListToCategory(val category: Budget.Specification.Filter.Category, val amountForPeriodList: List<AmountForPeriod>)
