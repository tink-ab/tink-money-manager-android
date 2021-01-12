package se.tink.android.repository.service

import com.tink.annotations.PfmScope
import javax.inject.Inject
import se.tink.android.categories.CategoryRepository

@PfmScope
class DataRefreshHandler @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userConfigurationService: UserConfigurationService
) {
    private val refreshables = mutableListOf<Refreshable>()

    fun refreshCategories() = categoryRepository.refresh()
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