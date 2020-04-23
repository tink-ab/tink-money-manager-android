package se.tink.repository;

public interface ObjectChangeObserver<T> {

	void onCreate(T item);

	void onRead(T item);

	void onUpdate(T item);

	void onDelete(T item);
}
