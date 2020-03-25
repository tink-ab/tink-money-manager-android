package se.tink.repository;


import com.tink.service.observer.ListChangeObserver;
import java.util.List;

public class SimpleChangeObserver<T> implements ListChangeObserver<T> {

	@Override
	public void onCreate(List<? extends T> items) {

	}

	@Override
	public void onRead(List<? extends T> items) {

	}

	@Override
	public void onUpdate(List<? extends T> items) {

	}

	@Override
	public void onDelete(List<? extends T> items) {

	}
}
