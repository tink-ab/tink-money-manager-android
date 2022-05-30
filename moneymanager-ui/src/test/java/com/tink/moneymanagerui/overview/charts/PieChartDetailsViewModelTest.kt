package com.tink.moneymanagerui.overview.charts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tink.model.category.Category
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.statistics.Statistics
import com.tink.model.time.MonthPeriod
import com.tink.model.time.Period
import com.tink.model.transaction.Transaction
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.overview.charts.utils.CoroutineTestRule
import com.tink.moneymanagerui.repository.StatisticsRepository
import com.tink.moneymanagerui.statistics.ChartType
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import se.tink.android.repository.transaction.TransactionRepository
import se.tink.android.repository.user.UserRepository
import java.time.Instant

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PieChartDetailsViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val categoryMockWithChildren = mockk<Category>(relaxed = true).apply {
        every { children }.returns(listOf(mockk(relaxed = true)))
    }

    @Test
    fun `tabPieChartDataState is loading of all sources are loading`() = coroutinesTestRule.testScope.runTest {
        val viewModel = getViewModel()

        val observer: Observer<ResponseState<TabPieChartData>> = mockk(relaxed = true)
        viewModel.tabPieChartDataState.observeForever(observer)

        verify { observer.onChanged(match { it is LoadingState }) }
        verify(exactly = 0) {
            observer.onChanged(
                match {
                    it is SuccessState<TabPieChartData> || it is ErrorState<TabPieChartData>
                }
            )
        }
    }

    @Test
    fun `tabPieChartDataState is error of one source is error`() = coroutinesTestRule.testScope.runTest {
        `verify tabPieChartDataState returns ErrorState`(
            getViewModel(currentPeriodStateMock = ErrorState())
        )

        `verify tabPieChartDataState returns ErrorState`(
            getViewModel(periodsStateMock = ErrorState())
        )

        `verify tabPieChartDataState returns ErrorState`(
            getViewModel(userProfileStateMock = ErrorState())
        )
    }

    private fun `verify tabPieChartDataState returns ErrorState`(viewModel: PieChartDetailsViewModel) {
        val observer: Observer<ResponseState<TabPieChartData>> = mockk(relaxed = true)
        viewModel.tabPieChartDataState.observeForever(observer)
        verify { observer.onChanged(match { it is ErrorState }) }
    }

    @Test
    fun `tabPieChartDataState has correct period`() = coroutinesTestRule.testScope.runTest {
        val periodMock = mockk<Period>(relaxed = true)
        tabPieChartDataStateReturnedSuccessThatMatches(currentPeriodStateMock = SuccessState(periodMock)) {
            it.selectedPeriod == periodMock
        }
    }

    @Test
    fun `tabPieChartDataState has correct periods list`() = coroutinesTestRule.testScope.runTest {
        val periodsListMock = mutableListOf<Period>()
        for (i: Long in 20L downTo 0L) {
            periodsListMock.add(
                mockk<Period>().apply {
                    every { start } returns Instant.ofEpochMilli(i)
                }
            )
        }

        tabPieChartDataStateReturnedSuccessThatMatches(periodsStateMock = SuccessState(periodsListMock)) {
            it.periods.size == 12 && it.periods.containsAll(periodsListMock.take(12))
        }
    }

    @Test
    fun `tabPieChartDataState has correct user profile`() = coroutinesTestRule.testScope.runTest {
        val userProfileMock = mockk<UserProfile>(relaxed = true)
        tabPieChartDataStateReturnedSuccessThatMatches(userProfileStateMock = SuccessState(userProfileMock)) { it.userProfile == userProfileMock }
    }

    @Test
    fun `tabPieChartDataState has correct category`() = coroutinesTestRule.testScope.runTest {
        val categoryMock = mockk<Category>(relaxed = true)
        tabPieChartDataStateReturnedSuccessThatMatches(categoryMock = categoryMock) { it.category == categoryMock }
    }

    private fun tabPieChartDataStateReturnedSuccessThatMatches(
        currentPeriodStateMock: ResponseState<Period> = SuccessState(mockk(relaxed = true)),
        periodsStateMock: ResponseState<List<Period>> = SuccessState(mockk(relaxed = true)),
        userProfileStateMock: ResponseState<UserProfile> = SuccessState(mockk(relaxed = true)),
        categoryMock: Category = mockk(relaxed = true),
        matcher: (TabPieChartData) -> Boolean
    ) {
        val viewModel = getViewModel(
            currentPeriodStateMock = currentPeriodStateMock,
            periodsStateMock = periodsStateMock,
            userProfileStateMock = userProfileStateMock,
            categoryMock = categoryMock
        )

        val observer: Observer<ResponseState<TabPieChartData>> = mockk(relaxed = true)
        viewModel.tabPieChartDataState.observeForever(observer)

        verify {
            observer.onChanged(
                match<SuccessState<TabPieChartData>> {
                    matcher(it.data)
                }
            )
        }
    }

    @Test
    fun `detailsChartDataState is loading of all sources are loading`() = coroutinesTestRule.testScope.runTest {
        val viewModel = getViewModel()

        val observer: Observer<ResponseState<DetailsChartData>> = mockk(relaxed = true)
        viewModel.getDetailsChartDataState(mockk(), "").observeForever(observer)

        verify { observer.onChanged(match { it is LoadingState }) }
        verify(exactly = 0) {
            observer.onChanged(
                match {
                    it is SuccessState<DetailsChartData> || it is ErrorState<DetailsChartData>
                }
            )
        }
    }

    @Test
    fun `detailsChartDataState is error of one source is error`() = coroutinesTestRule.testScope.runTest {
        `verify detailsChartDataState returns ErrorState`(
            getViewModel(currentPeriodStateMock = ErrorState())
        )

        `verify detailsChartDataState returns ErrorState`(
            getViewModel(userProfileStateMock = ErrorState())
        )

        val category = categoryMockWithChildren
        `verify detailsChartDataState returns ErrorState`(
            getViewModel(
                statisticsStateMock = ErrorState(),
                currentPeriodStateMock = SuccessState(mockk(relaxed = true)),
                userProfileStateMock = SuccessState(mockk(relaxed = true)),
                categoryMock = category
            )
        )
    }

    private fun `verify detailsChartDataState returns ErrorState`(viewModel: PieChartDetailsViewModel) {
        val observer: Observer<ResponseState<DetailsChartData>> = mockk(relaxed = true)
        viewModel.getDetailsChartDataState(mockk(), "").observeForever(observer)
        verify { observer.onChanged(match { it is ErrorState }) }
    }

    @Test
    fun `DetailsChartData has correct title color`() = coroutinesTestRule.testScope.runTest {
        detailsChartDataStateReturnedSuccessThatMatches(
            chartTypeMock = ChartType.EXPENSES,
            categoryMock = categoryMockWithChildren
        ) {
            it.title == ChartType.EXPENSES.title && it.color == ChartType.EXPENSES.color
        }
    }

    @Test
    fun `DetailsChartData statistics has correct amount`() = coroutinesTestRule.testScope.runTest {
        val currentPeriod = MonthPeriod(5, 1992, "", Instant.now(), Instant.now())

        val statisticsList = listOf(
            // Should be included
            mockk<Statistics>(relaxed = true).apply {
                every { value } returns Amount(ExactNumber(1L), "")
                every { type } returns Statistics.Type.INCOME_BY_CATEGORY
                every { period } returns currentPeriod
            },
            // Should be filtered out, wrong type
            mockk<Statistics>(relaxed = true).apply {
                every { value } returns Amount(ExactNumber(10L), "")
                every { type } returns Statistics.Type.EXPENSES_BY_CATEGORY
                every { period } returns currentPeriod
            },
            // Should be included
            mockk<Statistics>(relaxed = true).apply {
                every { value } returns Amount(ExactNumber(100L), "")
                every { type } returns Statistics.Type.INCOME_BY_CATEGORY
                every { period } returns currentPeriod
            },
            // Should be filtered out, wrong period
            mockk<Statistics>(relaxed = true).apply {
                every { value } returns Amount(ExactNumber(1000L), "")
                every { type } returns Statistics.Type.INCOME_BY_CATEGORY
            }
        )

        detailsChartDataStateReturnedSuccessThatMatches(
            chartTypeMock = ChartType.INCOME,
            statisticsStateMock = SuccessState(statisticsList),
            currentPeriodStateMock = SuccessState(currentPeriod),
            categoryMock = categoryMockWithChildren
        ) {
            it.amount == 101f
        }
    }

    @Test
    fun `DetailsChartData transactions has correct amount`() = coroutinesTestRule.testScope.runTest {
        val currentPeriod = MonthPeriod(5, 1992, "", Instant.now(), Instant.now())

        val transactionsList = listOf(
            mockk<Transaction>(relaxed = true).apply {
                every { amount } returns Amount(ExactNumber(1L), "")
            },
            mockk<Transaction>(relaxed = true).apply {
                every { amount } returns Amount(ExactNumber(10L), "")
            },
            mockk<Transaction>(relaxed = true).apply {
                every { amount } returns Amount(ExactNumber(100L), "")
            },
            mockk<Transaction>(relaxed = true).apply {
                every { amount } returns Amount(ExactNumber(1000L), "")
            }
        )

        detailsChartDataStateReturnedSuccessThatMatches(
            chartTypeMock = ChartType.INCOME,
            currentPeriodStateMock = SuccessState(currentPeriod),
            transactionsMock = transactionsList
        ) {
            it.amount == 1111f
        }
    }

    private fun detailsChartDataStateReturnedSuccessThatMatches(
        chartTypeMock: ChartType = ChartType.INCOME,
        statisticsStateMock: ResponseState<List<Statistics>> = SuccessState(mockk(relaxed = true)),
        currentPeriodStateMock: ResponseState<Period> = SuccessState(mockk(relaxed = true)),
        userProfileStateMock: ResponseState<UserProfile> = SuccessState(mockk(relaxed = true)),
        transactionsMock: List<Transaction> = mockk(relaxed = true),
        categoryMock: Category = mockk(relaxed = true),
        matcher: (DetailsChartData) -> Boolean
    ) {
        val viewModel = getViewModel(
            statisticsStateMock = statisticsStateMock,
            currentPeriodStateMock = currentPeriodStateMock,
            userProfileStateMock = userProfileStateMock,
            transactionsMock = transactionsMock,
            categoryMock = categoryMock
        )

        val observer: Observer<ResponseState<DetailsChartData>> = mockk(relaxed = true)
        viewModel.getDetailsChartDataState(chartTypeMock, "").observeForever(observer)

        verify {
            observer.onChanged(
                match<SuccessState<DetailsChartData>> {
                    matcher(it.data)
                }
            )
        }
    }

    private fun getViewModel(
        statisticsStateMock: ResponseState<List<Statistics>> = LoadingState,
        currentPeriodStateMock: ResponseState<Period> = LoadingState,
        periodsStateMock: ResponseState<List<Period>> = LoadingState,
        userProfileStateMock: ResponseState<UserProfile> = LoadingState,
        transactionsMock: List<Transaction> = listOf(),
        categoryMock: Category = mockk(relaxed = true)
    ): PieChartDetailsViewModel {
        return PieChartDetailsViewModel(
            mockk(relaxed = true),

            mockk<TransactionRepository>(relaxed = true).apply {
                every {
                    allTransactionsForCategoryAndPeriod(any(), any())
                }.answers {
                    MutableLiveData(transactionsMock)
                }
            },

            mockk<StatisticsRepository>(relaxed = true).apply {
                every {
                    statisticsState
                }.answers { MutableLiveData(statisticsStateMock) }

                every {
                    currentPeriodState
                }.answers { MutableLiveData(currentPeriodStateMock) }

                every {
                    periodsState
                }.answers { MutableLiveData(periodsStateMock) }
            },

            mockk<UserRepository>(relaxed = true).apply {
                every {
                    userProfileState
                }.answers { MutableLiveData(userProfileStateMock) }
            }
        ).apply {
            setCategory(categoryMock)
        }
    }
}
