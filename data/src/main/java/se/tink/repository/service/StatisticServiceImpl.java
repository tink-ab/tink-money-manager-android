package se.tink.repository.service;

import com.google.common.collect.Lists;
import io.grpc.stub.StreamObserver;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.transaction.Transaction;
import se.tink.grpc.v1.rpc.GetStatisticsRequest;
import se.tink.grpc.v1.rpc.StatisticsResponse;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.repository.ChangeObserver;
import se.tink.repository.ObjectChangeObserver;

public class StatisticServiceImpl implements StatisticService {

	private final StatisticServiceGrpc.StatisticServiceStub service;
	private final ModelConverter converter;
	private final List<ObjectChangeObserver<StatisticTree>> changeObserverers;
	private final StreamingService streamingService;
	private final TransactionService transactionService;

	@Inject
	public StatisticServiceImpl(StreamingService streamingStub, ModelConverter converter,
		StatisticServiceGrpc.StatisticServiceStub serviceStub, TransactionService transactionService) {
		service = serviceStub;
		streamingService = streamingStub;
		this.transactionService = transactionService;
		this.converter = converter;
		changeObserverers = Lists.newArrayList();
		startSubScribing();
	}

	private PublishSubject<List<Transaction>> transactionUpdateStream = PublishSubject.create();

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

		transactionService.subscribe(new ChangeObserver<Transaction>() {
			@Override
			public void onCreate(List<Transaction> items) {

			}

			@Override
			public void onRead(List<Transaction> items) {

			}

			@Override
			public void onUpdate(List<Transaction> items) {
				transactionUpdateStream.onNext(items);
			}

			@Override
			public void onDelete(List<Transaction> items) {

			}
		});

		int intervalPeriod = 3000;
		int debounceTimeout = (intervalPeriod / 2) + 100;
		transactionUpdateStream
			.flatMap((List<Transaction> items) -> Observable.intervalRange(1, 4, 1000, intervalPeriod, TimeUnit.MILLISECONDS))
			.debounce(debounceTimeout, TimeUnit.MILLISECONDS)
			.subscribe((Long a) -> refreshStatistics());
	}

	@Override
	public void subscribe(ObjectChangeObserver<StatisticTree> listener) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<StatisticTree> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void refreshStatistics() {
		GetStatisticsRequest request = GetStatisticsRequest.getDefaultInstance();
		service
			.getStatistics(request, new StreamObserver<StatisticsResponse>() {
				@Override
				public void onNext(StatisticsResponse value) {
					StatisticTree statisticTree = converter.map(value.getStatistics(), StatisticTree.class);
					for (ObjectChangeObserver<StatisticTree> changeObserver : changeObserverers) {
						changeObserver.onRead(statisticTree);
					}
				}

				@Override
				public void onError(Throwable t) {

				}

				@Override
				public void onCompleted() {

				}
			});
	}
}
