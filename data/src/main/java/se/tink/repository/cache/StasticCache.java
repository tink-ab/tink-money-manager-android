package se.tink.repository.cache;

import se.tink.core.models.statistic.StatisticTree;

public interface StasticCache extends Cache<StatisticTree> {

	StatisticTree read();
}
