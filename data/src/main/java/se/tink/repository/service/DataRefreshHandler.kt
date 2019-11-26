package se.tink.repository.service

import javax.inject.Inject

interface DataRefreshHandler {
    fun refreshCategories()
    fun refreshStatistics()
}

class DataRefreshHandlerImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val statisticService: StatisticService
) : DataRefreshHandler {

    override fun refreshCategories() = categoryService.refreshCategories()

    override fun refreshStatistics() = statisticService.refreshStatistics()
}