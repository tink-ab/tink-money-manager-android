package se.tink.android.repository;

public interface ExceptionTracker {

	<E extends Exception> void exceptionThrown(E e) throws E;
}
