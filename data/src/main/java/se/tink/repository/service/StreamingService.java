package se.tink.repository.service;

import java.util.Date;
import java.util.Map;
import se.tink.core.models.account.Account;
import se.tink.core.models.category.CategoryTree;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.misc.Period;
import se.tink.core.models.provider.Provider;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MapChangeObserver;
import se.tink.repository.ObjectChangeObserver;

public interface StreamingService extends TinkService {

	Date getLatestStreamingDate();

	void subscribeForProviders(ObjectChangeObserver<Provider> listener);

	void subscribeForProviders(ChangeObserver<Provider> listener);

	void subscribeForTransactions(ChangeObserver<Transaction> listener);

	void subscribeForCredentials(ChangeObserver<Credential> listener);

	void subscribeForPeriods(ObjectChangeObserver<Map<String, Period>> listener);

	void subscribeForStatistics(ObjectChangeObserver<StatisticTree> listener);

	void subscribeForCategories(ObjectChangeObserver<CategoryTree> handler);

	void subscribeForAccounts(ChangeObserver<Account> listener);

	void subscribeForFollowItems(ChangeObserver<FollowItem> listener);

	void subscribeForUserConfiguration(ObjectChangeObserver<UserConfiguration> listener);

	void subscribeForSignableOperations(ChangeObserver<SignableOperation> listener);

	void unsubscribe(MapChangeObserver listener);

	void unsubscribe(ChangeObserver listener);

	void unsubscribe(ObjectChangeObserver listener);

	void start(StreamingServiceErrorHandler streamingServiceErrorHandler);

	void stopStreaming();
}
