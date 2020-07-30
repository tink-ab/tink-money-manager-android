package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.tink.annotations.PfmScope
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.service.statistics.StatisticsService
import org.joda.time.DateTime
import se.tink.android.livedata.map
import se.tink.commons.extensions.isInPeriod
import javax.inject.Inject

@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticsService: StatisticsService
) {
    val statistics = liveData {
        val statistics = try {
            statisticsService.query()
        } catch (error: Throwable) {
            listOf<Statistics>()
        }
        emit(statistics)
    }

    val periodMap: LiveData<Map<String, Period>> = statistics.map {statistics ->
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

