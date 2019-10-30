package se.tink.repository.manager;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.extensions.AccountExtensionsKt;
import se.tink.core.models.account.Account;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.Period;
import se.tink.core.models.transaction.CreatePartAndCounterpartResponse;
import se.tink.core.models.transaction.SearchResultMetadata;
import se.tink.core.models.transaction.Transaction;
import se.tink.grpc.v1.rpc.GetTransactionRequest;
import se.tink.grpc.v1.rpc.GetTransactionResponse;
import se.tink.grpc.v1.rpc.QueryTransactionsRequest;
import se.tink.grpc.v1.rpc.QueryTransactionsResponse;
import se.tink.grpc.v1.services.TransactionServiceGrpc;
import se.tink.helpers.CollectionsHelper;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.PagingResult;
import se.tink.repository.SimpleChangeObserver;
import se.tink.repository.SimpleStreamObserver;
import se.tink.repository.TinkNetworkError;
import se.tink.repository.cache.Cache;
import se.tink.repository.service.AccountService;
import se.tink.repository.service.Pageable;
import se.tink.repository.service.PagingHandler;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.TransactionService;
import timber.log.Timber;

public class TransactionServiceCachedImpl implements TransactionService {

	private final TransactionServiceGrpc.TransactionServiceStub transactionServiceApi;
	private final TransactionService uncachedService;
	private final StreamingService streamingService;
	private final Cache<List<Transaction>> cache;
	private final ModelConverter converter;
	private final Map<ChangeObserver<Transaction>, List<TransactionSubscription>> subscriptionsByObserver;
	private final AccountService accountService;

	private ChangeObserver<Transaction> streamingChangeObserver;
	private ChangeObserver<Account> accountChangeObserver;


	@Inject
	public TransactionServiceCachedImpl(final TransactionServiceGrpc.TransactionServiceStub service,
		final TransactionService unchachedService, StreamingService streamingService,
		final Cache<List<Transaction>> cache, final ModelConverter converter,
		AccountService accountService) {
		this.transactionServiceApi = service;
		this.uncachedService = unchachedService;
		this.streamingService = streamingService;
		this.cache = cache;
		this.converter = converter;
		this.subscriptionsByObserver = Maps.newHashMap();
		this.accountService = accountService;

		addCacheAsStreamingObserver();
		setupStreamingService();
	}

