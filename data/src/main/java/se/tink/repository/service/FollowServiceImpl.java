package se.tink.repository.service;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.grpc.v1.models.PeriodMode;
import se.tink.grpc.v1.rpc.CreateFollowItemRequest;
import se.tink.grpc.v1.rpc.CreateFollowItemResponse;
import se.tink.grpc.v1.rpc.DeleteFollowItemRequest;
import se.tink.grpc.v1.rpc.DeleteFollowItemResponse;
import se.tink.grpc.v1.rpc.GetFollowItemHistoryRequest;
import se.tink.grpc.v1.rpc.GetFollowItemHistoryResponse;
import se.tink.grpc.v1.rpc.UpdateFollowItemRequest;
import se.tink.grpc.v1.rpc.UpdateFollowItemResponse;
import se.tink.grpc.v1.services.FollowServiceGrpc;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class FollowServiceImpl implements FollowService {

	private StreamingService streamingService;
	private FollowServiceGrpc.FollowServiceStub service;
	private ModelConverter converter;
	private List<ChangeObserver<FollowItem>> changeObserverers;

	@Inject
	public FollowServiceImpl(StreamingService streamingService, ModelConverter converter,
		FollowServiceGrpc.FollowServiceStub service) {
		this.streamingService = streamingService;
		this.converter = converter;
		this.service = service;

		changeObserverers = Lists.newArrayList();

		startSubScribing();
	}

	@Override
	public void subscribe(ChangeObserver<FollowItem> listener) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<FollowItem> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void createFollowItem(FollowItem item, final MutationHandler<FollowItem> handler) {
		CreateFollowItemRequest request = converter.map(item, CreateFollowItemRequest.class);

		service
			.createFollowItem(request, new SimpleStreamObserver<CreateFollowItemResponse>(handler) {
				@Override
				public void onNext(CreateFollowItemResponse value) {
					handler.onNext(converter.map(value.getFollowItem(), FollowItem.class));
				}
			});
	}

	@Override
	public void deleteFollowItem(FollowItem item, final MutationHandler<Void> handler) {
		DeleteFollowItemRequest.Builder request = DeleteFollowItemRequest.newBuilder();
		request.setFollowItemId(item.getId());

		service.deleteFollowItem(request.build(),
			new SimpleStreamObserver<DeleteFollowItemResponse>(handler) {
				@Override
				public void onNext(DeleteFollowItemResponse value) {
					handler.onNext(null);
				}
			});
	}

	@Override
	public void updateFollowItem(final FollowItem item,
		final MutationHandler<FollowItem> mutationHandler) {
		UpdateFollowItemRequest followItemRequest = converter
			.map(item, UpdateFollowItemRequest.class);
		service.updateFollowItem(followItemRequest,
			new SimpleStreamObserver<UpdateFollowItemResponse>(mutationHandler) {
				@Override
				public void onNext(UpdateFollowItemResponse value) {
					mutationHandler.onNext(converter.map(value.getFollowItem(), FollowItem.class));
				}
			});
	}

	@Override
	public void getDailyHistoryForFollowItem(FollowItem item,
		MutationHandler<List<PeriodExactNumberPair>> mutationHandler) {
		service.getFollowItemHistory(
			GetFollowItemHistoryRequest.newBuilder()
				.setFollowItemId(item.getId())
				.setPeriodMode(PeriodMode.PERIOD_MODE_DAILY)
				.build(),
			new SimpleStreamObserver<GetFollowItemHistoryResponse>(mutationHandler) {
				@Override
				public void onNext(GetFollowItemHistoryResponse value) {
					//noinspection unchecked
					mutationHandler.onNext(converter.map(
						value.getFollowItemHistory().getHistoricalAmountsList(),
						PeriodExactNumberPair.class));
				}
			});
	}

	private void startSubScribing() {
		streamingService.subscribeForFollowItems(new ChangeObserver<FollowItem>() {
			@Override
			public void onCreate(List<FollowItem> items) {
				for (ChangeObserver<FollowItem> changeObserver : changeObserverers) {
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<FollowItem> items) {
				for (ChangeObserver<FollowItem> changeObserver : changeObserverers) {
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<FollowItem> items) {
				for (ChangeObserver<FollowItem> changeObserver : changeObserverers) {
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<FollowItem> items) {
				for (ChangeObserver<FollowItem> changeObserver : changeObserverers) {
					changeObserver.onDelete(items);
				}
			}
		});
	}

}
