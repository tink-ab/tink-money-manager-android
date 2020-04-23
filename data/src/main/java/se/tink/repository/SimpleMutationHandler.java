package se.tink.repository;

public abstract class SimpleMutationHandler<T> implements MutationHandler<T> {

	@Override
	public void onNext(T item) {

	}

	@Override
	public void onCompleted() {

	}
}
