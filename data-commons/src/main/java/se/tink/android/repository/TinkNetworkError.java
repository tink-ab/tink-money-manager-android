package se.tink.android.repository;

//TODO: Core setup - do we need this? If yes, should be in core?
public class TinkNetworkError extends RuntimeException {

	public TinkNetworkError(Throwable t) {
		super(t);
	}

	public enum ErrorCode {
		UNAUTHENTICATED, UNHANDLED_ERROR
	}

	public class StatusCode {
		public int value() {
			return 0;
		}
	}

	public StatusCode getStatusCode() {
		return new StatusCode();
	}

	public ErrorCode getErrorCode() {
		return ErrorCode.UNAUTHENTICATED;
	}

	public String getBackendMessage() {
		return null;
	}
}
