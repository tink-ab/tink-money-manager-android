package se.tink.repository.service;

import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.ObjectChangeObserver;

public interface StatisticService extends TinkService {

	void subscribe(ObjectChangeObserver<StatisticTree> listener);

	void unsubscribe(ObjectChangeObserver<StatisticTree> listener);

	void refreshStatistics();
}
