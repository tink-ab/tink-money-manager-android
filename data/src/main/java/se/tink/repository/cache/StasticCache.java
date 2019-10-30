package se.tink.repository.cache;

import java.util.List;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;

public interface StasticCache extends Cache<StatisticTree> {

	StatisticTree read(List<Type> types);
}
