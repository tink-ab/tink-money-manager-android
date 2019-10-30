package se.tink.repository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import se.tink.repository.TinkNetworkErrorHandler.UnknownErrorTypeException;

public class ErrorUtils {

	public static <E, T extends SimpleMutationHandler<E>> T withErrorHandler(
		final TinkNetworkErrorHandler errorHandler, final T errorable) {
		//noinspection unchecked
		return (T) new SimpleMutationHandler<E>() {

			@Override
			public void onNext(E item) {
				errorable.onNext(item);
			}

			@Override
			public void onError(TinkNetworkError error) {
				try {
					errorHandler.handleError(error);
				} catch (UnknownErrorTypeException ex) {
					errorable.onError(error);
				}
			}
		};
	}

	public static <T extends TinkNetworkErrorable> T withErrorHandler(
		TinkNetworkErrorHandler errorHandler, T errorable) {
		//noinspection unchecked
		return (T) Proxy.newProxyInstance(
			errorable.getClass().getClassLoader(),
			errorable.getClass().getInterfaces(),
			new TinkNetworkErrorHandlingInvocationHandler(errorable, errorHandler));
	}

	private static class TinkNetworkErrorHandlingInvocationHandler implements InvocationHandler {

		private final TinkNetworkErrorable proxied;
		private final TinkNetworkErrorHandler errorHandler;

		TinkNetworkErrorHandlingInvocationHandler(TinkNetworkErrorable proxied,
			TinkNetworkErrorHandler errorHandler) {
			this.proxied = proxied;
			this.errorHandler = errorHandler;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("onError") && args.length == 1) {
				try {
					final TinkNetworkError error = (TinkNetworkError) args[0];
					errorHandler.handleError(error);
					return null;
				} catch (UnknownErrorTypeException | ClassCastException ex) {
					return method.invoke(proxied, args);
				}
			} else {
				return method.invoke(proxied, args);
			}
		}
	}
}
