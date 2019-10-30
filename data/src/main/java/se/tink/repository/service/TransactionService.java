package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.Period;
import se.tink.core.models.transaction.CreatePartAndCounterpartResponse;
import se.tink.core.models.transaction.SearchResultMetadata;
import se.tink.core.models.transaction.SuggestTransactionsResponse;
import se.tink.core.models.transaction.Transaction;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface TransactionService extends TinkService {

	Pageable<Transaction> listAndSubscribeForAccountId(String accountId,
		ChangeObserver<Transaction> listener);

	Pageable<Transaction> listAndSubscribeForCategoryCode(String categoryCode,
		ChangeObserver<Transaction> listener);

	Pageable<Transaction> listAndSubscribeForCategoryCodeAndPeriod(String categoryCode,
		Period period, ChangeObserver<Transaction> listener);

	void listAllAndSubscribeForCategoryCodeAndPeriod(String categoryCode, Period period, ChangeObserver<Transaction> listener);

	Pageable<Transaction> listAndSubscribeForLeftToSpendAndPeriod(Period period,
		ChangeObserver<Transaction> listener);

	Pageable<Transaction> search(String query, ChangeObserver<Transaction> listener,
		MutationHandler<SearchResultMetadata> metadataMutationHandler);

	Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		ChangeObserver<Transaction> listener);

	Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		int pageSize, ChangeObserver<Transaction> listener);

	void unsubscribe(ChangeObserver<Transaction> listener);

	void updateTransaction(Transaction transaction, final MutationHandler<Transaction> handler);

	void getTransaction(String transactionId, MutationHandler<Transaction> handler);

	void categorizeTransactions(List<String> transactionIds, String categoryCode,
		final MutationHandler<List<Transaction>> handler);

	void getSimilarTransactions(final String transactionId,
		final MutationHandler<List<Transaction>> handler);

	void suggestTransactions(boolean evaluateEverything, int nrOfClusters,
		final MutationHandler<SuggestTransactionsResponse> handler);

	void createPartAndCounterpart(String transactionId, String counterpartTransactionId,
		MutationHandler<CreatePartAndCounterpartResponse> handler, Amount partAmount,
		int suggestionIndex);

	void updatePartAndCounterpart(String transactionId, String partId, Amount amount,
		MutationHandler<Transaction> handler);

	void deletePartAndCounterpart(String transactionId, String counterpartId,
		MutationHandler<Transaction> mutationHandler);

	void suggestCounterparts(String transactionId, int limit,
		MutationHandler<List<Transaction>> mutationHandler);
}
