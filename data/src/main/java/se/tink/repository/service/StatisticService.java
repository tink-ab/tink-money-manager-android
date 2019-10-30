package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.onboarding.insights.Insights;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;

public interface StatisticService extends TinkService {

	void subscribe(ObjectChangeObserver<StatisticTree> listener, List<Type> types);

	void subscribe(ObjectChangeObserver<StatisticTree> listener, Type type);

	void unsubscribe(ObjectChangeObserver<StatisticTree> listener);

	void getInsights(MutationHandler<Insights> mutationHandler);
}
