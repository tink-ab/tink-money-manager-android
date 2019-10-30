package se.tink.repository;

public interface MutationHandler<T> extends TinkNetworkErrorable {

	void onCompleted();

	void onNext(T item);
}