package se.tink.repository;

import java.util.List;

public interface ChangeObserver<T> {

	void onCreate(List<T> items);

	void onRead(List<T> items);

	void onUpdate(List<T> items);

	void onDelete(List<T> items);
}