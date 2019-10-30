package se.tink.repository.service;

import se.tink.core.models.feed.EventsPage;
import se.tink.repository.TinkNetworkErrorable;

public interface FeedObserver extends TinkNetworkErrorable {

	void onFirst(EventsPage page);

	void onNext(EventsPage page);
}
