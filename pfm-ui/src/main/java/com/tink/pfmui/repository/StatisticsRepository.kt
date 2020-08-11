package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.tink.annotations.PfmScope
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.service.statistics.StatisticsQueryDescriptor
import com.tink.service.statistics.StatisticsService
import org.joda.time.DateTime
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.isInPeriod
import javax.inject.Inject

@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticsService: StatisticsService,
    userRepository: UserRepository
) {
    val statistics = userRepository.userProfile.switchMap { userProfile ->
        liveData {
            val statistics = try {
                statisticsService.query(
                    StatisticsQueryDescriptor(
                        periodMode = userProfile!!.periodMode,
                        currencyCode = userProfile.currency
                    )
                )
            } catch (error: Throwable) {
                listOf<Statistics>()
            }
            emit(statistics)
        }
    }

    private val periodMap: LiveData<Map<String, Period>> = statistics.map { statistics ->
        statistics.associate { it.period.identifier to it.period  }
    }

    val periods: LiveData<List<Period>> = Transformations.map(periodMap) {
        it?.values?.toList()
    }

    val currentPeriod: LiveData<Period> = Transformations.map(periodMap) {
        DateTime().let { now ->
            it?.values?.firstOrNull { it.isInPeriod(now) }
        }
    }
}

