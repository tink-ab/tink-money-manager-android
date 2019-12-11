package com.tink.pfmsdk.overview.charts

import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ActionEvent
import com.tink.pfmsdk.overview.charts.piechart.TabPieChartFragment
import com.tink.pfmsdk.tracking.ScreenEvent
import se.tink.core.models.Category

enum class ChartType {
    EXPENSES {
        override val analytics = listOf(
            ActionEvent.EXPENSES_SHOW_PAGE_ONE_MONTH,
            ActionEvent.EXPENSES_SHOW_PAGE_SIX_MONTHS,
            ActionEvent.EXPENSES_SHOW_PAGE_TWELVE_MONTHS
        )
        override val screenEvent: ScreenEvent get() = ScreenEvent.EXPENSES
        override val title: Int get() = R.string.expenses_title
        override val color: Int get() = R.attr.tink_expensesColor
        override val topCategoryName: Int get() = R.string.all_categories
        override val type: Category.Type get() = Category.Type.TYPE_EXPENSES

        override fun create1MonthFragment(): BaseFragment = TabPieChartFragment.newInstance(this)
        override fun create6MonthFragment(): BaseFragment = TabExpensesBarChartFragment.newInstance(1)
        override fun create12MonthFragment(): BaseFragment = TabExpensesBarChartFragment.newInstance(2)
    },
    LEFT_TO_SPEND {
        override val analytics = listOf(
            ActionEvent.LEFTTOSPEND_SHOW_PAGE_ONE_MONTH,
            ActionEvent.LEFTTOSPEND_SHOW_PAGE_SIX_MONTHS,
            ActionEvent.LEFTTOSPEND_SHOW_PAGE_TWELVE_MONTHS
        )
        override val screenEvent: ScreenEvent get() = ScreenEvent.TRACKING_ERROR // To be added when UI is not hidden
        override val title: Int get() = R.string.left_to_spend_title
        override val color: Int get() = R.attr.tink_leftToSpendColor
        override val showCategoryPicker = false
        override val type: Category.Type get() = Category.Type.TYPE_UNKKNOWN

        override fun create1MonthFragment(): BaseFragment = TabLeftToSpendFragment.newInstance(0)
        override fun create6MonthFragment(): BaseFragment = TabLeftToSpendFragment.newInstance(1)
        override fun create12MonthFragment(): BaseFragment = TabLeftToSpendFragment.newInstance(2)
    },
    INCOME {
        override val analytics = listOf(
            ActionEvent.INCOME_SHOW_PAGE_ONE_MONTH,
            ActionEvent.INCOME_SHOW_PAGE_SIX_MONTHS,
            ActionEvent.INCOME_SHOW_PAGE_TWELVE_MONTHS
        )
        override val screenEvent: ScreenEvent get() = ScreenEvent.INCOME
        override val title: Int get() = R.string.income_title
        override val color: Int get() = R.attr.tink_incomeColor
        override val topCategoryName: Int get() = R.string.all_categories
        override val type: Category.Type get() = Category.Type.TYPE_INCOME

        override fun create1MonthFragment(): BaseFragment = TabPieChartFragment.newInstance(this)
        override fun create6MonthFragment(): BaseFragment = TabIncomeBarChartFragment.newInstance(1)
        override fun create12MonthFragment(): BaseFragment = TabIncomeBarChartFragment.newInstance(2)
    };

    abstract val analytics: List<ActionEvent>
    abstract val screenEvent: ScreenEvent
    @get:AttrRes
    abstract val color: Int
    @get:StringRes
    abstract val title: Int
    open val showCategoryPicker: Boolean = true
    @get:StringRes
    open val topCategoryName: Int
        get() = throw NotImplementedError()
    abstract val type: Category.Type

    abstract fun create1MonthFragment(): BaseFragment
    abstract fun create6MonthFragment(): BaseFragment
    abstract fun create12MonthFragment(): BaseFragment
}



