package se.tink.converter.insights

import se.tink.converter.ModelConverter
import se.tink.core.models.insights.PerformedInsightAction
import se.tink.grpc.v1.rpc.SelectInsightActionRequest
import se.tink.modelConverter.AbstractConverter

class PerformedInsightActionToSelectInsightActionRequestConverter(
    val converter: ModelConverter
) : AbstractConverter<PerformedInsightAction, SelectInsightActionRequest>() {
    override fun convert(source: PerformedInsightAction): SelectInsightActionRequest {
        return SelectInsightActionRequest.newBuilder().apply {
            insightId = source.insightId
            userId = source.userId
        }.build()
    }
}
