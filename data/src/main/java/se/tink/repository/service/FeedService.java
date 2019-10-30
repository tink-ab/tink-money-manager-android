package se.tink.repository.service;

import se.tink.core.models.feed.EventsPage;
import se.tink.core.models.feed.HtmlHead;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;

public interface FeedService extends TinkService {


	void getHtmlHead(final MutationHandler<HtmlHead> handler);

	void getListHtml(int nextPageOffset, int maxPageSize, int screenWidth, int screenDensity,
		final MutationHandler<EventsPage> handler);

	FeedService.PagingHandle subscribe(FeedObserver observer);

	void unsubscribe(FeedService.PagingHandle handle);

	interface PagingHandle {

		void init(long maxPageSize, long screenWidth, long screenDensity);

		void next();

		void reset();

		void reload();
	}
}
