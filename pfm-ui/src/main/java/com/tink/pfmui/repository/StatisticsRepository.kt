package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.tink.annotations.PfmScope
import com.tink.model.time.MonthPeriod
import org.joda.time.DateTime
import se.tink.android.livedata.map
import com.tink.model.time.Period
import se.tink.commons.extensions.isInPeriod
import se.tink.core.models.statistic.StatisticTree
import se.tink.repository.ObjectChangeObserver
import se.tink.repository.service.StatisticService
import javax.inject.Inject

@PfmScope
internal class StatisticsRepository @Inject constructor(
    private val statisticService: StatisticService
) {

    val periodMap = getStatistics().map {
        it.extractPeriods().filter { entry ->
            entry.value is MonthPeriod
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

    fun getStatistics(): LiveData<StatisticTree> = object : MutableLiveData<StatisticTree>() {
        private val listener = StatisticObserver(this)

        override fun onActive() = statisticService.subscribe(listener)
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