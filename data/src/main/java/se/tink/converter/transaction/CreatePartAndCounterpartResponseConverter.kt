package se.tink.converter.transaction

import se.tink.converter.ModelConverter
import se.tink.core.models.transaction.CreatePartAndCounterpartResponse
import se.tink.core.models.transaction.Transaction
import se.tink.modelConverter.AbstractConverter


class CreatePartAndCounterpartResponseConverter(val converter: ModelConverter) :
        AbstractConverter<se.tink.grpc.v1.rpc.CreatePartAndCounterpartResponse, CreatePartAndCounterpartResponse>() {
    override fun getSourceClass(): Class<se.tink.grpc.v1.rpc.CreatePartAndCounterpartResponse> {
        return se.tink.grpc.v1.rpc.CreatePartAndCounterpartResponse::class.java
    }

    override fun getDestinationClass(): Class<CreatePartAndCounterpartResponse> {
        return CreatePartAndCounterpartResponse::class.java
    }

    override fun convert(source: se.tink.grpc.v1.rpc.CreatePartAndCounterpartResponse): CreatePartAndCounterpartResponse {

        val transaction = converter.map(source.transaction, Transaction::class.java)
        val counterpartTransaction = converter.map(source.counterpartTransaction, Transaction::class.java)

        return CreatePartAndCounterpartResponse(transaction, counterpartTransaction)
    }
}
