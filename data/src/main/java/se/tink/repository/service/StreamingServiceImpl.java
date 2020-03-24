package se.tink.repository.service;

import android.os.Handler;
import com.google.common.collect.Lists;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.StreamingResponseType;
import com.tink.model.account.Account;
import com.tink.model.category.CategoryTree;
import com.tink.model.time.Period;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.user.UserConfiguration;
import se.tink.grpc.v1.rpc.StreamingRequest;
import se.tink.grpc.v1.rpc.StreamingResponse;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.repository.ChangeObserver;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.Listener;
import se.tink.repository.MapChangeObserver;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.SimpleMutationHandler;
import se.tink.repository.TinkNetworkError;
import timber.log.Timber;

public class StreamingServiceImpl implements StreamingService {

	private final StreamingServiceGrpc.StreamingServiceStub service;
	private final ModelConverter converter;

	private Handler keepAliveHandler = new Handler();
	private static final int MAX_BACKOFF_TIME = 10 * 1000;
	private static final int INITIAL_BACKOFF_TIME = 2 * 1000;
	private static final int SUCCESS_DELAY = 1 * 30 * 1000;
	private static final int FAIL_DELAY = 1 * 1000;

	private final List<ChangeObserver<Transaction>> transactionListeners = Lists.newArrayList();
	private final List<ObjectChangeObserver<Map<String, Period>>> periodListeners = Lists
		.newArrayList();
	private final List<ObjectChangeObserver<StatisticTree>> statisticsListeners = Lists
		.newArrayList();
	private final List<ObjectChangeObserver<CategoryTree>> categoryListeners = Lists.newArrayList();
	private final List<ChangeObserver<Account>> accountListeners = Lists.newArrayList();
	private Date latestStreamingTime = DateTime.now().toDate();
	@Nullable
	private StreamObserver<StreamingRequest> requestStreamObserver;
	private ExceptionTracker exceptionTracker;


	public static Date timestampToDate(Timestamp timestamp) {
		return new Date(TimeUnit.SECONDS.toMillis(timestamp.getSeconds()) + TimeUnit.NANOSECONDS
			.toMillis(timestamp.getNanos()));
	}

	private List<ObjectChangeObserver<UserConfiguration>> userConfigurationListeners = Lists
		.newArrayList();
	private Listener<StreamingResponse> streamSuccessListener = new Listener<StreamingResponse>() {
		@Override
		public void onResponse(StreamingResponse value) {
			if (value == null) {
				return;
			}

			StreamingResponseType type = converter
				.map(value.getType(), StreamingResponseType.class);

			if (value.hasTimestamp()) {
				latestStreamingTime = timestampToDate(value.getTimestamp());
			}

			if (latestStreamingTime == null) {
				latestStreamingTime = DateTime.now().toDate();
			}

			if (value.hasUserConfiguration()) {
				UserConfiguration userConfiguration = converter
					.map(value.getUserConfiguration(), UserConfiguration.class);
				notifyListeners(userConfiguration, userConfigurationListeners, type);
			}

      /*
	  Listeners are notified in an order so that nothing that
      the models with no dependencies on other types will
      be fired first and models with dependecies fired latest.
      */

			if (value.hasCategories()) {
				CategoryTree categories = converter
					.map(value.getCategories(), CategoryTree.class);
				notifyListeners(categories, categoryListeners, type);
			}

			if (value.hasPeriods()) {
				Map<String, Period> periods = converter
					.map(value.getPeriods().getPeriodMap(), String.class, Period.class);
				notifyListeners(periods, periodListeners, type);
			}
			
			if (value.hasAccounts()) {
				List<Account> accounts = converter
					.map(value.getAccounts().getAccountList(), Account.class);
				notifyListeners(accounts, accountListeners, type);
			}

			if (value.hasTransactions()) {
				List<Transaction> transactions = converter
					.map(value.getTransactions().getTransactionList(),
						Transaction.class);
				notifyListeners(transactions, transactionListeners, type);
			}

			if (value.hasStatistics()) {
				StatisticTree statistics = converter
					.map(value.getStatistics(), StatisticTree.class);
				notifyListeners(statistics, statisticsListeners, type);
			}
		}
	};


	public StreamingServiceImpl(StreamingServiceGrpc.StreamingServiceStub stub,
		ModelConverter converter) {
		service = stub;
		this.converter = converter;
	}

	@Override
	public Date getLatestStreamingDate() {
		return latestStreamingTime;
	}

//	@Override
//	public void subscribeForTransactions(ChangeObserver<Transaction> listener) {
//		transactionListeners.add(listener);
//	}

	@Override
	public void subscribeForPeriods(ObjectChangeObserver<Map<String, Period>> listener) {
		//periodListeners.add(listener);
	}

	@Override
	public void subscribeForStatistics(ObjectChangeObserver<StatisticTree> listener) {
		statisticsListeners.add(listener);
	}

	@Override
	public void subscribeForCategories(ObjectChangeObserver<CategoryTree> listener) {
		categoryListeners.add(listener);
	}

	@Override
	public void subscribeForAccounts(ChangeObserver<Account> listener) {
		//accountListeners.add(listener);
	}

