package se.tink.repository;

public class VoidStreamObserver<T> extends SimpleStreamObserver<T> {


	public VoidStreamObserver(MutationHandler<Void> mutationHandler) {
		super(mutationHandler);
	}

	@Override
	public void onNext(T value) {
		mutationHandler.onNext(null);
	}
}
