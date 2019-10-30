package se.tink.repository.service;

import java.util.Map;
import se.tink.core.models.misc.Period;
import se.tink.repository.ObjectChangeObserver;

public interface PeriodService extends TinkService {

	void subscribe(ObjectChangeObserver<Map<String, Period>> period);

	void unsubscribe(ObjectChangeObserver<Map<String, Period>> period);
}