	@Override
	public void subscribeForUserConfiguration(ObjectChangeObserver<UserConfiguration> listener) {
		// TODO: PFMSDK: This is needed for currency code unless we can get that info elsewhere
		//userConfigurationListeners.add(listener);
	}

	@Override
	public void unsubscribe(MapChangeObserver listener) {
		statisticsListeners.remove(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver listener) {
		transactionListeners.remove(listener);
		categoryListeners.remove(listener);
		accountListeners.remove(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver listener) {
		categoryListeners.remove(listener);
		statisticsListeners.remove(listener);
		periodListeners.remove(listener);
		userConfigurationListeners.remove(listener);
	}


	@Override
	public void start(StreamingServiceErrorHandler streamingServiceErrorHandler) {
		//connectStreaming(streamingServiceErrorHandler, INITIAL_BACKOFF_TIME);
	}

	private void connectStreaming(final StreamingServiceErrorHandler streamingServiceErrorHandler,
		final int currentDelay) {
		new Thread(new Runnable() {
			int delay = currentDelay;

			@Override
			public void run() {

				StreamObserver<StreamingResponse> responseStreamObserver = new StreamObserver<StreamingResponse>() {
					@Override
					public void onNext(StreamingResponse value) {
						streamSuccessListener.onResponse(value);
						delay = INITIAL_BACKOFF_TIME;
					}

					@Override
					public void onError(Throwable t) {
						Timber.e(t, "onError");
						boolean shouldRetryConnection = streamingServiceErrorHandler.onError(t);
						stopReEstablishStreaming();
						if (shouldRetryConnection) {
							delay = incrementBackoff(delay, MAX_BACKOFF_TIME);
							delayedConnectStreaming(delay, streamingServiceErrorHandler);
						}
					}

					@Override
					public void onCompleted() {
						Timber.d("onCompleted");
						stopReEstablishStreaming();
					}
				};

				requestStreamObserver = service.stream(responseStreamObserver);
				continuousReconnectStreaming();
			}
		}).start();
	}

	private int incrementBackoff(int delay, int max) {
		return Math.min(delay * 2, max);
	}

	private Runnable streamReconnector = new Runnable() {
		@Override
		public void run() {
			if (requestStreamObserver != null) {
				requestStreamObserver.onNext(StreamingRequest.getDefaultInstance());
				continuousReconnectStreaming();
			}
		}
	};

	private void continuousReconnectStreaming() {
		keepAliveHandler.postDelayed(streamReconnector, 30000);
	}

	private void stopReEstablishStreaming() {
		keepAliveHandler.removeCallbacks(streamReconnector);
		requestStreamObserver = null;
	}

	private void delayedConnectStreaming(final int delay,
		final StreamingServiceErrorHandler streamingServiceErrorHandler) {
		Timber.d("connect stream observer");
		keepAliveHandler.postDelayed(() -> connectStreaming(streamingServiceErrorHandler, delay), delay);
	}

	@Override
	public void stopStreaming() {
		if (requestStreamObserver != null) {
			requestStreamObserver.onCompleted();
		}
		stopReEstablishStreaming();
	}

	private <T> void notifyListeners(T model, List<ObjectChangeObserver<T>> listeners,
		StreamingResponseType type) {
		switch (type) {
			case CREATE:
				for (int i = 0; i < listeners.size(); i++) {
					ObjectChangeObserver<T> listener = listeners.get(i);
					listener.onCreate(model);
				}
				break;
			case READ:
				for (int i = 0; i < listeners.size(); i++) {
					ObjectChangeObserver<T> listener = listeners.get(i);
					listener.onRead(model);
				}
				break;
			case UPDATE:
				for (int i = 0; i < listeners.size(); i++) {
					ObjectChangeObserver<T> listener = listeners.get(i);
					listener.onUpdate(model);
				}
				break;
			case DELETE:
				for (int i = 0; i < listeners.size(); i++) {
					ObjectChangeObserver<T> listener = listeners.get(i);
					listener.onDelete(model);
				}
				break;
		}
	}

	private <T> void notifyListeners(List<T> models, List<ChangeObserver<T>> listeners,
		StreamingResponseType type) {
		switch (type) {
			case CREATE:
				for (int i = 0; i < listeners.size(); i++) {
					ChangeObserver<T> listener = listeners.get(i);
					listener.onCreate(models);
				}
				break;
			case READ:
				for (int i = 0; i < listeners.size(); i++) {
					ChangeObserver<T> listener = listeners.get(i);
					listener.onRead(models);
				}
				break;
			case UPDATE:
				for (int i = 0; i < listeners.size(); i++) {
					ChangeObserver<T> listener = listeners.get(i);
					listener.onUpdate(models);
				}
				break;
			case DELETE:
				for (int i = 0; i < listeners.size(); i++) {
					ChangeObserver<T> listener = listeners.get(i);
					listener.onDelete(models);
				}
				break;
		}
	}

	public void setExceptionTracker(
		ExceptionTracker exceptionTracker) {
		this.exceptionTracker = exceptionTracker;
	}


}
