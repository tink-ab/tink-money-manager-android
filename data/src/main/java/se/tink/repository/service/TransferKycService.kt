package se.tink.repository.service

import se.tink.converter.ModelConverter
import se.tink.core.models.kyc.KycAnswers
import se.tink.core.models.kyc.KycCompliance
import se.tink.grpc.v1.rpc.IsTransferKycCompliantRequest
import se.tink.grpc.v1.rpc.IsTransferKycCompliantResponse
import se.tink.grpc.v1.rpc.StoreTransferKycRequest
import se.tink.grpc.v1.rpc.StoreTransferKycResponse
import se.tink.grpc.v1.services.TransferKycServiceGrpc
import se.tink.repository.MutationHandler
import se.tink.repository.SimpleStreamObserver

interface TransferKycService {
    fun fetchKycCompliance(mutationHandler: MutationHandler<KycCompliance>)
    fun storeKyc(answers: KycAnswers, mutationHandler: MutationHandler<Unit>)
}

class TransferKycServiceImpl(
    val stub: TransferKycServiceGrpc.TransferKycServiceStub,
    val converter: ModelConverter
) : TransferKycService {

    override fun storeKyc(
        answers: KycAnswers,
        mutationHandler: MutationHandler<Unit>
    ) {
        val request = converter.map(answers, StoreTransferKycRequest::class.java)
        stub.storeKyc(
            request,
            object : SimpleStreamObserver<StoreTransferKycResponse>(mutationHandler) {
                override fun onNext(value: StoreTransferKycResponse) {
                    mutationHandler.onNext(Unit)
                }
            })
    }

    override fun fetchKycCompliance(mutationHandler: MutationHandler<KycCompliance>) {
        stub.isCompliant(
            IsTransferKycCompliantRequest.getDefaultInstance(),
            object : SimpleStreamObserver<IsTransferKycCompliantResponse>(mutationHandler) {
                override fun onNext(value: IsTransferKycCompliantResponse) {
                    mutationHandler.onNext(KycCompliance(value.provided, value.compliant))
                }
            })
    }
}
