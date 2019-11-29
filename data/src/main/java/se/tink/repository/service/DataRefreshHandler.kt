package se.tink.repository.service

import javax.inject.Inject

class DataRefreshHandler @Inject constructor(
    private val categoryService: CategoryService,
    private val statisticService: StatisticService,
    private val userConfigurationService: UserConfigurationService
) {
    fun refreshCategories() = categoryService.refreshCategories()
    fun refreshStatistics() = statisticService.refreshStatistics()
    fun refreshUserConfiguration() = userConfigurationService.refreshUserConfiguration()
}
