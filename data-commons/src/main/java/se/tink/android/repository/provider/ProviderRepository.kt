package se.tink.android.repository.provider

import androidx.lifecycle.LiveData
import com.tink.service.network.ErrorState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState
import com.tink.service.provider.ProviderService
import com.tink.service.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import se.tink.android.livedata.AutoFetchLiveData
import javax.inject.Inject

class ProviderRepository @Inject constructor(
    private val providerService: ProviderService,

    private val dispatcher: DispatcherProvider
) {

    private val _accountIdToImagesState: AutoFetchLiveData<ResponseState<Map<String, String?>>> =
        AutoFetchLiveData {
            CoroutineScope(dispatcher.io()).launch {
                val response: ResponseState<Map<String, String?>> = try {
                    val idToImageMap = providerService.listProviders(null)
                        .filter { it.images != null }
                        .associate { provider ->
                            provider.financialInstitution.id to provider.images?.icon
                        }
                    SuccessState(idToImageMap)
                } catch (t: Throwable) {
                    ErrorState(t)
                }
                it.postValue(response)
            }
        }

    val accountIdToImagesState: LiveData<ResponseState<Map<String, String?>>> = _accountIdToImagesState
}
