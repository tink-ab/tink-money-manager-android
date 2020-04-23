package se.tink.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public interface TinkNetworkErrorHandler {

	void handleError(TinkNetworkError error) throws UnknownErrorTypeException;

	void setOnUnauthenticated(Function0<Unit> action);

	class UnknownErrorTypeException extends Exception {

		public UnknownErrorTypeException(String message) {
			super(message);
		}
	}
}
