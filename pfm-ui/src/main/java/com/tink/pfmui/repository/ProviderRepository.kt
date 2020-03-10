package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import se.tink.android.AppExecutors
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createChangeObserver
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import se.tink.core.models.provider.Provider
import se.tink.repository.service.ProviderService
import se.tink.tink_android_data.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ProviderRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val service: ProviderService
) {

    private val _providers: LiveData<List<Provider>> = object : MutableLiveData<List<Provider>>() {
        private val listener = createChangeObserver(appExecutors)

        override fun onActive() = service.subscribe(listener)
        override fun onInactive() = service.unsubscribe(listener)
    }

    private val providersWithDemoIncluded: LiveData<ErrorOrValue<List<Provider>>> = AutoFetchLiveData {
        service.listProviders(it.createMutationHandler(), true)
    }

    val providers: LiveData<List<Provider>> =
        if (BuildConfig.DEBUG) {
            providersWithDemoIncluded.map { result ->
                result.value
                    ?.filter { it.status != Provider.Status.STATUS_DISABLED }
                    ?.sorted()
                    ?: emptyList()
            }
        } else {
            _providers.map { providersList ->
                providersList
                    .filter {
                        it.status == Provider.Status.STATUS_ENABLED && it.type != Provider.Type.TYPE_TEST
                    }
                    .sorted()
            }
        }

    fun getSuggestedProviders(): LiveData<ErrorOrValue<List<Provider>>> {
        return MutableLiveData<ErrorOrValue<List<Provider>>>().also {
            service.listSuggestions(it.createMutationHandler())
        }
    }
}