package com.tink.pfmui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.tink.annotations.PfmScope
import se.tink.android.AppExecutors
import se.tink.android.livedata.AutoFetchLiveData
import se.tink.android.livedata.ErrorOrValue
import se.tink.android.livedata.createMutationHandler
import se.tink.android.livedata.map
import se.tink.core.models.credential.CachedCredential
import se.tink.core.models.credential.Credential
import se.tink.core.models.credential.Credential.Status.STATUS_AUTHENTICATING
import se.tink.core.models.credential.Credential.Status.STATUS_AUTHENTICATION_ERROR
import se.tink.core.models.credential.Credential.Status.STATUS_AWAITING_MOBILE_BANKID_AUTHENTICATION
import se.tink.core.models.credential.Credential.Status.STATUS_AWAITING_SUPPLEMENTAL_INFORMATION
import se.tink.core.models.credential.Credential.Status.STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION
import se.tink.core.models.credential.Credential.Status.STATUS_CREATED
import se.tink.core.models.credential.Credential.Status.STATUS_DISABLED
import se.tink.core.models.credential.Credential.Status.STATUS_PERMANENT_ERROR
import se.tink.core.models.credential.Credential.Status.STATUS_SESSION_EXPIRED
import se.tink.core.models.credential.Credential.Status.STATUS_TEMPORARY_ERROR
import se.tink.core.models.credential.Credential.Status.STATUS_UNKNOWN
import se.tink.core.models.credential.Credential.Status.STATUS_UPDATED
import se.tink.core.models.credential.Credential.Status.STATUS_UPDATING
import se.tink.core.models.misc.Field
import se.tink.repository.DefaultMutationHandler
import se.tink.repository.MutationHandler
import se.tink.repository.SimpleChangeObserver
import se.tink.repository.SimpleMutationHandler
import se.tink.repository.TinkNetworkError
import se.tink.repository.cache.LiveDataCache
import se.tink.repository.service.CredentialService
import javax.inject.Inject

