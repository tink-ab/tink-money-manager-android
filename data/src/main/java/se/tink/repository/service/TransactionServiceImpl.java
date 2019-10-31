package se.tink.repository.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.Period;
import se.tink.core.models.transaction.SearchResultMetadata;
import se.tink.core.models.transaction.SuggestTransactionsResponse;
import se.tink.core.models.transaction.Transaction;
import se.tink.grpc.v1.models.CurrencyDenominatedAmount;
import se.tink.grpc.v1.models.Tag;
import se.tink.grpc.v1.rpc.CategorizeTransactionsRequest;
import se.tink.grpc.v1.rpc.CategorizeTransactionsResponse;
import se.tink.grpc.v1.rpc.DeletePartAndCounterpartRequest;
import se.tink.grpc.v1.rpc.DeletePartAndCounterpartResponse;
import se.tink.grpc.v1.rpc.GetSimilarTransactionsRequest;
import se.tink.grpc.v1.rpc.GetSimilarTransactionsResponse;
import se.tink.grpc.v1.rpc.QueryTransactionsRequest;
import se.tink.grpc.v1.rpc.QueryTransactionsResponse;
import se.tink.grpc.v1.rpc.SuggestCounterpartsRequest;
import se.tink.grpc.v1.rpc.SuggestCounterpartsResponse;
import se.tink.grpc.v1.rpc.SuggestTransactionsRequest;
import se.tink.grpc.v1.rpc.UpdatePartAndCounterpartRequest;
import se.tink.grpc.v1.rpc.UpdatePartAndCounterpartResponse;
import se.tink.grpc.v1.rpc.UpdateTransactionRequest;
import se.tink.grpc.v1.rpc.UpdateTransactionResponse;
import se.tink.grpc.v1.services.TransactionServiceGrpc;
import se.tink.helpers.CollectionsHelper;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;
import se.tink.repository.PagingResult;
import se.tink.repository.SimpleStreamObserver;
import se.tink.repository.TinkNetworkError;

public class TransactionServiceImpl implements TransactionService {

	private final TransactionServiceGrpc.TransactionServiceStub transactionServiceApi;
	private final ModelConverter converter;
	private final StreamingService streamingService;
	private final Map<ChangeObserver<Transaction>, List<TransactionSubscription>> subscriptionsByObserver;

	@Inject
	public TransactionServiceImpl(
		TransactionServiceGrpc.TransactionServiceStub transactionServiceApi,
		StreamingService streamingStub, ModelConverter converter) {
		this.transactionServiceApi = transactionServiceApi;
		streamingService = streamingStub;
		this.converter = converter;
		subscriptionsByObserver = Maps.newHashMap();
		startListeningToStream();
	}

	private void startListeningToStream() {

		streamingService.subscribeForTransactions(new ChangeObserver<Transaction>() {
			@Override
			public void onCreate(List<Transaction> items) {
				Collection<TransactionSubscription> allSubscriptions = getAllSubscriptions();

				for (ChangeObserver<Transaction> changeObserver : allSubscriptions) {
					changeObserver.onCreate(items);
				}
			}

			@Override
			public void onRead(List<Transaction> items) {
				Collection<TransactionSubscription> allSubscriptions = getAllSubscriptions();

				for (ChangeObserver<Transaction> changeObserver : allSubscriptions) {
					changeObserver.onRead(items);
				}
			}

			@Override
			public void onUpdate(List<Transaction> items) {
				Collection<TransactionSubscription> allSubscriptions = getAllSubscriptions();

				for (ChangeObserver<Transaction> changeObserver : allSubscriptions) {
					changeObserver.onUpdate(items);
				}
			}

			@Override
			public void onDelete(List<Transaction> items) {
				Collection<TransactionSubscription> allSubscriptions = getAllSubscriptions();

				for (ChangeObserver<Transaction> changeObserver : allSubscriptions) {
					changeObserver.onDelete(items);
				}
			}
		});
	}


