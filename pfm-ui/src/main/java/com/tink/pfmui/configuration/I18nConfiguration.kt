package com.tink.pfmui.configuration

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
import com.tink.pfmui.R
import com.tink.pfmui.TimezoneManager
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.repository.user.UserRepository
import se.tink.core.models.user.UserConfiguration
import se.tink.utils.DateUtils.KEY_TODAY
import se.tink.utils.DateUtils.KEY_TOMORROW
import se.tink.utils.DateUtils.KEY_YESTERDAY
import se.tink.utils.DateUtils.getInstance
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_PRETTY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_FILENAME_SAFE
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_INTEGER_DATE
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_INTEGER_DATE_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_LOGGING
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MILLISECONDS_WITH_TIMEZONE
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MINUTES
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTHLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTHLY_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTHLY_ONLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_WEEK
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_NAME
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_SECONDS
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_SECONDS_DASHES
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_SECONDS_T
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_SECONDS_WITH_TIMEZONE
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_TIME_MILLIS
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_WEEKLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_WEEK_YEARLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_YEARLY
import se.tink.utils.ThreadSafeDateFormat.setDateFormatsMap
import javax.inject.Inject

internal class I18nConfiguration @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val userRepository: UserRepository
) {

    fun initialize() {
        userRepository.userConfiguration.observe(ProcessLifecycleOwner.get(), Observer {
            it?.let(::setupI18nConfigurationDependentSingletons)
        })

        val locale = SuitableLocaleFinder().findLocale()
        val timezone = TimezoneManager.defaultTimezone
        getInstance(locale, timezone).formatHumanStrings = getMapWithHumanDateStrings()
        setDateFormatsMap(getDateFormatsMap())
    }

    private fun setupI18nConfigurationDependentSingletons(item: UserConfiguration) {
        item.i18nConfiguration?.timezoneCode?.let {
            TimezoneManager.defaultTimezone = it
        }
        getInstance(SuitableLocaleFinder().findLocale(), item.i18nConfiguration?.timezoneCode)
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
            FORMATTER_WEEK_YEARLY to getString(R.string.tink_date_formatter_week_yearly),
            FORMATTER_MONTHLY to getString(R.string.tink_date_formatter_monthly),
            FORMATTER_INTEGER_DATE to getString(R.string.tink_date_formatter_integer_date),
            FORMATTER_INTEGER_DATE_COMPACT to getString(R.string.tink_date_formatter_integer_date_compact),
            FORMATTER_MONTHLY_ONLY to getString(R.string.tink_date_formatter_monthly_only),
            FORMATTER_MONTHLY_COMPACT to getString(R.string.tink_date_formatter_monthly_compact),
            FORMATTER_MONTH_NAME to getString(R.string.tink_date_formatter_month_name),
            FORMATTER_WEEKLY to getString(R.string.tink_date_formatter_weekly),
            FORMATTER_DAILY to getString(R.string.tink_date_formatter_daily),
            FORMATTER_DAILY_COMPACT to getString(R.string.tink_date_formatter_daily_compact),
            FORMATTER_DAILY_PRETTY to getString(R.string.tink_date_formatter_daily_pretty),
            FORMATTER_DAILY_MONTHLY to getString(R.string.tink_date_formatter_daily_monthly),
            FORMATTER_DAILY_MONTHLY_YEARLY to getString(R.string.tink_date_formatter_daily_monthly_yearly),
            FORMATTER_MINUTES to getString(R.string.tink_date_formatter_minutes),
            FORMATTER_SECONDS to getString(R.string.tink_date_formatter_seconds),
            FORMATTER_SECONDS_DASHES to getString(R.string.tink_date_formatter_seconds_dashes),
            FORMATTER_SECONDS_T to getString(R.string.tink_date_formatter_seconds_t),
            FORMATTER_SECONDS_WITH_TIMEZONE to getString(R.string.tink_date_formatter_seconds_with_timezone),
            FORMATTER_MILLISECONDS_WITH_TIMEZONE to getString(R.string.tink_date_formatter_milliseconds_with_timezone),
            FORMATTER_TIME_MILLIS to getString(R.string.tink_date_formatter_time_millis),
            FORMATTER_FILENAME_SAFE to getString(R.string.tink_date_formatter_filename_safe),
            FORMATTER_LOGGING to getString(R.string.tink_date_formatter_logging),
            FORMATTER_DAY_OF_WEEK to getString(R.string.tink_date_formatter_day_of_week),
            FORMATTER_DAY_OF_WEEK_COMPACT to getString(R.string.tink_date_formatter_day_of_week_compact),
            FORMATTER_MONTH_AND_DAY_OF_WEEK to getString(R.string.tink_date_formatter_month_and_day_of_week),
            FORMATTER_MONTH_AND_DAY to getString(R.string.tink_date_formatter_month_and_day),
            FORMATTER_MONTH_AND_DAY_AND_YEAR to getString(R.string.tink_date_formatter_month_and_day_and_year),
            FORMATTER_MONTH_AND_YEAR to getString(R.string.tink_date_formatter_month_and_year),
            FORMATTER_MONTH_AND_YEAR_COMPACT to getString(R.string.tink_date_formatter_month_and_year_compact)
        )
    }

    private fun getString(@StringRes id: Int) = context.getString(id)
}