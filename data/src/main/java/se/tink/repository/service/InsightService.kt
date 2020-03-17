package se.tink.repository.service
//
//import se.tink.converter.ModelConverter
//import se.tink.core.models.insights.Insight
//import se.tink.core.models.insights.PerformedInsightAction
//import se.tink.grpc.v1.rpc.ListArchivedInsightsResponse
//import se.tink.grpc.v1.rpc.ListInsightsRequest
//import se.tink.grpc.v1.rpc.ListInsightsResponse
//import se.tink.grpc.v1.rpc.SelectInsightActionRequest
//import se.tink.grpc.v1.rpc.SelectInsightActionResponse
//import se.tink.grpc.v1.services.InsightsServiceGrpc
//import se.tink.repository.MutationHandler
//import se.tink.repository.SimpleStreamObserver
//
//interface InsightService {
//    fun listInsights(mutationHandler: MutationHandler<List<Insight>>)
//    fun listArchived(mutationHandler: MutationHandler<List<Insight>>)
//    fun selectAction(
//        performedAction: PerformedInsightAction,
//        mutationHandler: MutationHandler<Unit>
//    )
//}
//
//class InsightServiceImpl(
//    val stub: InsightsServiceGrpc.InsightsServiceStub,
//    val converter: ModelConverter
//) : InsightService {
//    override fun selectAction(
//        performedAction: PerformedInsightAction,
//        mutationHandler: MutationHandler<Unit>
//    ) {
//        val request = converter.map(performedAction, SelectInsightActionRequest::class.java)
//        stub.selectAction(
//            request,
//            object : SimpleStreamObserver<SelectInsightActionResponse>(mutationHandler) {
//                override fun onNext(value: SelectInsightActionResponse?) =
//                    mutationHandler.onNext(Unit)
//            })
//    }
//
//    override fun listInsights(mutationHandler: MutationHandler<List<Insight>>) {
//        stub.listInsights(
//            ListInsightsRequest.getDefaultInstance(),
//            object : SimpleStreamObserver<ListInsightsResponse>(mutationHandler) {
//                override fun onNext(value: ListInsightsResponse) {
//                    val insights = converter.map(value.insightsList, Insight::class.java)
//                    mutationHandler.onNext(insights)
//                }
//            }
//        )
//    }
//
//    override fun listArchived(mutationHandler: MutationHandler<List<Insight>>) {
//        stub.listArchivedInsights(
//            ListInsightsRequest.getDefaultInstance(),
//            object : SimpleStreamObserver<ListArchivedInsightsResponse>(mutationHandler) {
//                override fun onNext(value: ListArchivedInsightsResponse) {
//                    val insights = converter.map(value.insightsList, Insight::class.java)
//                    mutationHandler.onNext(insights)
//                }
//            }
//        )
//    }
//}
