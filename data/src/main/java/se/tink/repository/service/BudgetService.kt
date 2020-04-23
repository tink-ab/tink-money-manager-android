package se.tink.repository.service

import com.google.protobuf.Timestamp
import org.joda.time.DateTime
import se.tink.converter.ModelConverter
import se.tink.core.models.budgets.BudgetCreateOrUpdateDescriptor
import se.tink.core.models.budgets.BudgetPeriod
import se.tink.core.models.budgets.BudgetSpecification
import se.tink.core.models.budgets.BudgetSummary
import se.tink.core.models.budgets.BudgetTransaction
import se.tink.grpc.v1.rpc.CreateBudgetRequest
import se.tink.grpc.v1.rpc.CreateBudgetResponse
import se.tink.grpc.v1.rpc.DeleteBudgetRequest
import se.tink.grpc.v1.rpc.DeleteBudgetResponse
import se.tink.grpc.v1.rpc.GetBudgetPeriodDetailsRequest
import se.tink.grpc.v1.rpc.GetBudgetPeriodDetailsResponse
import se.tink.grpc.v1.rpc.GetBudgetTransactionsRequest
import se.tink.grpc.v1.rpc.GetBudgetTransactionsResponse
import se.tink.grpc.v1.rpc.ListBudgetsRequest
import se.tink.grpc.v1.rpc.ListBudgetsResponse
import se.tink.grpc.v1.rpc.UpdateBudgetRequest
import se.tink.grpc.v1.rpc.UpdateBudgetResponse
import se.tink.grpc.v1.services.BudgetServiceGrpc
import se.tink.repository.MutationHandler
import se.tink.repository.SimpleStreamObserver


interface BudgetService {
    fun createBudget(
        descriptor: BudgetCreateOrUpdateDescriptor,
        mutationHandler: MutationHandler<BudgetSpecification>
    )

    fun updateBudget(
        descriptor: BudgetCreateOrUpdateDescriptor,
        mutationHandler: MutationHandler<BudgetSpecification>
    )

    fun deleteBudget(
        id: String,
        mutationHandler: MutationHandler<Unit>
    )

    fun listBudgets(mutationHandler: MutationHandler<List<BudgetSummary>>)

    fun listTransactionsForBudget(
        budgetId: String,
        start: DateTime,
        end: DateTime,
        mutationHandler: MutationHandler<List<BudgetTransaction>>
    )

    fun budgetPeriodDetails(
        budgetId: String,
        start: DateTime,
        end: DateTime,
        mutationHandler: MutationHandler<Pair<BudgetSpecification, List<BudgetPeriod>>>
    )
}


class BudgetServiceImpl(
    val stub: BudgetServiceGrpc.BudgetServiceStub,
    val modelConverter: ModelConverter
) : BudgetService {

    override fun createBudget(
        descriptor: BudgetCreateOrUpdateDescriptor,
        mutationHandler: MutationHandler<BudgetSpecification>
    ) {
        val request = modelConverter.map(descriptor, CreateBudgetRequest::class.java)
        stub.createBudget(
            request,
            object : SimpleStreamObserver<CreateBudgetResponse>(mutationHandler) {
                override fun onNext(value: CreateBudgetResponse) {
                    val result = modelConverter.map(
                        value.budgetSpecification,
                        BudgetSpecification::class.java
                    )
                    mutationHandler.onNext(result)
                }
            })
    }

    override fun updateBudget(
        descriptor: BudgetCreateOrUpdateDescriptor,
        mutationHandler: MutationHandler<BudgetSpecification>
    ) {

        val request = modelConverter.map(descriptor, UpdateBudgetRequest::class.java)

        stub.updateBudget(
            request,
            object : SimpleStreamObserver<UpdateBudgetResponse>(mutationHandler) {
                override fun onNext(value: UpdateBudgetResponse) {
                    val updatedBudgetSpecification = modelConverter.map(
                        value.budgetSpecification,
                        BudgetSpecification::class.java
                    )
                    mutationHandler.onNext(updatedBudgetSpecification)
                }
            })
    }

    override fun deleteBudget(id: String, mutationHandler: MutationHandler<Unit>) {
        val request = DeleteBudgetRequest.newBuilder().setBudgetId(id).build()
        stub.deleteBudget(
            request,
            object : SimpleStreamObserver<DeleteBudgetResponse>(mutationHandler) {
                override fun onNext(value: DeleteBudgetResponse) = mutationHandler.onNext(Unit)
            })
    }

    override fun listBudgets(mutationHandler: MutationHandler<List<BudgetSummary>>) {
        val request = ListBudgetsRequest.getDefaultInstance()
        stub.listBudgets(
            request,
            object : SimpleStreamObserver<ListBudgetsResponse>(mutationHandler) {
                override fun onNext(value: ListBudgetsResponse) {
                    val result =
                        modelConverter.map(value.budgetSummariesList, BudgetSummary::class.java)
                    mutationHandler.onNext(result)
                }
            })
    }

    override fun listTransactionsForBudget(
        budgetId: String,
        start: DateTime,
        end: DateTime,
        mutationHandler: MutationHandler<List<BudgetTransaction>>
    ) {
        val request = GetBudgetTransactionsRequest.newBuilder()
            .setBudgetId(budgetId)
            .setStart(modelConverter.map(start, Timestamp::class.java))
            .setEnd(modelConverter.map(end, Timestamp::class.java))
            .build()
        stub.getBudgetTransactions(
            request,
            object : SimpleStreamObserver<GetBudgetTransactionsResponse>(mutationHandler) {
                override fun onNext(value: GetBudgetTransactionsResponse) {
                    val transactions =
                        modelConverter.map(value.transactionsList, BudgetTransaction::class.java)
                    mutationHandler.onNext(transactions)
                }
            })
    }

    override fun budgetPeriodDetails(
        budgetId: String,
        start: DateTime,
        end: DateTime,
        mutationHandler: MutationHandler<Pair<BudgetSpecification, List<BudgetPeriod>>>
    ) {
        val request = GetBudgetPeriodDetailsRequest.newBuilder()
            .setBudgetId(budgetId)
            .setStart(modelConverter.map(start, Timestamp::class.java))
            .setEnd(modelConverter.map(end, Timestamp::class.java))
            .build()
        stub.getBudgetPeriodDetails(
            request,
            object : SimpleStreamObserver<GetBudgetPeriodDetailsResponse>(mutationHandler) {
                override fun onNext(value: GetBudgetPeriodDetailsResponse) {
                    val budgetSpecification = modelConverter.map(value.budgetSpecification, BudgetSpecification::class.java)
                    val budgetPeriods =
                        modelConverter.map(value.budgetPeriodsList, BudgetPeriod::class.java)
                    mutationHandler.onNext(budgetSpecification to budgetPeriods)
                }
            }
        )
    }
}
