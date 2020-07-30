package com.tink.pfmui.overview.charts

import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import com.tink.pfmui.overview.charts.piechart.TabPieChartFragment
import com.tink.pfmui.tracking.ScreenEvent
import com.tink.model.category.Category

internal enum class ChartType {
    EXPENSES {
        override val screenEvent = ScreenEvent.EXPENSES
        override val title: Int get() = R.string.tink_expenses_title
        override val color: Int get() = R.attr.tink_expensesColor
        override val topCategoryName: Int get() = R.string.tink_all_categories
        override val type: Category.Type get() = Category.Type.EXPENSE

        override fun create1MonthFragment(): BaseFragment = TabPieChartFragment.newInstance(this)
        override fun createOverTimeFragment(): BaseFragment = StatisticsOverTimeFragment()

    },
    INCOME {
        override val screenEvent = ScreenEvent.INCOME
        override val title: Int get() = R.string.tink_income_title
        override val color: Int get() = R.attr.tink_incomeColor
        override val topCategoryName: Int get() = R.string.tink_all_categories
        override val type: Category.Type get() = Category.Type.INCOME

        override fun create1MonthFragment(): BaseFragment = TabPieChartFragment.newInstance(this)
        override fun createOverTimeFragment(): BaseFragment = StatisticsOverTimeFragment()
    };
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
    abstract fun createOverTimeFragment(): BaseFragment
}



