package com.tink.moneymanagerui.configuration

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ProcessLifecycleOwner
import com.tink.model.user.UserProfile
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.TimezoneManager
import se.tink.android.di.application.ApplicationScoped
import se.tink.android.repository.user.UserRepository
import se.tink.utils.DateUtils.KEY_TODAY
import se.tink.utils.DateUtils.KEY_TOMORROW
import se.tink.utils.DateUtils.KEY_YESTERDAY
import se.tink.utils.DateUtils.getInstance
import se.tink.utils.ThreadSafeDateFormat
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAILY_MONTHLY_YEARLY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_DAY_OF_WEEK_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTHLY_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_DAY_OF_WEEK
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_AND_YEAR_COMPACT
import se.tink.utils.ThreadSafeDateFormat.FORMATTER_MONTH_NAME
import se.tink.utils.ThreadSafeDateFormat.setDateFormatsMap
import javax.inject.Inject

internal class I18nConfiguration @Inject constructor(
    @ApplicationScoped private val context: Context,
    private val suitableLocaleFinder: SuitableLocaleFinder,
    private val userRepository: UserRepository
) {

    fun initialize() {
        userRepository.userProfile.observe(ProcessLifecycleOwner.get(), Observer {
            it?.let(::setupI18nConfigurationDependentSingletons)
        })

        val locale = suitableLocaleFinder.findLocale()
        val timezone = TimezoneManager.defaultTimezone
        getInstance(locale, timezone).formatHumanStrings = getMapWithHumanDateStrings()
        setDateFormatsMap(getDateFormatsMap())
    }

    private fun setupI18nConfigurationDependentSingletons(item: UserProfile) {
        getInstance(suitableLocaleFinder.findLocale(), item.timeZone)
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
            ThreadSafeDateFormat.FORMATTER_YEARLY to getString(R.string.tink_date_formatter_yearly),
            FORMATTER_MONTHLY_COMPACT to getString(R.string.tink_date_formatter_monthly_compact),
            FORMATTER_MONTH_NAME to getString(R.string.tink_date_formatter_month_name),
            FORMATTER_DAILY to getString(R.string.tink_date_formatter_daily),
            FORMATTER_DAILY_MONTHLY to getString(R.string.tink_date_formatter_daily_monthly),
            FORMATTER_DAILY_MONTHLY_YEARLY to getString(R.string.tink_date_formatter_daily_monthly_yearly),
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