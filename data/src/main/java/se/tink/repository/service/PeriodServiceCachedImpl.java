package se.tink.repository.service;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Period;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.cache.Cache;

public class PeriodServiceCachedImpl implements PeriodService {

	private final Cache<Map<String, Period>> cache;
	private final StreamingService streamingService;
	private final ModelConverter converter;
	private final List<ObjectChangeObserver<Map<String, Period>>> changeObservers;

	@Inject
	public PeriodServiceCachedImpl(Cache<Map<String, Period>> cache,
		StreamingService streamingServiceStub, ModelConverter converter) {
		this.cache = cache;
		this.streamingService = streamingServiceStub;
		this.converter = converter;
		this.changeObservers = Lists.newLinkedList();

		addCacheAsStreamingObserver();
		setupStreamingService();
	}

	@Override
	public void subscribe(ObjectChangeObserver<Map<String, Period>> observer) {
		readFromCache(observer);
		changeObservers.add(observer);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<Map<String, Period>> listener) {
		changeObservers.remove(listener);
	}


	private void readFromCache(final ObjectChangeObserver<Map<String, Period>> listener) {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Period> item = cache.read();
				if (item != null && item.size() > 0) {
					listener.onRead(item);
				}
			}
		});
		thread.start();
	}

	private void setupStreamingService() {
		streamingService.subscribeForPeriods(new ObjectChangeObserver<Map<String, Period>>() {

			@Override
			public void onCreate(Map<String, Period> item) {
				for (int i = 0; i < changeObservers.size(); i++) {
					ObjectChangeObserver<Map<String, Period>> changeObserver = changeObservers
						.get(i);
					changeObserver.onCreate(item);
				}
			}

			@Override
			public void onRead(Map<String, Period> item) {
				for (int i = 0; i < changeObservers.size(); i++) {
					ObjectChangeObserver<Map<String, Period>> changeObserver = changeObservers
						.get(i);
					changeObserver.onRead(item);
				}
			}

			@Override
			public void onUpdate(Map<String, Period> item) {
				for (int i = 0; i < changeObservers.size(); i++) {
					ObjectChangeObserver<Map<String, Period>> changeObserver = changeObservers
						.get(i);
					changeObserver.onUpdate(item);
				}
			}

			@Override
			public void onDelete(Map<String, Period> item) {
				for (int i = 0; i < changeObservers.size(); i++) {
					ObjectChangeObserver<Map<String, Period>> changeObserver = changeObservers
						.get(i);
					changeObserver.onDelete(item);
				}
			}
		});
	}

	private void addCacheAsStreamingObserver() {
		changeObservers.add(new ObjectChangeObserver<Map<String, Period>>() {
			@Override
			public void onCreate(Map<String, Period> item) {
				cache.insert(item);
			}

			@Override
			public void onRead(Map<String, Period> item) {
				cache.clearAndInsert(item);
			}

			@Override
			public void onUpdate(Map<String, Period> item) {
				cache.update(item);
			}

			@Override
			public void onDelete(Map<String, Period> item) {
				cache.delete(item);
			}
		});
	}
}