	private void setupStreamingService() {
		streamingChangeObserver = new ChangeObserver<Transaction>() {
			@Override
			public void onCreate(List<Transaction> items) {
				List<TransactionSubscription> allSubscriptions = Lists
					.newArrayList(getAllSubscriptions());

				for (int i = allSubscriptions.size() - 1; i >= 0; i--) {
					TransactionSubscription changeObserver = allSubscriptions.get(i);
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<Transaction> items) {
				List<TransactionSubscription> allSubscriptions = Lists
					.newArrayList(getAllSubscriptions());

				for (int i = allSubscriptions.size() - 1; i >= 0; i--) {
					TransactionSubscription changeObserver = allSubscriptions.get(i);
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<Transaction> items) {
				List<TransactionSubscription> allSubscriptions = Lists
					.newArrayList(getAllSubscriptions());

				for (int i = allSubscriptions.size() - 1; i >= 0; i--) {
					TransactionSubscription changeObserver = allSubscriptions.get(i);
					try {
						changeObserver.onUpdate(items);
					} catch (Exception e) {
						Timber.e(e);
					}
				}
			}

			@Override
			public void onDelete(List<Transaction> items) {
				List<TransactionSubscription> allSubscriptions = Lists
					.newArrayList(getAllSubscriptions());

				for (int i = allSubscriptions.size() - 1; i >= 0; i--) {
					TransactionSubscription changeObserver = allSubscriptions.get(i);
					changeObserver.onDelete(items);
				}
			}
		};
		streamingService.subscribeForTransactions(streamingChangeObserver);

		accountChangeObserver = new SimpleChangeObserver<Account>() {
			@Override
			public void onDelete(List<Account> items) {
				for (TransactionSubscription subscription : getAllSubscriptions()) {
					subscription.deleteTransactionsForAccounts(items);
				}
			}
		};

		accountService.subscribe(accountChangeObserver);

	}

	@Override
	public void getTransaction(final String transactionId,
		final MutationHandler<Transaction> handler) {

		transactionServiceApi.getTransaction(
			GetTransactionRequest.newBuilder().setTransactionId(transactionId).build(),
			new SimpleStreamObserver<GetTransactionResponse>(handler) {
				@Override
				public void onNext(GetTransactionResponse value) {
					Transaction transaction = converter
						.map(value.getTransaction(), Transaction.class);
					handler.onNext(transaction);
				}
			});

	}

	private void addCacheAsStreamingObserver() {
		ChangeObserver<Transaction> cacheStreamingObserver = new ChangeObserver<Transaction>() {
			public void onCreate(List<Transaction> items) {
				cache.insert(items);
			}

			@Override
			public void onRead(List<Transaction> items) {
				cache.clearAndInsert(items);
			}

			@Override
			public void onUpdate(List<Transaction> items) {
				cache.update(items);
			}

			@Override
			public void onDelete(List<Transaction> items) {
				cache.delete(items);
			}
		};

		TransactionSubscription transactionSubscription = new TransactionSubscriptionBuilder(false,
			cacheStreamingObserver)
			.createTransactionSubscription();

		addSubscriptionForListener(transactionSubscription, cacheStreamingObserver);
	}

	@Override
	public void getSimilarTransactions(final String transactionId,
		final MutationHandler<List<Transaction>> handler) {
		uncachedService.getSimilarTransactions(transactionId, handler);
	}

	@Override
	public void suggestTransactions(boolean evaluateEverything, int nrOfClusters,
		MutationHandler<se.tink.core.models.transaction.SuggestTransactionsResponse> handler) {
		uncachedService.suggestTransactions(evaluateEverything, nrOfClusters, handler);
	}

	@Override
	public void createPartAndCounterpart(String transactionId, String counterpartTransactionId,
		MutationHandler<CreatePartAndCounterpartResponse> mutationHandler, Amount amount,
		int suggestionIndex) {
		uncachedService
			.createPartAndCounterpart(transactionId, counterpartTransactionId, mutationHandler,
				amount, suggestionIndex);
	}

	@Override
	public void updatePartAndCounterpart(String transactionId, String partId, Amount amount,
		MutationHandler<Transaction> handler) {
		uncachedService.updatePartAndCounterpart(transactionId, partId, amount, handler);
	}

	@Override
	public void deletePartAndCounterpart(String transactionId, String counterpartId,
		MutationHandler<Transaction> mutationHandler) {
		uncachedService.deletePartAndCounterpart(transactionId, counterpartId, mutationHandler);
	}

	@Override
	public void suggestCounterparts(String transactionId, int limit,
		MutationHandler<List<Transaction>> mutationHandler) {
		uncachedService.suggestCounterparts(transactionId, limit, mutationHandler);
	}

	/**
	 * Update Transaction fields
	 *
	 * @param handler Callback when completed
	 * @see {{@link Transaction} and @link {{@link se.tink.converter.request.TransactionToUpdateTransactionRequest}}}
	 * Uses the transactionId Uses the description Uses the timestamp Uses the notes Uses the tags
	 */
	@Override
	public void updateTransaction(final Transaction transaction,
		final MutationHandler<Transaction> handler) {

		MutationHandler<Transaction> wrappingUpdateHandler = new MutationHandler<Transaction>() {

			@Override
			public void onCompleted() {
				handler.onCompleted();
			}

			@Override
			public void onNext(Transaction item) {
				streamingChangeObserver.onUpdate(Lists.newArrayList(item));
				handler.onNext(item);
			}

			@Override
			public void onError(TinkNetworkError error) {
				handler.onError(error);
			}
		};

		uncachedService.updateTransaction(transaction, wrappingUpdateHandler);
	}

	/**
	 * Update transactions category
	 *
	 * @param transactionIds list of Transaction Ids
	 * @param categoryCode the new Category code to be set.
	 * @param handler Callback when completed
	 */
	@Override
	public void categorizeTransactions(List<String> transactionIds, String categoryCode,
		final MutationHandler<List<Transaction>> handler) {

		MutationHandler<List<Transaction>> wrappingUpdateHandler = new MutationHandler<List<Transaction>>() {

			@Override
			public void onCompleted() {
				handler.onCompleted();
			}

			@Override
			public void onNext(List<Transaction> item) {
				streamingChangeObserver.onUpdate(item);
				handler.onNext(item);
			}

			@Override
			public void onError(TinkNetworkError error) {
				handler.onError(error);
			}
		};
		uncachedService.categorizeTransactions(transactionIds, categoryCode, wrappingUpdateHandler);
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForAccountId(String accountId,
		final ChangeObserver<Transaction> listener) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(true,
			listener)
			.setAccountId(accountId)
			.createTransactionSubscription();

		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;
	}


	@Override
	public Pageable<Transaction> search(String query, ChangeObserver<Transaction> listener,
		final MutationHandler<SearchResultMetadata> searchMetadataMutationHandler) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(false,
			listener)
			.setQuery(query)
			.createTransactionSubscription();

		subscription.setSearchResultMetadataHandler(searchMetadataMutationHandler);

		addSubscriptionForListener(subscription, listener);

		return subscription;
	}


	@Override
	public Pageable<Transaction> listAndSubscribeForCategoryCode(String categoryCode,
		ChangeObserver<Transaction> listener) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(false,
			listener)
			.setCategoryId(categoryCode)
			.createTransactionSubscription();

		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;

	}

