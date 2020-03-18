package se.tink.repository.service;

import java.util.Date;
import java.util.Map;
import se.tink.core.models.account.Account;
import com.tink.model.category.CategoryTree;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MapChangeObserver;
import se.tink.repository.ObjectChangeObserver;

public interface StreamingService extends TinkService {

	Date getLatestStreamingDate();

//	void subscribeForTransactions(ChangeObserver<Transaction> listener);

	void subscribeForPeriods(ObjectChangeObserver<Map<String, Period>> listener);

	void subscribeForStatistics(ObjectChangeObserver<StatisticTree> listener);

	void subscribeForCategories(ObjectChangeObserver<CategoryTree> handler);

	void subscribeForAccounts(ChangeObserver<Account> listener);

	void subscribeForUserConfiguration(ObjectChangeObserver<UserConfiguration> listener);

	void unsubscribe(MapChangeObserver listener);

	void unsubscribe(ChangeObserver listener);

	void unsubscribe(ObjectChangeObserver listener);

	void start(StreamingServiceErrorHandler streamingServiceErrorHandler);

	void stopStreaming();
}
