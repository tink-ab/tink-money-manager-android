package se.tink.repository;

public interface Listener<T> {

	void onResponse(T response);
}
