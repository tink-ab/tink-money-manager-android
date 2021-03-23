package com.tink.moneymanagerui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tink.annotations.PfmScope
import javax.inject.Inject
import javax.inject.Provider

internal typealias ModelProviders = @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>

/**
 * View model provider factory allows injection into view models
 */
@PfmScope
open class ViewModelFactory @Inject constructor(
    private val providers: ModelProviders
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        providers[modelClass]?.get() as? T ?: error("Bind class $modelClass to view model")

}