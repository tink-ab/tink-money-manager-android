package se.tink.repository;

import javax.inject.Inject;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class DefaultErrorHandler implements TinkNetworkErrorHandler {

	private Function0<Unit> onUnauthenticatedAction;

	@Inject
	DefaultErrorHandler() {
	}

	@Override
	public void handleError(TinkNetworkError error) throws UnknownErrorTypeException {
		//TODO: Core setup - do we need this
//		switch (error.getStatusCode()) {
//			case UNAUTHENTICATED:
//				onUnauthenticatedAction.invoke();
//				break;
//			default:
//				throw new UnknownErrorTypeException(
//					String.format("Could not handle error of type %s", error.getStatusCode()));
//		}
	}

	@Override
	public void setOnUnauthenticated(Function0<Unit> action) {
		this.onUnauthenticatedAction = action;
	}
}
