package se.tink.repository.service;

import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.rpc.GetInsightsRequest;
import se.tink.grpc.v1.rpc.InsightsResponse;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.core.models.onboarding.insights.Insights;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.TinkNetworkError;

public class StatisticServiceImpl implements StatisticService {

	private final StatisticServiceGrpc.StatisticServiceStub service;
	private final ModelConverter converter;
	private final List<ObjectChangeObserver<StatisticTree>> changeObserverers;
	private final StreamingService streamingService;

	@Inject
	public StatisticServiceImpl(StreamingService streamingStub, ModelConverter converter,
		StatisticServiceGrpc.StatisticServiceStub serviceStub) {
		service = serviceStub;
		streamingService = streamingStub;
		this.converter = converter;
		changeObserverers = Lists.newArrayList();
		startSubScribing();
	}

	//TODO
	private void startSubScribing() {
		streamingService.subscribeForStatistics(new ObjectChangeObserver<StatisticTree>() {
			@Override
			public void onCreate(StatisticTree items) {
				for (ObjectChangeObserver<StatisticTree> changeObserver : changeObserverers) {
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(StatisticTree items) {
				for (ObjectChangeObserver<StatisticTree> changeObserver : changeObserverers) {
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(StatisticTree items) {
				for (ObjectChangeObserver<StatisticTree> changeObserver : changeObserverers) {
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(StatisticTree items) {
				for (ObjectChangeObserver<StatisticTree> changeObserver : changeObserverers) {
					changeObserver.onDelete(items);
				}
			}
		});
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, List<Type> types) {
		changeObserverers.add(listener);
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener, Type type) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<StatisticTree> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void getInsights(final MutationHandler<Insights> mutationHandler) {
		service.getInsights(GetInsightsRequest.getDefaultInstance(),
			new StreamObserver<InsightsResponse>() {
				@Override
				public void onNext(InsightsResponse value) {

					mutationHandler.onNext(converter.map(value, Insights.class));
				}

				@Override
				public void onError(Throwable t) {
					mutationHandler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					mutationHandler.onCompleted();
				}
			});
	}
}
