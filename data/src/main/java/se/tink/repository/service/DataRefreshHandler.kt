package se.tink.repository.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRefreshHandler @Inject constructor(
    private val categoryService: CategoryService,
    private val statisticService: StatisticService,
    private val userConfigurationService: UserConfigurationService
) {
    private val refreshables = mutableListOf<Refreshable>()

    fun refreshCategories() = categoryService.refreshCategories()
    fun refreshStatistics() = statisticService.refreshStatistics()
    fun refreshUserConfiguration() = userConfigurationService.refreshUserConfiguration()
    fun refreshRegistered() = refreshables.forEach { it.refresh() }

    fun registerRefreshable(refreshable: Refreshable) {
        refreshables += refreshable
    }

    fun unregisterRefreshable(refreshable: Refreshable) {
        refreshables -= refreshable
    }
}

interface Refreshable {
    fun refresh()
}
