package com.tink.moneymanagerui.configuration

import android.content.Context
import androidx.annotation.StringRes
import com.tink.moneymanagerui.R
import se.tink.android.di.application.ApplicationScoped
import se.tink.utils.DateUtils.Companion.KEY_TODAY
import se.tink.utils.DateUtils.Companion.KEY_TOMORROW
import se.tink.utils.DateUtils.Companion.KEY_YESTERDAY
import se.tink.utils.DateUtils.Companion.getInstance
import se.tink.utils.ThreadSafeDateFormat
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DAILY
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DAILY_MONTHLY
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DAILY_MONTHLY_YEARLY
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DATE_WITH_YEAR
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DAY_OF_WEEK_COMPACT
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_DAY_OF_WEEK_FULL
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTHLY_COMPACT
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_DAY
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_DAY_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_DAY_OF_MONTH
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_DAY_OF_WEEK
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_AND_YEAR_COMPACT
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_MONTH_NAME
import se.tink.utils.ThreadSafeDateFormat.Companion.FORMATTER_YEARLY
import javax.inject.Inject

internal class I18nConfiguration @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val suitableLocaleFinder: SuitableLocaleFinder
) {

    fun initialize() {
        setupI18nConfigurationDependentSingletons()
        getInstance().formatHumanStrings = getMapWithHumanDateStrings()
        getInstance().locale = suitableLocaleFinder.findLocale()
        ThreadSafeDateFormat.dateFormatsMap = getDateFormatsMap()
    }

    private fun setupI18nConfigurationDependentSingletons() {
        getInstance(suitableLocaleFinder.findLocale())
    }

    private fun getMapWithHumanDateStrings(): Map<String, String> {
        return mapOf(
            KEY_TODAY to getString(R.string.tink_date_format_human_today),
            KEY_TOMORROW to getString(R.string.tink_date_format_human_tomorrow),
            KEY_YESTERDAY to getString(R.string.tink_date_format_human_yesterday)
        )
    }

    private fun getDateFormatsMap(): Map<String, String> {
        return mapOf(
            FORMATTER_YEARLY to getString(R.string.tink_date_formatter_yearly),
            FORMATTER_MONTHLY_COMPACT to getString(R.string.tink_date_formatter_monthly_compact),
            FORMATTER_MONTH_NAME to getString(R.string.tink_date_formatter_month_name),
            FORMATTER_DAILY to getString(R.string.tink_date_formatter_daily),
            FORMATTER_DAILY_MONTHLY to getString(R.string.tink_date_formatter_daily_monthly),
            FORMATTER_DAILY_MONTHLY_YEARLY to getString(R.string.tink_date_formatter_daily_monthly_yearly),
            FORMATTER_DAY_OF_WEEK_COMPACT to getString(R.string.tink_date_formatter_day_of_week_compact),
            FORMATTER_DAY_OF_WEEK_FULL to getString(R.string.tink_date_formatter_day_of_week_full),
            FORMATTER_MONTH_AND_DAY_OF_WEEK to getString(R.string.tink_date_formatter_month_and_day_of_week),
            FORMATTER_MONTH_AND_DAY to getString(R.string.tink_date_formatter_month_and_day),
            FORMATTER_MONTH_AND_DAY_AND_YEAR to getString(R.string.tink_date_formatter_month_and_day_and_year),
            FORMATTER_MONTH_AND_YEAR to getString(R.string.tink_date_formatter_month_and_year),
            FORMATTER_MONTH_AND_YEAR_COMPACT to getString(R.string.tink_date_formatter_month_and_year_compact),
            FORMATTER_DATE_WITH_YEAR to getString(R.string.tink_date_formatter_date_with_year),
            FORMATTER_MONTH_AND_DAY_OF_MONTH to getString(R.string.tink_date_formatter_month_and_day_of_month)
        )
    }

    private fun getString(@StringRes id: Int) = context.getString(id)
}