@PfmScope
internal class CredentialRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val service: CredentialService,
    private val cache: LiveDataCache<List<CachedCredential>>
) {
    private val _credentials = AutoFetchLiveData<ErrorOrValue<List<Credential>>> {
        service.list(it.createMutationHandler())
    }

    init {
        _credentials.observeForever { result ->
            result?.value?.let { list ->
                appExecutors.backgroundExecutor.execute {
                    cache.clearAndInsert(
                        list.mapNotNull { credential ->
                            credential.id?.let {
                                CachedCredential(
                                    id = it,
                                    disabled = credential.status == STATUS_DISABLED
                                )
                            }
                        }
                    )
                }
            }
        }
        service.subscribe(object : SimpleChangeObserver<Credential>() {
            override fun onUpdate(items: MutableList<Credential>?) = _credentials.update()
        })
    }

    val credentials: LiveData<List<Credential>> =
        MediatorLiveData<List<Credential>>().apply {
            addSource(_credentials) { errorOrValue ->
                errorOrValue.value?.let { value = it }
            }
        }

    val disabledCredentialIds: LiveData<List<String>> = cache.read().map { list ->
        list.asSequence()
            .filter { it.disabled }
            .map { it.id }
            .toList()
    }

    fun create(credential: Credential): LiveData<CredentialOperationStatus> {
        val status = MediatorLiveData<CredentialOperationStatus>()
        service.create(credential, object : SimpleMutationHandler<Credential>() {
            override fun onNext(item: Credential) =
                status.postValue(
                    CredentialOperationSuccess(
                        trackStatus(
                            listOf(item),
                            false
                        )
                    )
                )

            override fun onError(error: TinkNetworkError?) {
                if (error?.errorCode == TinkNetworkError.ErrorCode.ALREADY_EXIST) {
                    // Try to delete existing credentials silently
                    credentials.value?.firstOrNull { it.providerName == credential.providerName }
                        ?.let {
                            service.delete(it, DefaultMutationHandler())
                        }
                    status.postValue(CredentialOperationError(null))
                } else {
                    status.postValue(CredentialOperationError(error))
                }
            }
        })
        return status
    }

    fun refresh(toRefresh: List<Credential>): LiveData<CredentialOperationStatus> {
        val status = MediatorLiveData<CredentialOperationStatus>()
        val toRefreshIds = toRefresh.map { it.id }
        service.refresh(toRefreshIds, object : SimpleMutationHandler<Void?>() {
            override fun onNext(item: Void?) =
                status.postValue(
                    CredentialOperationSuccess(
                        trackStatus(
                            toRefresh
                        )
                    )
                )

            override fun onError(error: TinkNetworkError?) =
                status.postValue(CredentialOperationError(error))
        })
        return status
    }

    // TODO: If we can refactor the way we track statuses, then we simply pass in the credentialId instead of the Credential object.
    fun update(
        credential: Credential,
        fields: List<Field>
    ): LiveData<CredentialOperationStatus> {
        val status = MediatorLiveData<CredentialOperationStatus>()
        service.update(credential.id, fields, object: SimpleMutationHandler<Credential>() {
            override fun onError(error: TinkNetworkError?) {
                status.postValue(CredentialOperationError(error))
            }
            override fun onNext(item: Credential?) {
                _credentials.update()
                status.postValue(
                    CredentialOperationSuccess(
                        trackStatus(
                            listOf(credential)
                        )
                    )
                )
            }
        })
        return status
    }

    fun delete(credential: Credential, mutationHandler: MutationHandler<Unit>) {
        service.delete(credential, object: SimpleMutationHandler<Void>() {
            override fun onError(error: TinkNetworkError?) {
                mutationHandler.onError(error)
            }
            override fun onNext(item: Void?) {
                _credentials.update()
                mutationHandler.onNext(Unit)
            }
        })
    }

    fun enableCredential(credential: Credential, mutationHandler: MutationHandler<Void>) {
        service.enableCredential(credential, mutationHandler)
    }

    fun disableCredential(credential: Credential, mutationHandler: MutationHandler<Void>) {
        service.disableCredential(credential, mutationHandler)
    }

    fun cancelSupplementalInformation(
        credential: Credential,
        mutationHandler: MutationHandler<Void>
    ) {
        service.cancelSupplementalInformation(credential, mutationHandler)
    }

    fun submitSupplemental(credential: Credential): LiveData<CredentialOperationStatus> {
        val status = MediatorLiveData<CredentialOperationStatus>()
        service.supplementInformation(credential, object : SimpleMutationHandler<Void?>() {
            override fun onNext(item: Void?) =
                status.postValue(
                    CredentialOperationSuccess(
                        trackStatus(
                            listOf(credential)
                        )
                    )
                )

            override fun onError(error: TinkNetworkError?) =
                status.postValue(CredentialOperationError(error))
        })
        return status
    }

    fun getStatusOf(credentialId: String): LiveData<CredentialStatus?> {
        return credentials.map { credentials ->
            convertStatus(credentials.firstOrNull { it.id == credentialId })
        }
    }

    private fun trackStatus(
        toTrack: List<Credential>,
        followingOnly: Boolean = true
    ): List<LiveData<CredentialStatus?>> {
        return toTrack.mapNotNull { trackedCredential ->
            trackedCredential.id?.let { trackedCredentialId ->
                credentials.map {
                    convertStatus(
                        it.firstOrNull { credential ->
                            credential.id == trackedCredentialId && (!followingOnly || trackedCredential.statusUpdated < credential.statusUpdated)
                        }
                    )
                }
            }
        }
    }

    private fun convertStatus(credential: Credential?): CredentialStatus? {
        return when (credential?.status) {
            STATUS_AWAITING_SUPPLEMENTAL_INFORMATION -> CredentialStatusInfoRequired(
                credential
            )

            STATUS_AWAITING_THIRD_PARTY_APP_AUTHENTICATION,
            STATUS_AWAITING_MOBILE_BANKID_AUTHENTICATION -> CredentialStatusAuthRequired(
                credential
            )

            STATUS_UNKNOWN,
            STATUS_DISABLED,
            STATUS_SESSION_EXPIRED,
            STATUS_TEMPORARY_ERROR,
            STATUS_AUTHENTICATION_ERROR,
            STATUS_PERMANENT_ERROR -> CredentialStatusError(credential)

            STATUS_UPDATING -> CredentialStatusUpdating(credential)
            STATUS_UPDATED -> CredentialStatusUpdated(credential)

            STATUS_CREATED,
            STATUS_AUTHENTICATING -> CredentialStatusIntermediate(
                credential
            )

            null -> null
        }
    }

    fun onThirdPartyCallback(
        state: String,
        parameters: Map<String, String>,
        mutationHandler: MutationHandler<Void>
    ) {
        service.thirdPartyCallback(
            state,
            parameters,
            mutationHandler
        )
    }
}

internal sealed class CredentialOperationStatus
internal class CredentialOperationError(val error: TinkNetworkError?) : CredentialOperationStatus()
internal class CredentialOperationSuccess(val statuses: List<LiveData<CredentialStatus?>>) :
    CredentialOperationStatus()

internal sealed class CredentialStatus(val credential: Credential)
internal class CredentialStatusError(credential: Credential) : CredentialStatus(credential)
internal class CredentialStatusUpdating(credential: Credential) : CredentialStatus(credential)
internal class CredentialStatusUpdated(credential: Credential) : CredentialStatus(credential)
internal class CredentialStatusAuthRequired(credential: Credential) : CredentialStatus(credential)
internal class CredentialStatusInfoRequired(credential: Credential) : CredentialStatus(credential)
internal class CredentialStatusIntermediate(credential: Credential) : CredentialStatus(credential)