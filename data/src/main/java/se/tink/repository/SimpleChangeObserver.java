package se.tink.repository;


import java.util.List;

public class SimpleChangeObserver<T> implements ChangeObserver<T> {

	@Override
	public void onCreate(List<T> items) {

	}

	@Override
	public void onRead(List<T> items) {

	}

	@Override
	public void onUpdate(List<T> items) {

	}

	@Override
	public void onDelete(List<T> items) {

	}
}
