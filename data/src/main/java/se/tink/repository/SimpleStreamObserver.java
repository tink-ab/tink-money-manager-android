package se.tink.repository;


public abstract class SimpleStreamObserver<T> implements io.grpc.stub.StreamObserver<T> {

	protected MutationHandler mutationHandler;

	public SimpleStreamObserver(MutationHandler mutationHandler) {
		this.mutationHandler = mutationHandler;
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
		mutationHandler.onError(new TinkNetworkError(t));
	}

	@Override
	public void onCompleted() {
		mutationHandler.onCompleted();
	}
}
