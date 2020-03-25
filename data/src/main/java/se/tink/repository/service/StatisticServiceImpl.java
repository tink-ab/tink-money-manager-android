package se.tink.repository.service;

import com.google.common.collect.Lists;
import com.tink.service.observer.ListChangeObserver;
import com.tink.service.transaction.TransactionService;
import io.grpc.stub.StreamObserver;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.rpc.QueryStatisticsRequest;
import se.tink.grpc.v1.rpc.QueryStatisticsResponse;
import com.tink.model.statistic.StatisticTree;
import com.tink.model.transaction.Transaction;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
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

		transactionService.subscribe(new ListChangeObserver<Transaction>() {
			@Override
			public void onCreate(@NotNull List<? extends Transaction> items) {

			}

			@Override
			public void onRead(@NotNull List<? extends Transaction> items) {

			}

			@Override
			public void onUpdate(@NotNull List<? extends Transaction> items) {
				transactionUpdateStream.onNext((List<Transaction>) items);
			}

			@Override
			public void onDelete(@NotNull List<? extends Transaction> items) {

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
		QueryStatisticsRequest request = QueryStatisticsRequest.getDefaultInstance();
		service
			.queryStatistics(request, new StreamObserver<QueryStatisticsResponse>() {
				@Override
				public void onNext(QueryStatisticsResponse value) {
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
