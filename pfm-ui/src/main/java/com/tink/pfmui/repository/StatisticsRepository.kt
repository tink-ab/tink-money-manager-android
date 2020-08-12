package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.tink.annotations.PfmScope
import com.tink.model.statistics.Statistics
import com.tink.model.time.Period
import com.tink.service.statistics.StatisticsQueryDescriptor
import com.tink.service.statistics.StatisticsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.map
import se.tink.android.livedata.switchMap
import se.tink.android.repository.service.DataRefreshHandler
import se.tink.android.repository.service.Refreshable
import se.tink.android.repository.user.UserRepository
import se.tink.commons.extensions.isInPeriod
import javax.inject.Inject

@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticsService: StatisticsService,
    dataRefreshHandler: DataRefreshHandler,
    private val userRepository: UserRepository
) : Refreshable {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        dataRefreshHandler.registerRefreshable(this)
    }

    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    private val _statistics = MediatorLiveData<List<Statistics>>()
    val statistics: LiveData<List<Statistics>> = _statistics

    private var dataSource: LiveData<List<Statistics>>? = null

    private val periodMap: LiveData<Map<String, Period>> = statistics.map { statistics ->
        statistics.associate { it.period.identifier to it.period  }
    }

    private fun fetch(): LiveData<List<Statistics>> {
        return userRepository.userProfile.switchMap { userProfile ->
            AutoFetchLiveData<List<Statistics>> {
                scope.launch {
                    val result: List<Statistics> = try {
                        val statistics = statisticsService.query(
                            StatisticsQueryDescriptor(
                                userProfile!!.periodMode,
                                userProfile.currency
                            )
                        )
                        statistics
                    } catch (error: Throwable) {
                        emptyList()
                    }
                    it.postValue(result)
                }
            }
        }
    }

    val periods: LiveData<List<Period>> = Transformations.map(periodMap) {
        it?.values?.toList()
    }

    val currentPeriod: LiveData<Period> = Transformations.map(periodMap) {
        DateTime().let { now ->
            it?.values?.firstOrNull { it.isInPeriod(now) }
        }
    }

    override fun refresh() {
        dataSource?.let {
            _statistics.removeSource(it)
        }
        val newSource = fetch()
        _statistics.addSource(newSource) {
            _statistics.postValue(it)
        }
        dataSource = newSource
    }
}

