package com.tink.pfmsdk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.joda.time.DateTime
import se.tink.android.livedata.map
import se.tink.core.models.misc.Period
import se.tink.core.models.statistic.Statistic
import se.tink.core.models.statistic.StatisticTree
import se.tink.repository.ObjectChangeObserver
import se.tink.repository.service.StatisticService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepository @Inject constructor(
    private val statisticService: StatisticService
) {

    val periodMap = getStatisticsOf(Statistic.Type.TYPE_BY_CATEGORY).map {
        it.extractPeriods().filter { entry ->
            entry.value.isMonthPeriod
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

    fun getStatisticsOf(type: Statistic.Type): LiveData<StatisticTree> = object : MutableLiveData<StatisticTree>() {
        private val listener = StatisticObserver(this)

        override fun onActive() = statisticService.subscribe(listener, type)
        override fun onInactive() = statisticService.unsubscribe(listener)
    }
}

private class StatisticObserver(private val data: MutableLiveData<StatisticTree>) :
    ObjectChangeObserver<StatisticTree> {
    private val currentValue get() = data.value ?: StatisticTree()

    override fun onCreate(items: StatisticTree) = post(StatisticTree.addOrUpdate(currentValue, items))
    override fun onRead(items: StatisticTree) = post(items)
    override fun onUpdate(items: StatisticTree) = post(StatisticTree.addOrUpdate(currentValue, items))
    override fun onDelete(items: StatisticTree) = post(StatisticTree.delete(currentValue, items))

    private fun post(item: StatisticTree) = data.postValue(item)
}