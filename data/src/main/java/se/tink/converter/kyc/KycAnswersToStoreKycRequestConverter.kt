package se.tink.converter.kyc

import se.tink.core.models.kyc.Answer
import se.tink.core.models.kyc.KycAnswers
import se.tink.grpc.v1.models.KycQuestion
import se.tink.grpc.v1.rpc.StoreTransferKycRequest
import se.tink.modelConverter.AbstractConverter

class KycAnswersToStoreKycRequestConverter :
    AbstractConverter<KycAnswers, StoreTransferKycRequest>() {

    override fun convert(source: KycAnswers): StoreTransferKycRequest {
        return StoreTransferKycRequest.newBuilder()
            .apply {
                this.compliant = source.compliant
                source
                    .filter { it.value != null}
                    .map { (question, answer) ->
                        KycQuestion.newBuilder()
                            .apply {
                                reference = question.reference
                                this.answer = getAnswerString(answer)
                            }
                            .build()
                    }
                    .also {
                        addAllQuestions(it)
                    }
            }
            .build()
    }


    private fun getAnswerString(answer: Any?): String {
        return when (answer){
            is Answer -> answer.toString().toLowerCase()
            is List<*> -> answer.joinToString()
            else -> answer.toString()
        }
    }

    override fun getSourceClass(): Class<KycAnswers> = KycAnswers::class.java
    override fun getDestinationClass(): Class<StoreTransferKycRequest> =
        StoreTransferKycRequest::class.java
}
