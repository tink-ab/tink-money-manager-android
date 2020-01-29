package se.tink.repository.service


import se.tink.converter.ModelConverter
import se.tink.core.models.account.Account
import se.tink.extensions.getStreamingServiceObserver
import se.tink.grpc.v1.rpc.ListAccountsRequest
import se.tink.grpc.v1.rpc.ListAccountsResponse
import se.tink.grpc.v1.rpc.UpdateAccountRequest
import se.tink.grpc.v1.rpc.UpdateAccountResponse
import se.tink.grpc.v1.services.AccountServiceGrpc
import se.tink.repository.ChangeObserver
import se.tink.repository.MutationHandler
import se.tink.repository.SimpleStreamObserver
import se.tink.repository.TinkNetworkError
import se.tink.repository.cache.WritableCache
import se.tink.repository.cache.createChangeObserver
import javax.inject.Inject

class AccountServiceCachedImpl @Inject constructor(
    private val accountServiceApi: AccountServiceGrpc.AccountServiceStub,
    private val streamingService: StreamingService,
    private val converter: ModelConverter,
    writableCache: WritableCache<List<Account>>
) : AccountService {
    private val changeObservers: MutableList<ChangeObserver<Account>> = mutableListOf(
        writableCache.createChangeObserver()
    )

    init {
        startSubscribing()
    }

    private fun startSubscribing() {
        streamingService.subscribeForAccounts(changeObservers.getStreamingServiceObserver())
    }

    override fun update(account: Account, handler: MutationHandler<Account>) {
        val updateAccountRequest = converter.map(account, UpdateAccountRequest::class.java)

        accountServiceApi.updateAccount(
            updateAccountRequest,
            object : SimpleStreamObserver<UpdateAccountResponse>(handler) {
                override fun onNext(value: UpdateAccountResponse) {
                    handler.onNext(converter.map(value.account, Account::class.java))
                }
            }
        )
    }

    override fun subscribe(listener: ChangeObserver<Account>) {
        changeObservers.add(listener)
    }

    override fun unsubscribe(listener: ChangeObserver<Account>) {
        changeObservers.remove(listener)
    }

    override fun listAccounts(handler: MutationHandler<MutableList<Account>>) {
        val request = ListAccountsRequest.newBuilder().build()
        accountServiceApi.listAccounts(
            request,
            object: io.grpc.stub.StreamObserver<ListAccountsResponse> {
                override fun onError(t: Throwable) {
                    handler.onError(TinkNetworkError(t))
                }

                override fun onNext(value: ListAccountsResponse) {
                    val accountsList = mutableListOf<Account>()
                    for (account in value.accountsList) {
                        accountsList.add(converter.map(account, Account::class.java))
                    }
                    handler.onNext(accountsList)
                }

                override fun onCompleted() {
                    handler.onCompleted()
                }

            }
        )
    }
}
