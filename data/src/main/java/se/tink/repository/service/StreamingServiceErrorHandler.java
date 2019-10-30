package se.tink.repository.service;

import se.tink.repository.TinkNetworkError;
import se.tink.repository.TinkNetworkErrorHandler;
import timber.log.Timber;

/**
 * The idea behind this class is to collect standard error handlers for the streaming service.
 */
public class StreamingServiceErrorHandler {

	private static final String TAG = "StreamingErrorHandler";

	private final TinkNetworkErrorHandler networkErrorHandler;

	public StreamingServiceErrorHandler(TinkNetworkErrorHandler networkErrorHandler) {
		this.networkErrorHandler = networkErrorHandler;
	}

	/**
	 * You can override or extend this class with composition of multiple error handlers
	 * @param throwable the error from gRPC
	 * @return return true to indicate that the stream should continue to try to reconnect or false to indicate that this was an unrecoverable error
	 */
	boolean onError(Throwable throwable) {
		try {
			this.networkErrorHandler.handleError(new TinkNetworkError(throwable));
			return false;
		} catch (TinkNetworkErrorHandler.UnknownErrorTypeException | ClassCastException e) {
			Timber.tag(TAG).e(throwable, "Unhandled streaming error");
			return true;
		}
	}
}
