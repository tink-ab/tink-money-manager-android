package se.tink.repository;

public interface StreamObserver<T> {

	void onError(TinkNetworkError t);

	void onCompleted();

	void onNext(T item);
}
