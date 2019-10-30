package se.tink.repository;

import java.util.Map;

public interface MapChangeObserver<T, U> {

	void onCreate(Map<T, U> items);

	void onRead(Map<T, U> items);

	void onUpdate(Map<T, U> items);

	void onDelete(Map<T, U> items);
}
