package se.tink.repository.service;

import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;

import se.tink.converter.ModelConverter;
import se.tink.core.models.account.AccountDetails;
import se.tink.core.models.transfer.Transfer;
import se.tink.grpc.v1.models.Loan;
import se.tink.grpc.v1.rpc.ListLoansRequest;
import se.tink.grpc.v1.rpc.ListLoansResponse;
import se.tink.grpc.v1.services.LoanServiceGrpc;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;


public class LoanServiceImpl implements LoanService {

    private final LoanServiceGrpc.LoanServiceStub service;
    private final ModelConverter modelConverter;

    public LoanServiceImpl(LoanServiceGrpc.LoanServiceStub loanServiceStub, ModelConverter modelConverter) {
        this.modelConverter = modelConverter;
        this.service = loanServiceStub;
    }

    @Override
    public void listLoans(final MutationHandler<List<se.tink.core.models.account.Loan>> handler) {
        ListLoansRequest.Builder builder = ListLoansRequest.newBuilder();
        service.listLoans(builder.build(), new SimpleStreamObserver<ListLoansResponse>(handler) {
            @Override
            public void onNext(ListLoansResponse value) {
                handler.onNext(modelConverter.map(value.getLoansList(), se.tink.core.models.account.Loan.class));

            }
        });

    }
}
