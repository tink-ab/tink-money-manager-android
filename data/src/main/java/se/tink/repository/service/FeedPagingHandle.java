package se.tink.repository.service;

import se.tink.core.models.feed.EventsPage;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleMutationHandler;
import se.tink.repository.TinkNetworkError;
import timber.log.Timber;

public class FeedPagingHandle implements FeedService.PagingHandle {

	private long currentOffset;
	private long maxPageSize;
	private long screenWidth;
	private long screenDensity;
	private boolean isSetup;
	private FeedObserver observer;
	private FeedService service;

	public FeedPagingHandle(FeedObserver observer, FeedService service) {
		this.observer = observer;
		this.service = service;
	}

	@Override
	public void init(long maxPageSize, long screenWidth, long screenDensity) {
		currentOffset = 0;
		this.maxPageSize = maxPageSize;
		this.screenWidth = screenWidth;
		this.screenDensity = screenDensity;
		isSetup = true;
	}

	@Override
	public void next() {
		if (!isSetup) {
			Timber.w("FeedPagingHandle.next was called before init!");
		}
		service.getListHtml((int) currentOffset, (int) maxPageSize, (int) screenWidth,
			(int) screenDensity, new SimpleMutationHandler<EventsPage>() {
				@Override
				public void onError(TinkNetworkError error) {
					observer.onError(error);
				}

				@Override
				public void onNext(EventsPage item) {
					if (currentOffset == 0) {
						observer.onFirst(item);
					} else {
						observer.onNext(item);
					}
					currentOffset = item.getNextPageOffset();
				}
			});
	}

	@Override
	public void reset() {
		currentOffset = 0;
	}

	@Override
	public void reload() {
		if (!isSetup) {
			return;
		}
		service.getListHtml(0, (int) maxPageSize, (int) screenWidth, (int) screenDensity,
			new MutationHandler<EventsPage>() {
				@Override
				public void onError(TinkNetworkError error) {
					observer.onError(error);
				}

				@Override
				public void onCompleted() {

				}

				@Override
				public void onNext(EventsPage item) {
					observer.onFirst(item);
				}
			});
	}
}
