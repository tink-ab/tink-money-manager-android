package se.tink.repository.service;


import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.rpc.ActivityHtmlHeadRequest;
import se.tink.grpc.v1.rpc.ActivityHtmlHeadResponse;
import se.tink.grpc.v1.rpc.ListActivityHtmlRequest;
import se.tink.grpc.v1.rpc.ListActivityHtmlResponse;
import se.tink.grpc.v1.services.ActivityServiceGrpc;
import se.tink.core.models.feed.EventsPage;
import se.tink.core.models.feed.HtmlHead;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.SimpleStreamObserver;
import se.tink.repository.cache.FeedInMemoryCache;

public class FeedServiceImpl implements FeedService {


	private FeedInMemoryCache cache = new FeedInMemoryCache();

	private ActivityServiceGrpc.ActivityServiceStub activityServiceApi;
	private StreamingService streamingService;
	private final ModelConverter converter;

	private List<PagingHandle> pagingHandles = new ArrayList<>();

	public FeedServiceImpl(ActivityServiceGrpc.ActivityServiceStub activityServiceApi,
		final StreamingService streamingService, ModelConverter converter) {

		this.activityServiceApi = activityServiceApi;
		this.streamingService = streamingService;
		this.converter = converter;

		streamingService.subscribeForStatistics(new ObjectChangeObserver<StatisticTree>() {
			@Override
			public void onCreate(StatisticTree item) {

			}

			@Override
			public void onRead(StatisticTree item) {
				notifyFeedUpdate();
			}

			@Override
			public void onUpdate(StatisticTree item) {
				notifyFeedUpdate();
			}

			@Override
			public void onDelete(StatisticTree item) {

			}
		});
	}

	private void notifyFeedUpdate() {
		for (PagingHandle handle : pagingHandles) {
			handle.reload();
		}
	}

	@Override
	public void getHtmlHead(final MutationHandler<HtmlHead> handler) {
		activityServiceApi.htmlHead(ActivityHtmlHeadRequest.getDefaultInstance(),
			new SimpleStreamObserver<ActivityHtmlHeadResponse>(handler) {
				@Override
				public void onNext(ActivityHtmlHeadResponse value) {
					handler.onNext(converter.map(value, HtmlHead.class));
				}
			});
	}

	@Override
	public void getListHtml(int nextPageOffset, int maxPageSize, int screenWidth, int screenDensity,
		final MutationHandler<EventsPage> handler) {
		ListActivityHtmlRequest request = ListActivityHtmlRequest.newBuilder()
			.setOffset(nextPageOffset)
			.setLimit(maxPageSize)
			.setScreenWidth(screenWidth)
			.setScreenPpi(screenDensity)
			.build();

		activityServiceApi
			.listHtml(request, new SimpleStreamObserver<ListActivityHtmlResponse>(handler) {
				@Override
				public void onNext(ListActivityHtmlResponse value) {
					handler.onNext(converter.map(value, EventsPage.class));
				}
			});
	}

	@Override
	public PagingHandle subscribe(FeedObserver observer) {
		PagingHandle handle = new FeedPagingHandle(observer, this);
		pagingHandles.add(handle);
		return handle;
	}

	@Override
	public void unsubscribe(PagingHandle handle) {
		pagingHandles.remove(handle);
	}

}