	@Override
	public void updateTransaction(Transaction transaction,
		final MutationHandler<Transaction> handler) {
		List<Tag> tags = Lists.newArrayList();
		for (se.tink.core.models.transaction.Tag tag : transaction.getTags()) {
			tags.add(Tag.newBuilder().setName(tag.getName()).build());
		}

		UpdateTransactionRequest.Builder builder = UpdateTransactionRequest.newBuilder();

		builder.setTransactionId(transaction.getId());
		builder.setDescription(
			StringValue.newBuilder().setValue(transaction.getDescription()).build());

		if (transaction.getTimestamp() != null) {
			long millis = transaction.getTimestamp().getMillis();
			builder.setDate(Timestamp.newBuilder().setSeconds(millis / 1000)
				.setNanos((int) ((millis % 1000) * 1000000)));
		}

		builder.setNotes(StringValue.newBuilder().setValue(transaction.getNotes()).build());
		builder.addAllTags(tags);

		UpdateTransactionRequest request = builder.build();

		transactionServiceApi
			.updateTransaction(request, new StreamObserver<UpdateTransactionResponse>() {
				@Override
				public void onNext(UpdateTransactionResponse value) {
					handler.onNext(converter.map(value.getTransaction(), Transaction.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});

	}

	@Override
	public void getTransaction(String transactionId, MutationHandler<Transaction> handler) {
		//TODO
	}

	@Override
	public void categorizeTransactions(List<String> transactionIds, String categoryCode,
		final MutationHandler<List<Transaction>> handler) {
		CategorizeTransactionsRequest request = CategorizeTransactionsRequest.newBuilder()
			.addAllTransactionIds(transactionIds)
			.setCategoryCode(categoryCode)
			.build();

		transactionServiceApi
			.categorizeTransactions(request, new StreamObserver<CategorizeTransactionsResponse>() {
				@Override
				public void onNext(CategorizeTransactionsResponse value) {
					List<Transaction> list = Lists.newArrayList();
					for (se.tink.grpc.v1.models.Transaction transaction : value
						.getTransactionsList()) {
						list.add(converter.map(transaction, Transaction.class));
					}

					handler.onNext(list);
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void getSimilarTransactions(String transactionId, final MutationHandler<List<Transaction>> handler) {
		GetSimilarTransactionsRequest request = GetSimilarTransactionsRequest.newBuilder()
			.setTransactionId(transactionId)
			.build();

		transactionServiceApi
			.getSimilarTransactions(request, new StreamObserver<GetSimilarTransactionsResponse>() {
				@Override
				public void onNext(GetSimilarTransactionsResponse value) {
					List<Transaction> list = Lists.newArrayList();
					for (se.tink.grpc.v1.models.Transaction transaction : value
						.getTransactionsList()) {
						list.add(converter.map(transaction, Transaction.class));
					}
					handler.onNext(list);
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void suggestTransactions(boolean evaluateEverything, int nrOfClusters,
		final MutationHandler<SuggestTransactionsResponse> handler) {

		SuggestTransactionsRequest request = SuggestTransactionsRequest.newBuilder()
			.setEvaluateEverything(evaluateEverything)
			.setNumberOfClusters(nrOfClusters)
			.build();

		transactionServiceApi.suggestTransactions(request,
			new StreamObserver<se.tink.grpc.v1.rpc.SuggestTransactionsResponse>() {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.SuggestTransactionsResponse value) {
					handler.onNext(converter.map(value, SuggestTransactionsResponse.class));
				}

				@Override
				public void onError(Throwable t) {
					handler.onError(new TinkNetworkError(t));
				}

				@Override
				public void onCompleted() {
					handler.onCompleted();
				}
			});
	}

	@Override
	public void updatePartAndCounterpart(String transactionId, String partId, Amount amount,
		final MutationHandler<Transaction> handler) {

		CurrencyDenominatedAmount amountDTO = converter
			.map(amount, CurrencyDenominatedAmount.class);

		UpdatePartAndCounterpartRequest request = UpdatePartAndCounterpartRequest
			.newBuilder()
			.setTransactionId(transactionId)
			.setPartId(partId)
			.setPartAmount(amountDTO)
			.build();

		transactionServiceApi.updatePartAndCounterpart(request,
			new SimpleStreamObserver<UpdatePartAndCounterpartResponse>(handler) {
				@Override
				public void onNext(UpdatePartAndCounterpartResponse value) {
					Transaction updatedTransaction = converter
						.map(value.getTransaction(), Transaction.class);
					handler.onNext(updatedTransaction);
				}
			});
	}

	@Override
	public void deletePartAndCounterpart(String transactionId, String counterpartId,
		final MutationHandler<Transaction> handler) {
		DeletePartAndCounterpartRequest request = DeletePartAndCounterpartRequest.newBuilder()
			.setTransactionId(transactionId)
			.setPartId(counterpartId) //TODO naming
			.build();
		transactionServiceApi.deletePartAndCounterpart(request,
			new SimpleStreamObserver<DeletePartAndCounterpartResponse>(handler) {
				@Override
				public void onNext(DeletePartAndCounterpartResponse value) {
					handler.onNext(converter.map(value.getTransaction(), Transaction.class));
				}
			});
	}

	@Override
	public void suggestCounterparts(String transactionId, int limit,
		final MutationHandler<List<Transaction>> handler) {

		SuggestCounterpartsRequest request = SuggestCounterpartsRequest
			.newBuilder()
			.setTransactionId(transactionId)
			.setLimit(limit)
			.build();

		transactionServiceApi.suggestCounterparts(request,
			new SimpleStreamObserver<SuggestCounterpartsResponse>(handler) {
				@Override
				public void onNext(SuggestCounterpartsResponse value) {
					List<Transaction> suggestedTransactions = converter
						.map(value.getTransactionsList(), Transaction.class);
					handler.onNext(suggestedTransactions);
				}
			});
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForAccountId(String accountId,
		final ChangeObserver<Transaction> listener) {
		QueryTransactionsRequest.Builder requestBuilder = QueryTransactionsRequest.newBuilder();
		requestBuilder.addAccountIds(accountId);
		requestBuilder.setLimit(Int32Value.newBuilder().setValue(100).build());

		QueryTransactionsRequest request = requestBuilder.build();

//        transactionServiceApi.queryTransactions(request

		//TODO GRPC-Refactoring

//        request.setAccountIds(ids);

//        ProtobufInt32Value value = new ProtobufInt32Value();
//        value.setValue(123241);
//        request.setLimit(value);

//        ProtobufInt32Value value = new ProtobufInt32Value();
//        value.setValue(100);
//        request.setLimit(value);

//        request.setEndDate(DateTime.parse("2018-02-01").toDate());

//        transactionServiceApi.queryTransactions(request, new Listener<QueryTransactionsResponse>() {
//            @Override
//            public void onResponse(QueryTransactionsResponse response) {
//
//            if(response.getTransactions() == null)
//                {
//                    return;
//                }
//
//                List<Transaction> transactions = converter
//                    .map(response.getTransactions(), new TypeToken<List<Transaction>>() {
//                    }.getType());
//
//                listener.onRead(transactions);
//            }
//        }, new ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });

		TransactionSubscription subscription = new TransactionSubscription(null, accountId,
			listener);
		addSubScriptionAndGetFirstPage(subscription, listener);
		return subscription;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForCategoryCode(String categoryCode,
		ChangeObserver<Transaction> listener) {
		//TODO
		return null;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForCategoryCodeAndPeriod(String categoryCode,
		se.tink.core.models.misc.Period period, ChangeObserver<Transaction> listener) {
		return null;
	}

	@Override
	public void listAllAndSubscribeForCategoryCodeAndPeriod(String categoryCode, Period period, ChangeObserver<Transaction> listener) {

	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLeftToSpendAndPeriod(
		se.tink.core.models.misc.Period period,
		ChangeObserver<Transaction> listener) {
		return null;
	}

	@Override
	public Pageable<Transaction> search(String query, ChangeObserver<Transaction> listener,
		MutationHandler<SearchResultMetadata> metadataMutationHandler) {
		return null;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		ChangeObserver<Transaction> listener) {
		return null;
	}

	@Override
	public Pageable<Transaction> listAndSubscribeForLatestTransactions(boolean includeUpcomming,
		int pageSize, ChangeObserver<Transaction> listener) {
		return null;
	}

	@Override
	public void unsubscribe(ChangeObserver<Transaction> listener) {
		subscriptionsByObserver.remove(listener);
	}

	private void addSubScriptionAndGetFirstPage(TransactionSubscription subscription,
		ChangeObserver<Transaction> listener) {
		addSubscriptionForListener(subscription, listener);
		subscription.next(new PagingHandler() {
			@Override
			public void onError(TinkNetworkError error) {

			}

			@Override
			public void onCompleted(PagingResult result) {

			}
		});
	}

	private void addSubscriptionForListener(TransactionSubscription subscription,
		ChangeObserver<Transaction> listener) {
		List<TransactionSubscription> subscriptions = subscriptionsByObserver.get(listener);
		if (subscriptions == null) {
			subscriptions = Lists.newLinkedList();
			subscriptionsByObserver.put(listener, subscriptions);
		}
		subscriptions.add(subscription);
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
		private final String categoryCode;
		private final Function<Transaction, String> getTransactionId;
		private final SortedSet<Transaction> transactionSet;
		private final ChangeObserver<Transaction> subscriberChangeObserver;
		private boolean hasMore = true;

		private TransactionSubscription(final String categoryCode, final String accountId,
			ChangeObserver<Transaction> subscriberChangeObserver) {
			this.categoryCode = categoryCode;
			this.accountId = accountId;
			transactionSet = new TreeSet<>();
			this.subscriberChangeObserver = subscriberChangeObserver;
			this.filter = new Predicate<Transaction>() {
				@Override
				public boolean apply(Transaction input) {
					if (categoryCode != null && !categoryCode.equals(input.getCategoryCode())) {
						return false;
					}
					if (accountId != null && !accountId.equals(input.getAccountId())) {
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
			List<Transaction> filteredTransactions = filterTransactions(items);
			transactionSet.addAll(filteredTransactions);
			subscriberChangeObserver.onCreate(Lists.newLinkedList(filteredTransactions));
		}

		@Override
		public void onRead(List<Transaction> items) {
			List<Transaction> filteredTransactions = filterTransactions(items);
			transactionSet.addAll(filteredTransactions);
			subscriberChangeObserver.onRead(Lists.newLinkedList(filteredTransactions));
		}

		@Override
		public void onUpdate(List<Transaction> items) {
			List<Transaction> filteredTransactions = filterTransactions(items);

			Collection<Transaction> shouldBeRemoved = CollectionsHelper
				.unionOn(transactionSet, filteredTransactions, getTransactionId);
			Collection<Transaction> shouldBeAdded = CollectionsHelper
				.unionOn(filteredTransactions, shouldBeRemoved, getTransactionId);

			transactionSet.removeAll(shouldBeRemoved);
			transactionSet.addAll(shouldBeAdded);
			subscriberChangeObserver.onUpdate(Lists.newLinkedList(shouldBeAdded));
		}

		@Override
		public void onDelete(List<Transaction> items) {
			Collection<Transaction> shouldBeRemoved = CollectionsHelper
				.unionOn(transactionSet, items, getTransactionId);
			transactionSet.removeAll(shouldBeRemoved);
			subscriberChangeObserver.onDelete(Lists.newLinkedList(shouldBeRemoved));
		}

		@Override
		public void next(PagingHandler handler) {
			query(accountId, categoryCode, getLastId(), 30, handler);
		}

		@Override
		public boolean getHasMore() {
			return hasMore;
		}

		private List<Transaction> filterTransactions(List<Transaction> transactions) {
			return Lists.newLinkedList(Collections2.filter(transactions, filter));
		}

		private String getLastId() {
			if (transactionSet.isEmpty()) {
				return null;
			}
			Transaction last = transactionSet.last();
			return last.getId();
		}

		private void query(final String accountId, String categoryId, String lastId, int limit,
			final PagingHandler handler) {

			// TODO babis add date range
			QueryTransactionsRequest.Builder builder = QueryTransactionsRequest.newBuilder();
			builder.setLimit(Int32Value.newBuilder().setValue(limit).build());

			if (accountId != null) {
				builder.addAccountIds(accountId);
			}

			if (lastId != null) {
				builder.setLastTransactionId(StringValue.newBuilder().setValue(lastId).build());
			}

			if (categoryId != null) {
				builder.addCategoryCodes(categoryId);
			}

			QueryTransactionsRequest request = builder.build();

			transactionServiceApi
				.queryTransactions(request, new StreamObserver<QueryTransactionsResponse>() {
					@Override
					public void onNext(QueryTransactionsResponse value) {
						List<Transaction> list = Lists.newArrayList();
						for (se.tink.grpc.v1.models.Transaction transaction : value
							.getTransactionsList()) {
							list.add(converter.map(transaction, Transaction.class));
						}
						onRead(list);
						handler.onCompleted(new PagingResult(value.getHasMore()));
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
	}
}
