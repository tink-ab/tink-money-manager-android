package se.tink.repository.service;

import java.util.List;

import se.tink.core.models.account.AccountDetails;
import se.tink.core.models.transfer.Transfer;
import se.tink.grpc.v1.models.Loan;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface LoanService extends TinkService {

    void listLoans(MutationHandler<List<se.tink.core.models.account.Loan>> handler);
}