	@Override
	public Pageable<Transaction> listAndSubscribeForCategoryCodeAndPeriod(String categoryCode, Period period, ChangeObserver<Transaction> listener) {
		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(false,
			listener)
			.setCategoryId(categoryCode)
			.setPeriod(period)
			.setPageSize(-1)
			.createTransactionSubscription();
		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;
	}

	@Override
	public void listAllAndSubscribeForCategoryCodeAndPeriod(String categoryCode, Period period, ChangeObserver<Transaction> listener) {
		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(false, listener)
			.setCategoryId(categoryCode)
			.setPeriod(period)
			.createTransactionSubscription();
		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLeftToSpendAndPeriod(Period period,
		ChangeObserver<Transaction> listener) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(false,
			listener)
			.setPeriod(period)
			.createTransactionSubscription();

		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		ChangeObserver<Transaction> listener) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(
			includeUpcomming, listener)
			.createTransactionSubscription();

		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		int pageSize, ChangeObserver<Transaction> listener) {

		final TransactionSubscription subscription = new TransactionSubscriptionBuilder(
			includeUpcomming, listener)
			.setPageSize(pageSize)
			.createTransactionSubscription();

		addSubscriptionForListener(subscription, listener);

		readTransactionsFromCache(subscription);

		return subscription;
	}

	public void readTransactionsFromCache(final TransactionSubscription subscription) {
		// TODO: The cache is not working properly so a quick fix for now is to just turn if off.
		// A sync will be had on 2018-06-11 regarding how to resolve this.

		/*
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				List<Transaction> transactions = cache.read();
				if (transactions != null && transactions.size() > 0) {
					subscription.onReadFromCache(transactions);
				}
			}
		});
		t.start();
		*/
	}

	@Override
	public void unsubscribe(ChangeObserver<Transaction> listener) {
		subscriptionsByObserver.remove(listener);
	}

	private void addSubscriptionForListener(TransactionSubscription subscription,
		ChangeObserver<Transaction> listener) {
		List<TransactionSubscription> subscriptions = subscriptionsByObserver.get(listener);
		if (subscriptions == null) {
			subscriptions = Lists.newLinkedList();
			subscriptionsByObserver.put(listener, subscriptions);
		}
		subscriptions.add(subscription);
		subscription.next(new PagingHandler() {
			@Override
			public void onError(TinkNetworkError error) {

			}

			@Override
			public void onCompleted(PagingResult result) {

			}
		});
	}

	private Collection<TransactionSubscription> getAllSubscriptions() {
		Collection<List<TransactionSubscription>> values = subscriptionsByObserver.values();
		List<TransactionSubscription> allSubscriptions = Lists.newLinkedList();
		for (List<TransactionSubscription> subscriptionList : values) {
			allSubscriptions.addAll(subscriptionList);
		}

		return allSubscriptions;
	}

	private class TransactionSubscription implements ChangeObserver<Transaction>,
		Pageable<Transaction> {

		private final Predicate<Transaction> filter;
		private final String accountId;
		private final String categoryId;
		private final Function<Transaction, String> getTransactionId;
		private final SortedSet<Transaction> transactionSet;
		private final ChangeObserver<Transaction> subscriberChangeObserver;
		private final se.tink.core.models.misc.Period period;
		private final String query;
		private final boolean includeUpcomming;
		private final int pageSize;
		private boolean hasMore = true;
		private String lastTransactionId;
		private MutationHandler<SearchResultMetadata> searchResultMetadataHandler;

		private final Object lock = new Object();

		private AtomicBoolean hasReadFromBackend = new AtomicBoolean(false);

		private TransactionSubscription(final String categoryId, final String accountId,
			final String query, final Period period, final boolean includeUpcomming,
			int pageSize, ChangeObserver<Transaction> subscriberChangeObserver) {
			this.categoryId = categoryId;
			this.accountId = accountId;
			this.period = period;
			this.query = query;
			this.pageSize = pageSize;
			this.includeUpcomming = includeUpcomming;
			this.transactionSet = new ConcurrentSkipListSet<>();
			this.subscriberChangeObserver = subscriberChangeObserver;
			this.filter = new Predicate<Transaction>() {
				@Override
				public boolean apply(Transaction input) {

					String inputCategory = input.getCategoryCode();
					if (categoryId != null && inputCategory != null && !inputCategory
						.startsWith(categoryId)) {
						return false;
					}
					if (accountId != null && !accountId.equals(input.getAccountId())) {
						return false;
					}

					if (period != null && !period.isInPeriod(input.getTimestamp())) {
						return false;
					}

					if (!includeUpcomming && (input.isUpcoming() || input.getTimestamp()
						.isAfter(DateTime.now().withMillisOfDay(0).plusDays(1).minusMillis(1)))) {
						return false;
					}

					return true;
				}
			};
			this.getTransactionId = new Function<Transaction, String>() {
				@Override
				public String apply(Transaction input) {
					return input.getId();
				}
			};
		}

		@Override
		public void onCreate(List<Transaction> items) {
			synchronized (lock) {
				List<Transaction> filteredTransactions = filterTransactions(items);

				transactionSet.addAll(filteredTransactions);
				SortedSet<Transaction> createdTransactions = new TreeSet<>(filteredTransactions);

				subscriberChangeObserver.onCreate(Lists.newLinkedList(createdTransactions));
			}
		}

		@Override
		public void onRead(List<Transaction> items) {
			synchronized (lock) {
				List<Transaction> filteredTransactions = filterTransactions(items);

				transactionSet.clear();
				transactionSet.addAll(filteredTransactions);
				subscriberChangeObserver.onRead(Lists.newLinkedList(transactionSet));
			}
		}

		public void onReadFromCache(List<Transaction> items) {
			synchronized (lock) {
				List<Transaction> filteredTransactions = filterTransactions(items);

				if (!filteredTransactions.isEmpty() && !hasReadFromBackend.get()) {
					transactionSet.clear();
					transactionSet.addAll(filteredTransactions);
					subscriberChangeObserver.onRead(Lists.newLinkedList(transactionSet));
				}
			}
		}

		@Override
		public void onUpdate(List<Transaction> items) {
			synchronized (lock) {
				List<Transaction> filteredTransactions = filterTransactions(items);

				List<Transaction> itemsToDelete = Lists.newArrayList();
				for (Transaction transaction : items) {
					if (transactionSet.contains(transaction)) {
						if (!filteredTransactions.contains(transaction)) {
							itemsToDelete.add(transaction);
						}
					}
				}

				List<Transaction> shouldBeUpdated = Lists.newArrayList(CollectionsHelper
					.unionOn(filteredTransactions, transactionSet, getTransactionId));

				if (!shouldBeUpdated.isEmpty()) {
					transactionSet.removeAll(shouldBeUpdated);
					transactionSet.addAll(shouldBeUpdated);
					subscriberChangeObserver.onUpdate(shouldBeUpdated);
				}

				if (!itemsToDelete.isEmpty()) {
					transactionSet.removeAll(itemsToDelete);
					subscriberChangeObserver.onDelete(itemsToDelete);
				}
			}
		}

		@Override
		public void onDelete(List<Transaction> items) {
			synchronized (lock) {
				items = new ArrayList<>(items);
				Collection<Transaction> shouldBeRemoved = CollectionsHelper
					.unionOn(transactionSet, items, getTransactionId);
				shouldBeRemoved = new ArrayList<>(shouldBeRemoved);
				if (!shouldBeRemoved.isEmpty()) {
					transactionSet.removeAll(shouldBeRemoved);
					subscriberChangeObserver.onDelete(Lists.newLinkedList(shouldBeRemoved));
				}
			}
		}

		@Override
		public void next(PagingHandler handler) {
			query(accountId, categoryId, period, pageSize, handler, includeUpcomming);
		}

		@Override
		public boolean getHasMore() {
			return hasMore;
		}

		private List<Transaction> filterTransactions(List<Transaction> transactions) {
			return Lists.newLinkedList(Collections2.filter(transactions, filter));
		}

		private void query(final String accountId, String categoryId, Period period,
			int limit, final PagingHandler handler, boolean includeUpcomming) {

			QueryTransactionsRequest.Builder request = QueryTransactionsRequest.newBuilder();
			if (accountId != null) {
				List<String> accountIdList = Lists.newLinkedList();
				accountIdList.add(accountId);
				request.addAllAccountIds(accountIdList);
			}
			if (limit > 0) {
				request.setLimit(Int32Value.newBuilder().setValue(limit));
			}
			if (lastTransactionId != null) {
				request.setLastTransactionId(converter.map(lastTransactionId, StringValue.class));
			}
			if (categoryId != null) {
				List<String> categoryCodes = Lists.newArrayList();
				categoryCodes.add(categoryId);
				request.addAllCategoryCodes(categoryCodes);
			}
			if (period != null) {
				request.setStartDate(converter.map(period.getStart().toDate(),
					com.google.protobuf.Timestamp.class));
				request.setEndDate(converter.map(period.getStop().toDate(),
					com.google.protobuf.Timestamp.class));
			}
			if (query != null) {
				request.setQueryString(converter.map(query, StringValue.class));
			}
			request.setIncludeUpcoming(BoolValue.newBuilder().setValue(includeUpcomming).build());

			transactionServiceApi.queryTransactions(request.build(),
				new StreamObserver<QueryTransactionsResponse>() {
					@Override
					public void onNext(QueryTransactionsResponse value) {

						synchronized (lock) {

							handleMetadata(value);

							final List<Transaction> transactions;

							if (value.getTransactionsCount() > 0) {
								transactions = converter.map(value.getTransactionsList(),
									Transaction.class);
								lastTransactionId = transactions.get(transactions.size() - 1)
									.getId();
								if (!hasReadFromBackend.get()) {
									hasReadFromBackend.set(true);
									Set<Transaction> diff = Sets.newHashSet(transactions);
									diff.removeAll(transactionSet);
									if (!diff.isEmpty()) {
										onRead(transactions);
									}
								} else {
									List<Transaction> createdTransactions = new ArrayList<>(
										transactions);
									createdTransactions.removeAll(transactionSet);
									onCreate(createdTransactions);
								}
								hasMore = value.getHasMore();
								handler.onCompleted(new PagingResult(hasMore));
							} else {
								if (!hasReadFromBackend.get()) {
									hasReadFromBackend.set(true);
									onRead(Lists.<Transaction>newLinkedList());
								} else {
									onCreate(Lists.<Transaction>newLinkedList());
								}
							}
						}
					}

					@Override
					public void onError(Throwable t) {
						handler.onError(new TinkNetworkError(t));
					}

					@Override
					public void onCompleted() {

					}
				});
		}

		private void handleMetadata(QueryTransactionsResponse response) {
			SearchResultMetadata metadata = converter.map(response, SearchResultMetadata.class);
			if (searchResultMetadataHandler != null) {
				searchResultMetadataHandler.onNext(metadata);
			}
		}

		public void setSearchResultMetadataHandler(
			MutationHandler<SearchResultMetadata> searchResultMetadataHandler) {
			this.searchResultMetadataHandler = searchResultMetadataHandler;
		}

		public void deleteTransactionsForAccounts(List<Account> items) {
			Set<Transaction> transactionsToDelete = AccountExtensionsKt
				.transactionsBelongingToListOf(items, transactionSet);
			onDelete(new ArrayList<>(transactionsToDelete));
		}
	}

	private class TransactionSubscriptionBuilder {

		private String categoryId = null;
		private String accountId = null;
		private String query = null;
		private Period period = null;
		private int pageSize = 40;
		private final boolean includeUpcomming;
		private final ChangeObserver<Transaction> subscriberChangeObserver;

		private TransactionSubscriptionBuilder(boolean includeUpcomming,
			ChangeObserver<Transaction> subscriberChangeObserver) {
			this.includeUpcomming = includeUpcomming;
			this.subscriberChangeObserver = subscriberChangeObserver;
		}

		private TransactionSubscriptionBuilder setCategoryId(String categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		private TransactionSubscriptionBuilder setAccountId(String accountId) {
			this.accountId = accountId;
			return this;
		}

		private TransactionSubscriptionBuilder setQuery(String query) {
			this.query = query;
			return this;
		}

		private TransactionSubscriptionBuilder setPeriod(Period period) {
			this.period = period;
			return this;
		}

		public TransactionSubscriptionBuilder setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		private TransactionSubscription createTransactionSubscription() {
			return new TransactionSubscription(categoryId, accountId, query, period,
				includeUpcomming, pageSize, subscriberChangeObserver);
		}
	}

}
